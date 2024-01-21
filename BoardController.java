public class BoardController {
    private Board model;
    private BoardView view;
    private Cell selectedCell;

    public BoardController(Board model, BoardView view) {
        this.model = model;
        this.view = view;
        selectedCell = null;
    }

    public Piece getPiece(int x, int y) {
        return model.getCell(x, y).getPiece();
    }

    public void handleCellButtonClick(int x, int y) {
        if (selectedCell == null) {
            selectedCell = model.getCell(x, y);
            view.setSelectBorder();
            view.showAvailableMove(selectedCell.getPiece().getAllMoves(model, model.getCell(x, y)));
        }
        else {
            //move
            new Move(selectedCell.getPiece().isYellow(), selectedCell, model.getCell(x, y));
            selectedCell = null;
        }
    }

    // public void checkMovement(int x, int y) {
    //     boolean yellowTurn = model.getCell(x, y).getPiece().isYellow();
    //     if (model.getCell(x, y).getPiece() == null) {
    //         if (model.getCell(x, y).getPiece() != null && model.getCell(x, y).getPiece().getIsWhite() == whiteTurn) {
    //             model.setselectedCell(model.getPiece(x, y));
    //         }
    //     } 
    //     else {
    //         if (selectedCell.canMove(x, y)) {
    //             if (model.getPiece(x, y) == null || model.getPiece(x, y).getIsWhite() != whiteTurn) {
    //                 model.movePiece(selectedCell.getX(), selectedCell.getY(), x, y);
    //                 model.setSelectedPiece(null);
    //                 model.setWhiteTurn(!whiteTurn);
    //             }
    //         }
    //     }
    // }
}
