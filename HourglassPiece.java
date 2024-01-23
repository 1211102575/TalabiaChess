import java.util.ArrayList;

public class HourglassPiece extends Piece {

    public HourglassPiece(boolean yellow) {
        super(yellow);
    }

    // Get all moves of the piece from the current position
    public ArrayList<Move> getAllMoves(Board board, Cell start) {
        ArrayList<Move> moves = new ArrayList<>();

        moves.addAll(getMove(board, start, 2, 1));
        moves.addAll(getMove(board, start, -2, -1));
        moves.addAll(getMove(board, start, 2, -1));
        moves.addAll(getMove(board, start, -2, 1));
        moves.addAll(getMove(board, start, 1, 2));
        moves.addAll(getMove(board, start, -1, -2));
        moves.addAll(getMove(board, start, 1, -2));
        moves.addAll(getMove(board, start, -1, 2));

        return moves;
    }

    // Get moves in direction row, col
    public ArrayList<Move> getMove(Board board, Cell start, int row, int col) {
        ArrayList<Move> moves = new ArrayList<>();
        int currentRow = start.getRow() + row;
        int currentCol = start.getCol() + col;

        // Check if the move is within the board bounds
        if (currentRow >= 0 && currentRow <= 5 && currentCol >= 0 && currentCol <= 6) {
            Cell moveToCell = new Cell(currentRow, currentCol, this);
            Piece pieceInCell = board.getCell(currentRow, currentCol).getPiece();

            // Check if the destination cell is empty or has an enemy piece
            if (pieceInCell == null || pieceInCell.isYellow() != this.isYellow()) {
                moves.add(new Move(this.isYellow(), start, moveToCell));
            }
        }

        return moves;
    }
}

