public class BoardController {
    private Board model;
    private BoardView view;
    private Cell selectedCell;

    public BoardController(Board model, BoardView view) {
        this.model = model;
        this.view = view;
        selectedCell = null;
    }

    public Piece getPiece(int row, int col) {
        return model.getCell(row, col).getPiece();
    }

    public void handleCellButtonClick(int row, int col) {
        if (selectedCell == null) {
            selectedCell = model.getCell(row, col);
            view.setSelectBorder();
            view.showAvailableMove(selectedCell.getPiece().getAllMoves(model, selectedCell));
            view.updateIcon();
        }
        else {
            //move
            Move selectedMove = new Move(selectedCell.getPiece().isYellow(), selectedCell, model.getCell(row, col));
            for (Move move : selectedCell.getPiece().getAllMoves(model, selectedCell)) {
                if ((selectedMove.getEnd()).equals(move.getEnd())) {
                    System.out.println("???");
                    model.getCell(row, col).setPiece(selectedCell.getPiece());
                    selectedCell = null;
                    view.updateIcon();
                }
            }
            view.clearSelectedBorder();
            view.clearAvailableMove();
        }
    }
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
