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

    //Setters
    public void setYellow(boolean yellow) {
        this.yellow = yellow;
    }
    public void setStart(Cell start) {
        this.start = start;
    }
    public void setEnd(Cell end) {
        this.end = end;
    }
    public void setMovePiece(Piece movePiece) {
        this.movePiece = movePiece;
    }

    //Getters
    public boolean getYellow() {
        return yellow;
    }
    public Cell getStart() {
        return start;
    }
    public Cell getEnd() {
        return end;
    }
    public Piece getMovePiece() {
        return movePiece;
    }
}