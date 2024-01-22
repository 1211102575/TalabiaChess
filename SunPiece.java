import java.util.ArrayList;

public class SunPiece extends Piece {

    public SunPiece(boolean yellow) {
        super(yellow);
    }

    // Get all moves of the piece from the current position
    public ArrayList<Move> getAllMoves(Board board, Cell start) {
        ArrayList<Move> moves = new ArrayList<>();

        moves.addAll(getMove(board, start, 1, 0));
        moves.addAll(getMove(board, start, -1, 0));
        moves.addAll(getMove(board, start, 0, 1));
        moves.addAll(getMove(board, start, 0, -1));
        moves.addAll(getMove(board, start, 1, 1));
        moves.addAll(getMove(board, start, -1, -1));
        moves.addAll(getMove(board, start, 1, -1));
        moves.addAll(getMove(board, start, -1, 1));

        return moves;
    }

    // Get moves in direction x, y
    public ArrayList<Move> getMove(Board board, Cell start, int x, int y) {
        ArrayList<Move> moves = new ArrayList<>();
        int currentX = start.getX() + x;
        int currentY = start.getY() + y;

        // Check if the move is within the board bounds
        if (currentX >= 0 && currentX <= 6 && currentY >= 0 && currentY <= 5) {
            Cell moveToCell = new Cell(currentX, currentY, this);
            Piece pieceInCell = board.getCell(currentX, currentY).getPiece();

            // Check if the destination cell is empty or has an enemy piece
            if (pieceInCell == null || pieceInCell.isYellow() != this.isYellow()) {
                moves.add(new Move(this.isYellow(), start, moveToCell));
            }
        }

        return moves;
    }
}
