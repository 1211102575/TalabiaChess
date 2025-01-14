// Pang Ding Yuan

// The Visitor interface in Visitor pattern
public interface PieceVisitor {
    void visit(SunPiece sunPiece);
    void visit(TimePiece timePiece);
    void visit(HourglassPiece hourglassPiece);
    void visit(PlusPiece plusPiece);
    void visit(PointPiece pointPiece);
}
