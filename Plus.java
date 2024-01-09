public class Plus extends Piece{
    public Plus(String type, int x, int y, boolean isWhite){
        super(type,x,y,isWhite);
    }
    
    public boolean canMove(Board board, Piece piece, int newX, int newY){
        //not for enemy(havenet complete)
        if(board.getPiece(newX, newY) != null){
            return false ;
        }
        else if((newX != piece.getX() && newY != piece.getY() ||newX == piece.getX() && newY == piece.getY())){
            return false;
        }
        else{
            //change the location on the board first to delete the old piece
            board.setPiece(piece, newX, newY);
            //change location
            piece.setX (newX);
            piece.setY(newY);
            return true;
        }
    }
    public void changeState(Piece piece,int turn, int x , int y ){
        if (turn % 2 == 0 ){
            piece = new Time("time", piece.getX(), piece.getY(), piece.getIsWhite()) ;
        }
    }
}  
    
    