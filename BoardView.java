import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

// Fam Yi Qi

// The class handles display of the Board.
// View part of MVC
public class BoardView extends JFrame {
    private JButton[][] cellButtons;
    private BoardController controller;
    private JButton selectedCellButton;

    private JPanel board;
    private JPanel boardContainer;
    private JPanel buttonPanel ;
    private JPanel centerPanel ; 
    private JButton saveButton ;
    private JButton newGameButton;
    private JButton loadButton ; 
    private JButton exitButton ;
    private boolean flipped = false;

    private final PieceIconVisitor visitor = new PieceIconVisitor();
    
    public BoardView() {
        setLayout(new BorderLayout());
        setTitle("Talabia Chess");
        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        board = new JPanel();
        board.setLayout(new GridLayout(6,7));
        board.setBorder(new LineBorder(new Color(40, 33, 21 ),20));

        boardContainer = new JPanel();
        boardContainer.setLayout(new GridBagLayout()); // Changed to BorderLayout
        boardContainer.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                controller.resize(board, boardContainer);
                //https://stackoverflow.com/questions/27544569/java-how-to-control-jpanel-aspect-ratio
            } 
        });
        boardContainer.add(board); // Added board to the center
        boardContainer.setBackground(new Color(117, 95, 62 ));

        //create four buttons on the right side.
        Dimension buttonSize = new Dimension(100,50);
        saveButton = new JButton("Save");
        saveButton.setMaximumSize(buttonSize);
        saveButton.setBackground(Color.ORANGE);
        saveButton.setForeground(Color.black);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleSaveGameButton();
            }
        });

        newGameButton = new JButton("New Game");
        newGameButton.setMaximumSize(buttonSize);
        newGameButton.setBackground(Color.ORANGE);
        newGameButton.setForeground(Color.black);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleNewGameButton();
            }
        });

        loadButton = new JButton("Load Game");
        loadButton.setMaximumSize(buttonSize);
        loadButton.setBackground(Color.ORANGE);
        loadButton.setForeground(Color.black);
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleLoadGameButton();
            }
        });


        exitButton = new JButton("Exit");
        exitButton.setMaximumSize(buttonSize);
        exitButton.setBackground(Color.ORANGE);
        exitButton.setForeground(Color.black);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.handleExitButton();
            }
        });


        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel , BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalStrut(90));
        buttonPanel.add(newGameButton);
        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(loadButton);
        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createVerticalStrut(50));
        buttonPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        //create center panel
        centerPanel = new JPanel();
        centerPanel.setBackground(new Color(117, 95, 62 ));
        centerPanel.setLayout(new BorderLayout());
        
        centerPanel.add(boardContainer, BorderLayout.CENTER); // Added boardContainer to the center
        centerPanel.add(buttonPanel, BorderLayout.EAST); // Added buttonPanel to the east

        add(centerPanel);
        revalidate();
        pack();
        setLocationRelativeTo(null);
    }

    public void setController(BoardController controller) {
        this.controller = controller;
    }

    public boolean getFlipped() {
        return flipped;
    }
    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void invalidPiece() {
        JOptionPane.showMessageDialog(new JFrame(), "Please select your piece to move!", "Dialog", JOptionPane.ERROR_MESSAGE);
    }

    public void initiateBoard() {
        Color defaultColor =new Color (71, 58, 11  );
        cellButtons = new JButton[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                
                if (defaultColor.equals(new Color(71, 58, 11  ))) {
                    defaultColor = new Color(192, 177, 127 );
                }
                else{
                    defaultColor = new Color (71, 58, 11  ) ;
                }
                cellButtons[row][col] = new JButton();
                cellButtons[row][col].setPreferredSize(new Dimension(100, 100));
                cellButtons[row][col].setBackground(defaultColor);
                cellButtons[row][col].setBorder(new LineBorder(Color.WHITE));
                int buttonRow = row;
                int buttonCol = col;
                cellButtons[row][col].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedCellButton = cellButtons[buttonRow][buttonCol];
                        controller.handleCellButtonClick(buttonRow, buttonCol);
                    }
                });
                board.add(cellButtons[row][col]);
            }
        }
        updateIcon();
        setVisible(true);
    }

    public void updateIcon() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col <7; col++) {
                if (controller.getPiece(row, col) != null) {
                    if (flipped) {
                        if (controller.getPiece(row, col) instanceof PointPiece) {
                            visitor.visitFliped((PointPiece) controller.getPiece(row, col));
                            Icon icon = visitor.getIcon();
                            cellButtons[row][col].setIcon(icon);
                            continue;
                        }
                    }
                    controller.getPiece(row, col).accept(visitor);
                    Icon icon = visitor.getIcon();
                    cellButtons[row][col].setIcon(icon);
                }
                else {
                    cellButtons[row][col].setIcon(new ImageIcon());
                }
            }       
        }
    }

    public void flip() {
        board.removeAll();
        if (!flipped) {
            for (int row = 5; row >= 0; row = row - 1) {
                for (int col = 6; col >=0; col = col - 1) {
                    board.add(cellButtons[row][col]);
                    
                }
            }
            flipped = true;
        }
        else {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 7; col++) {
                    board.add(cellButtons[row][col]);
                }
            }
            flipped = false;
        }
    }

    public void setSelectBorder() {
        selectedCellButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }
    public void clearSelectedBorder() {
        Color defaultColor =new Color (71, 58, 11  );
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col <7; col++) {
                if (defaultColor.equals(new Color(71, 58, 11  ))) {
                    defaultColor = new Color(192, 177, 127 );
                }
                else{
                    defaultColor = new Color(71, 58, 11  ) ;
                }
                cellButtons[row][col].setBackground(defaultColor);
                cellButtons[row][col].setBorder(new LineBorder(Color.WHITE));
            }       
        }
    }

    public void showAvailableMove(ArrayList<Move> availableList) {
        for (Move square : availableList) {
            cellButtons[square.getEnd().getRow()][square.getEnd().getCol()].setBackground(new Color(0, 255, 0));
        }
    }
    public void clearAvailableMove() {
        Color defaultColor =new Color (71, 58, 11  );
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col <7; col++) {
                if (defaultColor.equals(new Color(71, 58, 11  ))) {
                    defaultColor = new Color(192, 177, 127 );
                }
                else{
                    defaultColor = new Color(71, 58, 11  ) ;
                }
                cellButtons[row][col].setBackground(defaultColor);
            }       
        }
    }

    public void displayWinner(String winner) {
        JOptionPane.showMessageDialog(this, winner, "Game Over!", JOptionPane.INFORMATION_MESSAGE);
    }
}
