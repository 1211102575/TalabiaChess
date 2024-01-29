import java.awt.Dimension;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

// Pang Ding Yuan

// Handles what happens after button is clicked in BoardView.
// Controller Part of MVC
public class BoardController {
    private BoardView view = new BoardView();
    private Game game = new Game();
    private Cell selectedCell;

    public BoardController() {
        view.setController(this);
        selectedCell = null;
        view.setController(this);
        view.initiateBoard();
    }

    public Piece getPiece(int row, int col) {
        return game.getBoard().getCell(row, col).getPiece();
    }

    public void handleCellButtonClick(int row, int col) {
        selectedCell = game.getBoard().getCell(row, col);
        char signal = game.selectCell(selectedCell);
        switch (signal) {
            case 'n':
                selectedCell = null;
                view.clearSelectedBorder();

                break;
            case 'x':
                //errorhandling
                view.invalidPiece();
                selectedCell = null;
                break;
            case 'm':
                if (!game.getGameState().equals("IN_PLAY")) {
                    view.clearSelectedBorder();
                    view.clearAvailableMove();
                    winGame(game.getGameState());
                    break;
                }
                view.clearSelectedBorder();
                view.clearAvailableMove();
                view.flip();
                view.updateIcon();
                break;
            case 'h':
                //move
                view.setSelectBorder();
                view.showAvailableMove(selectedCell.getPiece().getAllMoves(game.getBoard(), selectedCell));
                view.updateIcon();
                break;
            }
    }
    
    public void resize(JPanel board, JPanel boardContainer) {
        int width = boardContainer.getWidth();
        int height = boardContainer.getHeight();
        int size = Math.min(width, height);
        board.setPreferredSize(new Dimension(size, size));
        boardContainer.revalidate();
    }
    
    public void handleNewGameButton() {
        game = new Game();
        view.setController(this);
        selectedCell = null;
        view.setController(this);
        if (view.getFlipped()) {
            view.flip();
            view.setFlipped(false);
        }
        view.updateIcon();
    }

    public void handleSaveGameButton(){
        //save game
        String filename = JOptionPane.showInputDialog("Enter save file name: ");
        if (filename != null) {
            game.saveToFile(filename + ".txt");
        }
    }

    public void handleLoadGameButton(){
        //load game code
        String filename = JOptionPane.showInputDialog("Enter load file name: ");
        if (filename != null) {
            try {
                game = new Game();
                game.loadGameFromFile(filename);
                view.setFlipped(game.getCurrenTurnIsYellow());
                view.flip();
                view.updateIcon();
            } catch (FileNotFoundException e) {
            }
        }
    }

    public void winGame(String winner) {
        view.displayWinner(winner);
        handleNewGameButton();
    }

    public void handleExitButton() {
        System.exit(0);
    }
}