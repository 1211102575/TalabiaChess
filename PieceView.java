import javax.swing.Icon;

public class PieceView{
    private Icon icon;

    public PieceView(Piece piece) {
        PieceIconVisitor visitor = new PieceIconVisitor();
        piece.accept(visitor);
        icon = visitor.getIcon();
    }

    public Icon getIcon() {
        return icon;
    }
}