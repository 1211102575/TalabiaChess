public class BoardController {
    private BoardView view = new BoardView();
    private Game game = new Game();
    private Cell selectedCell;

    //add
    private boolean moved;

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
        // if (selectedCell == null) {
        selectedCell = game.getBoard().getCell(row, col);
        char signal = game.selectCell(selectedCell);
        switch (signal) {
            case 'n':
                selectedCell = null;
                view.clearSelectedBorder();

                break;
            case 'x':
                //errorhandling
                selectedCell = null;
                break;
            case 'm':
                view.clearSelectedBorder();
                view.clearAvailableMove();
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
}
