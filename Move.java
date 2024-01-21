public class Move {
    private boolean yellow;
    private Cell start;
    private Cell end;
    private Piece movePiece;

    public Move(boolean yellow, Cell start, Cell end) {
        this.yellow = yellow;
        this.start = start;
        this.end = end;
        this.movePiece = start.getPiece();
    }
}