import javax.swing.Icon;
import javax.swing.JButton;

public class PieceView extends JButton{
    private Piece piece;

    public PieceView() {
    }

    public PieceView(Piece piece) {
        this.piece = piece;
        setIcon(piece.getIcon());
    }

}
