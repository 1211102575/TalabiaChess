public abstract class Piece {
    private boolean yellow = false;  // true means yellow, false means blue

    public Piece(boolean bool) {
        this.yellow = bool;
    }

    public boolean isYellow() {
        return this.yellow;
    }

    public abstract boolean canMove(Board board, Cell start, Cell end);

    //public abstract void changeState(Piece piece);
}