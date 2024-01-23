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

    //Get moves in direction row, col
    public ArrayList<Move> getMove(Board board, Cell start, int row, int col) {
        //Initiallize variables
        ArrayList<Move> moves = new ArrayList<>(); 
        int currentRow = start.getRow();
        int currentCol = start.getCol();
        boolean cond = true;
        Cell currentCell;
        Piece pieceInCell;
        Cell moveToCell;

        //Start finding
        while (cond) {
            //Move in direction
            currentRow += row;
            currentCol += col;

            //Get information
            currentCell = board.getCell(currentRow, currentCol);
            pieceInCell = currentCell.getPiece();
            //This piece move to cell
            moveToCell = new Cell(currentRow, currentCol, this);

            //Out of bounds, then break out
            if (currentRow < 0 || currentRow > 5 || currentCol < 0 || currentCol > 6) {
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
    
