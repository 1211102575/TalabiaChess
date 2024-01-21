// public class Time extends Piece{
//     public Time(String type, int x, int y, boolean isWhite){
//         super(type,x,y,isWhite);
//     }
    
//     public boolean canMove(Board board, Piece piece, int newX, int newY){
//         if(board.getPiece(newX, newY) != null){
//             return false ;
//         }
//         else if(Math.abs(newX - piece.getX()) != Math.abs(newY - piece.getY())){
//             return false;
//         }
//         else{
//             //change the location on the board first to delete the old piece
//             board.setPiece(piece, newX, newY);
//             //change location
//             piece.setX (newX);
//             piece.setY(newY);
//             return true;
//         }
//     }
//     public void changeState(Piece piece,int turn, int x , int y ){
//         if (turn % 2 == 0 ){
//             piece = new Plus("plus", piece.getX(), piece.getY(), piece.getIsWhite()) ;
//         }
//     }
// }  
    
    