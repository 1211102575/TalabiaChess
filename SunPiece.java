public class SunPiece extends Piece {
    public SunPiece(String type, int x, int y, boolean isWhite) {
        super(type, x, y, isWhite);
    }
    @Override
    public boolean canMove(Board board, Piece piece, int newX, int newY) {
        int dx = newX - piece.getX();
        int dy = newY - piece.getY();
        if (dx == 1 && dy == 1) {
            Piece checkPiece = board.getPiece(newX, newY);
            if (checkPiece == null || checkPiece.getIsWhite() != this.getIsWhite()) {
                return true;
            }
        }
        return false;
    }
}
