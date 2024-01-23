import java.util.ArrayList;

public class PointPiece extends Piece {
    private boolean directionUp = false; // true is up, false is down

    public PointPiece(boolean yellow, boolean directionUp) {
        super(yellow);
        this.directionUp = directionUp;
    }

    // Setters and Getters for direction
    public void setDirectionUp(boolean directionUp) {
        this.directionUp = directionUp;
    }
    public boolean getDirectionUp() {
        return directionUp;
    }

    // Get all moves of the piece from the current position
    public ArrayList<Move> getAllMoves(Board board, Cell start) {
        ArrayList<Move> moves = new ArrayList<>();

        // Point piece can only move forward, 1 or 2 steps, depending on direction
        if (directionUp) {
            moves.addAll(getMove(board, start, 0, 1));
            moves.addAll(getMove(board, start, 0, 2));
        }
        else {
            moves.addAll(getMove(board, start, 0, -1));
            moves.addAll(getMove(board, start, 0, -2));
        }

        return moves;
    }

    // Get moves in direction x, y
    public ArrayList<Move> getMove(Board board, Cell start, int x, int y) {
        ArrayList<Move> moves = new ArrayList<>();
        int currentX = start.getX() + x;
        int currentY = start.getY() + y;

        // Check if the move is within the board bounds
        if (isValidMove(currentX, currentY)) {
            Cell moveToCell = new Cell(currentX, currentY, this);
            Piece pieceInCell = board.getCell(currentX, currentY).getPiece();

            // Check if the destination cell is empty or has an enemy piece
            if (pieceInCell == null || pieceInCell.isYellow() != this.isYellow()) {
                moves.add(new Move(this.isYellow(), start, moveToCell));
            }
        }

        return moves;
    }

    // Check if the move is within the board bounds
    private boolean isValidMove(int x, int y) {
        return x >= 0 && x <= 6 && y >= 0 && y <= 5;
    }
}

