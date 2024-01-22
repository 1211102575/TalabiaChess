import java.util.*;

public class PlusPiece extends Piece{

    public PlusPiece(boolean yellow){
        super(yellow);
    }
    
    //Get all moves of piece from current position
    public ArrayList<Move> getAllMoves(Board board, Cell start) {
        ArrayList<Move> moves = new ArrayList<>(); 

        moves.addAll(getMove(board, start, 1, 0));
        moves.addAll(getMove(board, start, -1, 0));
        moves.addAll(getMove(board, start, 0, 1));
        moves.addAll(getMove(board, start, 0, -1));

        return moves;
    }

    //Get moves in direction x, y
    public ArrayList<Move> getMove(Board board, Cell start, int x, int y) {
        //Initiallize variables
        ArrayList<Move> moves = new ArrayList<>(); 
        int currentX = start.getX();
        int currentY = start.getY();
        boolean cond = true;
        Cell currentCell;
        Piece pieceInCell;
        Cell moveToCell;

        //Start finding
        while (cond) {
            //Move in direction
            currentX += x;
            currentY += y;

            //Get information
            currentCell = board.getCell(currentX, currentY);
            pieceInCell = currentCell.getPiece();
            //This piece move to cell
            moveToCell = new Cell(currentX, currentY, this);

            //Out of bounds, then break out
            if (currentX < 0 || currentX > 6 || currentY < 0 || currentY > 5) {
                break;
            }
            
            //If piece in cell not null
            if (pieceInCell != null) {
                if (pieceInCell.isYellow() == this.isYellow()) {  //Friendly piece
                    break;
                }

                moves.add(new Move(this.isYellow(), start, moveToCell));  //Take enemy piece
                break;
            }

            //If current cell empty
            moves.add(new Move(this.isYellow(), start, moveToCell));
        }

        return moves;
    }
}
    
