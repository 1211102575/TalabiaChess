import java.util.*;

// Lew Chun Men
public class PlusPiece extends Piece{

    public PlusPiece(boolean yellow){
        super(yellow);
    }
    
    //Get all moves of piece from current position
    public ArrayList<Move> getAllMoves(Board board, Cell start) {
        ArrayList<Move> moves = new ArrayList<>(); 

        moves.addAll(getMove(board, start, 1, 0));
        moves.addAll(getMove(board, start, -1, 0));
        moves.addAll(getMove(board, start, 0, 1));
        moves.addAll(getMove(board, start, 0, -1));

        return moves;
    }

       public ArrayList<Move> getMove(Board board, Cell start, int row, int col) {
        ArrayList<Move> moves = new ArrayList<>();
        int currentRow = start.getRow() + row;
        int currentCol = start.getCol() + col;

        // Check if the move is within the board bounds
        while (isValidMove(currentRow, currentCol)) {
            Cell moveToCell = new Cell(currentRow, currentCol, this);
            Piece pieceInCell = board.getCell(currentRow, currentCol).getPiece();

            // Check if the destination cell is empty or has an enemy piece
            if (pieceInCell == null || pieceInCell.isYellow() != this.isYellow()) {
                moves.add(new Move(this.isYellow(), start, moveToCell));
            }

            // If there is a piece in the cell, break the loop
            if (pieceInCell != null) {
                break;
            }

            // Move
            currentRow += row;
            currentCol += col;
        }

        return moves;
    }

    // Check if the move is within the board bounds
    private boolean isValidMove(int row, int col) {
        return row >= 0 && row <= 5 && col >= 0 && col <= 6;
    }
    // Accept visitor for the visitor pattern
    public void accept(PieceVisitor visitor) {
        visitor.visit(this);
    }
}
    
