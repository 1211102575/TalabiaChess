public class Cell {
    private int x;
    private int y;
    private Piece piece;

    public Cell(int x, int y, Piece p) {
        this.x = x;
        this.y = y;
        this.piece = p;
    }

    //set and get for piece
    public void setPiece(Piece p) {
        this.piece = p;
    }
    public Piece getPiece() {
        return this.piece;
    }

    //get for coordinates
    public int getX() {
        return this.x;
    }
    public int getY() {
        return this.y;
    }
}
