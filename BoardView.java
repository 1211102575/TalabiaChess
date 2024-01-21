import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BoardView extends JFrame {
    private JButton[][] cellButtons;
    private BoardController controller;
    private JButton selectedCellButton;
    
    public BoardView(BoardController controller) {
        this.controller = controller;
        setLayout(new GridLayout(6,7));
        initiateBoard();
    }

    public void initiateBoard() {
        cellButtons = new JButton[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                cellButtons[i][j] = new JButton();
                cellButtons[i][j].setPreferredSize(new Dimension(100, 100));
                cellButtons[i][j].setBackground(Color.WHITE);
                cellButtons[i][j].setBorder(new LineBorder(Color.GRAY));
                int buttoni = i;
                int buttonj = j;
                cellButtons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.handleCellButtonClick(buttoni, buttonj);
                        selectedCellButton = cellButtons[buttoni][buttonj];
                    }
                });
                add(cellButtons[i][j]);
            }
        }
        updateIcon();
    }

    public void updateIcon() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j <7; j++) {
                Piece piece = controller.getPiece(i, j);
                if (piece != null) {
                    cellButtons[i][j].setIcon(piece.getIcon());
                }
                else {
                    cellButtons[i][j].setIcon(null);
                }
            }       
        }
    }

    public void flip() {
        removeAll();
        for (int i = 5; i >= 0; i = i - 1) {
            for (int j = 6; j >=0; j = j - 1) {
                add(cellButtons[i][j]);
            }
        }
    }

    public void setSelectBorder() {
        selectedCellButton.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }
    public void clearSelectedBorder() {
        selectedCellButton.setBorder(new LineBorder(Color.GRAY));
    }

    public void showAvailableMove(ArrayList<Move> availableList) {
        for (Move square : availableList) {
            cellButtons[square.start.getX()][square.start.getY()].setBackground(new Color(0, 255, 0));
        }
    }
    public void clearAvailableMove() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j <7; j++) {
                cellButtons[i][j].setBackground(Color.WHITE);
            }       
        }
    }
}
