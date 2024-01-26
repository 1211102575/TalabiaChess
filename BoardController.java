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
        // view.updateIcon();
        // }
        // else {
        //     selectedCell = game.getBoard().getCell(row, col);
        //     char signal = game.selectCell(selectedCell);
        //     switch (signal) {
        //         case 'n':
        //             selectedCell = null;
        //             System.out.println("??");
        //             break;
        //         case 'x':
        //             //errorhandling
        //             break;
        //         case 'm':
        //             //move
        //             view.updateIcon();
        //             break;
        //     }
            //move
            // Move selectedMove = new Move(selectedCell.getPiece().isYellow(), selectedCell, game.getBoard().getCell(row, col));
            // for (Move move : selectedCell.getPiece().getAllMoves(game.getBoard(), selectedCell)) {
            //     if ((selectedMove.getEnd()).equals(move.getEnd())) {
            //         System.out.println("???");
            //         game.getBoard().getCell(row, col).setPiece(selectedCell.getPiece());
            //         selectedCell = null;
            //         view.updateIcon();
            //     }
            // }

    }
    // }
    // public void checkMovement(int row, int col) {
    //     boolean colellowTurn = model.getCell(row, col).getPiece().iscolellow();
    //     if (model.getCell(row, col).getPiece() == null) {
    //         if (model.getCell(row, col).getPiece() != null && model.getCell(row, col).getPiece().getIsWhite() == whiteTurn) {
    //             model.setselectedCell(model.getPiece(row, col));
    //         }
    //     } 
    //     else {
    //         if (selectedCell.canMove(row, col)) {
    //             if (model.getPiece(row, col) == null || model.getPiece(row, col).getIsWhite() != whiteTurn) {
    //                 model.movePiece(selectedCell.getrow(), selectedCell.getcol(), row, col);
    //                 model.setSelectedPiece(null);
    //                 model.setWhiteTurn(!whiteTurn);
    //             }
    //         }
    //     }
    // }
}
