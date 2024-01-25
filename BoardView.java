import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class BoardView extends JFrame {
    private JButton[][] cellButtons;
    private BoardController controller;
    private JButton selectedCellButton;
    private JPanel centerPanel;
    private JPanel board;
    private JPanel boardContainer;

    private final PieceIconVisitor visitor = new PieceIconVisitor();

    public BoardView() {
        setLayout(new BorderLayout());
        setTitle("Talabia Chess");
        setSize(600, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        board = new JPanel();
        board.setPreferredSize(new Dimension(750, 720));
        board.setLayout(new GridLayout(6, 7));
        board.setBorder(new EmptyBorder(20, 20, 20, 20));

        boardContainer = new JPanel();
        boardContainer.setLayout(new FlowLayout());
        boardContainer.add(board);

        // centerPanel = new JPanel();
        // centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        // centerPanel.add(boardContainer);
        // add(centerPanel, BorderLayout.CENTER);
        add(boardContainer, BorderLayout.CENTER);

    }

    public void setController(BoardController controller) {
        this.controller = controller;
    }

    public void initiateBoard() {
        cellButtons = new JButton[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {

                cellButtons[row][col] = new JButton();
                cellButtons[row][col].setPreferredSize(new Dimension(100, 100));
                cellButtons[row][col].setBackground(Color.WHITE);
                cellButtons[row][col].setBorder(new LineBorder(Color.GRAY));
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

        JButton saveButton = new JButton("Save Game");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.saveGame(Game.getInstance()); // Save the current game instance
                JOptionPane.showMessageDialog(BoardView.this, "Game saved successfully!");
            }
        });

        JButton loadButton = new JButton("Load Game");
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game loadedGame = Game.loadGame();
                if (loadedGame != null) {
                    Game.setInstance(loadedGame); // Set the loaded game instance
                    updateIcon(); // Update the UI to reflect the loaded game state
                    JOptionPane.showMessageDialog(BoardView.this, "Game loaded successfully!");
                } else {
                    JOptionPane.showMessageDialog(BoardView.this, "Error loading the game.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        add(buttonPanel, BorderLayout.SOUTH);

        updateIcon();
        setVisible(true);
    }

    public void updateIcon() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (controller.getPiece(row, col) != null) {
                    controller.getPiece(row, col).accept(visitor);
                    Icon icon = visitor.getIcon();
                    cellButtons[row][col].setIcon(icon);
                }
            }
        }
    }

    public void flip() {
        removeAll();
        for (int row = 5; row >= 0; row = row - 1) {
            for (int col = 6; col >= 0; col = col - 1) {
                add(cellButtons[row][col]);
            }
        }
    }

    public void setSelectBorder() {
        selectedCellButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }

    public void clearSelectedBorder() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                cellButtons[row][col].setBackground(Color.WHITE);
                cellButtons[row][col].setBorder(new LineBorder(Color.GRAY));
            }
        }
    }

    public void showAvailableMove(ArrayList<Move> availableList) {
        for (Move square : availableList) {
            cellButtons[square.getEnd().getRow()][square.getEnd().getCol()].setBackground(new Color(0, 255, 0));
        }
    }

    public void clearAvailableMove() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                cellButtons[row][col].setBackground(Color.WHITE);
            }
        }
    }

    private void saveGame() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save_game.ser"))) {
            oos.writeObject(Game.getInstance()); // Assuming you have a static method to get the current game instance
            JOptionPane.showMessageDialog(this, "Game saved successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving the game.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save_game.ser"))) {
            Game loadedGame = (Game) ois.readObject();
            // Set the loaded game instance to the current game instance
            Game.setInstance(loadedGame);
            updateIcon(); // Update the UI to reflect the loaded game state
            JOptionPane.showMessageDialog(this, "Game loaded successfully!");
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading the game.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
