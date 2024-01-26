import java.util.*;

public class Game {
    private Board board;
    private int turns;
    private boolean currentTurnIsYellow;
    private String gameState; // "IN_PLAY" "YELLOW_WIN" "BLUE_WIN"
    private ArrayList<Move> moves; // Arraylist to store the moves of the piece that is selected
    private Cell previousCell;

    // Initialize game
    public Game() {
        this.board = new Board();
        this.turns = 0;
        this.currentTurnIsYellow = true;
        this.gameState = "IN_PLAY";
        this.moves = new ArrayList<>();
    }

    // Get Board instance from Game instance
    public Board getBoard() {
        return board;
    }

    //  When a player selects a cell on the board
    // 'n' = clear board, 'x' = return error, 'm' = make move, 'h' = highlight moves
    public char selectCell(Cell cell) {
        Piece piece = cell.getPiece();
        if (previousCell == null) {
            if (piece == null) {    // if no piece in cell
                return 'n';
            }
            else if (piece.isYellow() != currentTurnIsYellow) {    // if piece selected is not yellow
                System.out.println("Please select the piece according to turn!");
                return 'x';
            }
            previousCell = cell;
        }
        else {
            if (!moves.isEmpty()) {    // if no piece have not been selected
                for (Move move : moves) {
                    if (move.getEnd().getRow() == cell.getRow() && move.getEnd().getCol() == cell.getCol()) {    // if valid move is made
                        makeMove(move);
                        moves.clear();
                        changeState(move);
                        previousCell = null;
                        return 'm';
                    }
                }
            }
            // if move made is not valid
            previousCell = null;
            return 'n';
        }
        
        // if select a piece to make move, then highlight all possible moves
        moves = piece.getAllMoves(board, cell);
        return 'h';
    }

    // Make move in board instance
    public void makeMove(Move move) {
        Cell start = move.getStart();
        Cell end = move.getEnd();
        Piece movePiece = start.getPiece();

        // Starting piece position
        int sRow = start.getRow();
        int sCol = start.getCol();
        board.getCell(sRow, sCol).setPiece(null);

        // Ending piece position
        int eRow = end.getRow();
        int eCol = end.getCol();
        // Check if point piece reach the end
        if (movePiece instanceof PointPiece) {
            if (end.getRow() == 0 || end.getRow() == 5) {
                ((PointPiece)movePiece).changeDirectionUp();
            }
        }

        board.getCell(eRow, eCol).setPiece(movePiece);
    }

    public void changeState(Move move) {
        Cell endCell = move.getEnd();
        Piece endPiece = endCell.getPiece();

        if (endPiece instanceof SunPiece) {
            if (endPiece.isYellow()) {    // If Yellow's SunPiece captured
                gameState = "BLUE_WIN";
                return;
            }
            // If Blue's SunPiece captured
            gameState = "YELLOW_WIN";
            return;
        }

        // Next player turn
        currentTurnIsYellow = !currentTurnIsYellow;

        turns ++;
        if (turns%4 == 0) {
            transformPiece();
        }
    }

    // Transform piece every 2 turns
    public void transformPiece() {    
        Cell cell;
        Piece piece;

        for (int row = 0; row <= 5; row++) {
            for (int col = 0; col <= 6; col++) {
                cell = board.getCell(row, col);
                piece = cell.getPiece();
                
                if (piece instanceof TimePiece) {
                    cell.setPiece(new PlusPiece(cell.getPiece().isYellow()));
                }
                else if (piece instanceof PlusPiece) {
                    cell.setPiece(new TimePiece(cell.getPiece().isYellow()));
                }

                board.setCell(row, col, cell);
            }
        }
    }
}
