import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BoardView extends JFrame {
    private JButton[][] cellButtons;
    private BoardController controller;
    private JButton selectedCellButton;

    private JPanel board;
    private JPanel boardContainer;
    private JPanel leftPanel;
    private JPanel buttonPanel ;
    private JPanel centerPanel ; 
    private JButton saveButton ;
    private JButton loadButton ; 
    private JButton exitButton ;
    private boolean flipped = false;

    private final PieceIconVisitor visitor = new PieceIconVisitor();
    
    public BoardView() {
        setLayout(new BorderLayout());
        setTitle("Talabia Chess");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        board = new JPanel();
        board.setPreferredSize(new Dimension(750,720));
        board.setLayout(new GridLayout(6,7));
        board.setBorder(new LineBorder(new Color(40, 33, 21 ),20));

        boardContainer = new JPanel();
        boardContainer.setPreferredSize(new Dimension(760,730));
        boardContainer.setLayout(new FlowLayout()); // floatlayout : not resize the board no matter window is maximized/ minimizied
        boardContainer.add(board);
        boardContainer.setBackground(new Color(117, 95, 62 ));

        leftPanel = new JPanel();


        //create three buttons on the right side.
        Dimension buttonSize = new Dimension(100,50);
        saveButton = new JButton("Save");
        saveButton.setPreferredSize(buttonSize);
        saveButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        loadButton = new JButton("Load");
        loadButton.setPreferredSize(buttonSize);
        loadButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(buttonSize);
        exitButton.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setPreferredSize(new Dimension(300, 0));
        buttonPanel.setLayout(new BoxLayout(buttonPanel , BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createRigidArea(new Dimension(0,220)));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0,100)));
        buttonPanel.add(loadButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0,100)));
        buttonPanel.add(exitButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0,220)));
        buttonPanel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        
        //create center panel
        centerPanel = new JPanel();
        centerPanel.setBackground(new Color(117, 95, 62 ));
        centerPanel.setPreferredSize(new Dimension(1000,900));
        
        // centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        // centerPanel.add(Box.createRigidArea(new Dimension (300,0)));
        // centerPanel.add(boardContainer);
        // centerPanel.add(Box.createRigidArea(new Dimension (200,0)));
        // centerPanel.add(buttonPanel);
        // add(centerPanel,BorderLayout.CENTER);

        centerPanel.setLayout(new BorderLayout());
        centerPanel.add(boardContainer, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);

    }

    public void setController(BoardController controller) {
        this.controller = controller;
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
}
