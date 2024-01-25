public class Cell {
    private int row;
    private int col;
    private Piece piece;

    public Cell(int row, int col, Piece p) {
        this.row = row;
        this.col = col;
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
    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }
}
