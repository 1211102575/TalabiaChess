import javax.swing.Icon;
import javax.swing.ImageIcon;

public class PieceIconVisitor implements PieceVisitor {
    private Icon icon;

    public void visit(SunPiece sunPiece) {
        if (sunPiece.isYellow()) {
            icon = new ImageIcon("chess pieces/yellow_Sun.png");
        } else {
            icon = new ImageIcon("chess pieces/blue_Sun.png");
        }
    }

    public void visit(TimePiece timePiece) {
        if (timePiece.isYellow()) {
            icon = new ImageIcon("chess pieces/yellow_Times.png");
        } else {
            icon = new ImageIcon("chess pieces/blue_Times.png");
        }
    }

    public void visit(HourglassPiece hourglassPiece) {
        if (hourglassPiece.isYellow()) {
            icon = new ImageIcon("chess pieces/yellow_Hourglass.png");
        } else {
            icon = new ImageIcon("chess pieces/blue_Hourglass.png");
        }
    }

    public void visit(PlusPiece plusPiece) {
        if (plusPiece.isYellow()) {
            icon = new ImageIcon("chess pieces/yellow_Plus.png");
        } else {
            icon = new ImageIcon("chess pieces/blue_Plus.png");
        }
    }

    public void visit(PointPiece pointPiece) {
        if (pointPiece.isYellow()) {
            if (pointPiece.getDirectionUp()) {
                icon = new ImageIcon("chess pieces/yellow_Point_Up.png");
            }
            else {
                icon = new ImageIcon("chess pieces/yellow_Point_Down.png");
            }
        } else {
            if (pointPiece.getDirectionUp()) {
                icon = new ImageIcon("chess pieces/blue_Point_Up.png");
            }
            else {
                icon = new ImageIcon("chess pieces/blue_Point_Down.png");
            }
        }
    }

    public void visitFliped(PointPiece pointPiece) {
        if (pointPiece.isYellow()) {
            if (!pointPiece.getDirectionUp()) { // Notice the '!'
                icon = new ImageIcon("chess pieces/yellow_Point_Up.png");
            }
            else {
                icon = new ImageIcon("chess pieces/yellow_Point_Down.png");
            }
        } else {
            if (!pointPiece.getDirectionUp()) { // Notice the '!'
                icon = new ImageIcon("chess pieces/blue_Point_Up.png");
            }
            else {
                icon = new ImageIcon("chess pieces/blue_Point_Down.png");
            }
        }
    }

    public Icon getIcon() {
        return icon;
    }
}
