import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class BoardView extends JPanel {
    private JButton[][] squares;
    private Board board;
    
    public BoardView(Board board) {
        this.board = board;
        setLayout(new GridLayout(6,7));
        squares = new JButton[6][7];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                squares[i][j] = new JButton();
                squares[i][j].setPreferredSize(new Dimension(100, 100));
                squares[i][j].setBackground(Color.WHITE);
                squares[i][j].setBorder(new LineBorder(Color.GRAY));
                squares[i][j].addActionListener(new SquareListener(i, j));
                add(squares[i][j]);
            }
        }
        update(board);
        //mv
        board.setSquares(squares);
    }

    public void update(Board board) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j <7; j++) {
                Piece piece = board.getPiece(i, j);
                if (piece != null) {
                    squares[i][j].setIcon(piece.getIcon());
                }
                else {
                    squares[i][j].setIcon(null);
                }
            }       
        }
    }

    public void flip(Board board) {
        removeAll();
        for (int i = 5; i >= 0; i = i - 1) {
            for (int j = 6; j >=0; j = j - 1) {
                add(squares[i][j]);
            }
        }
    }

    //mv
    public void showAvailableMove(ArrayList<int[]> availableList) {
        for (int[] square : availableList) {
            squares[square[0]][square[1]].setBackground(new Color(0, 255, 0));
        }
    }
    public void clearAvailableMove() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j <7; j++) {
                squares[i][j].setBackground(Color.WHITE);
            }       
        }
    }

    private class SquareListener implements ActionListener {
        private int x;
        private int y;

        public SquareListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void actionPerformed(ActionEvent e) {
            JButton previousSelectedSquare = board.getSelectedSquare();
            //mv
            board.squareControl(x, y);
            JButton currentSelectedSquare = board.getSelectedSquare();

            if (previousSelectedSquare != currentSelectedSquare) {
                if (previousSelectedSquare != null) {
                    previousSelectedSquare.setBorder(new LineBorder(Color.GRAY)); 
                    clearAvailableMove(); 
                }
                if (currentSelectedSquare != null) {
                    currentSelectedSquare.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
                    //mv
                    showAvailableMove(board.getAvailableMoves(x, y));
                }
                update(board);
            }
        }
    }
}
