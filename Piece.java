import java.util.ArrayList;

// Lew Chun Men
public abstract class Piece {
    private boolean yellow = false;  // true means yellow, false means blue

    public Piece(boolean bool) {
        this.yellow = bool;
    }

    public boolean isYellow() {
        return this.yellow;
    }

    public abstract ArrayList<Move> getAllMoves(Board board, Cell start);

    // Pang Ding Yuan
    public abstract void accept(PieceVisitor visitor);
}
