// Pang Ding Yuan

public interface PieceVisitor {
    void visit(SunPiece sunPiece);
    void visit(TimePiece timePiece);
    void visit(HourglassPiece hourglassPiece);
    void visit(PlusPiece plusPiece);
    void visit(PointPiece pointPiece);
}
