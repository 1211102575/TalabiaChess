import java.util.*;
import java.io.*;

public class Game implements Serializable {
    private Board board;
    private static final long serialVersionUID = 1L;
    private int turns;
    private boolean currentTurnIsYellow;
    private String gameState; // "IN_PLAY" "YELLOW_WIN" "BLUE_WIN"
    private ArrayList<Move> moves; // Arraylist to store the moves of the piece that is selected
    private static Game instance;

    // Initialize game
    private Game() {
        this.board = new Board();
        this.turns = 0;
        this.currentTurnIsYellow = true;
        this.gameState = "IN_PLAY";
        this.moves = new ArrayList<>();
    }
    
    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }
    
    public static void setInstance(Game game) {
        instance = game;
    }

    public static void saveGame(Game game) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save_game.ser"))) {
            oos.writeObject(game);
        } catch (IOException ex) {
            ex.printStackTrace();
            // Handle the exception as needed
        }
    }
    public static Game loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save_game.ser"))) {
            return (Game) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
            // Handle the exception as needed
            return null;
        }
    }

    // public Cell getCellFromBoard(Cell cell) {

    //     return cell;
    // }

    //  When a player selects a cell on the board
    // 'n' = clear board, 'x' = return error, 'm' = make move, 'h' = highlight moves
    public char selectCell(Cell cell) {
        Piece piece = cell.getPiece();

        if (piece == null) {    // if no piece in cell
            moves.clear();
            return 'n';
        }

        if (piece.isYellow() != currentTurnIsYellow) {    // if piece selected is not yellow
            System.out.println("Please select the piece according to turn!");
            return 'x';
        }

        if (!moves.isEmpty()) {    // if no piece have not been selected
            for (Move move : moves) {
                if (move.getEnd() == cell) {    // if valid move is made
                    makeMove(move);
                    moves.clear();
                    changeState(move);
                    return 'm';
                }
            }
            // if move made is not valid
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
        board.setCell(sRow, sCol, null);

        // Ending piece position
        int eRow = end.getRow();
        int eCol = end.getCol();
        end.setPiece(movePiece);
        board.setCell(eRow, eCol, end);
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

        if (turns%4 == 0) {
            transformPiece();
        }
    }

    public void transformPiece() {    // Transform piece every 2 turns
        Cell cell;
        Piece piece;
        Boolean isYellow;

        for (int row = 0; row <= 5; row++) {
            for (int col = 0; col <= 6; col++) {
                cell = board.getCell(row, col);
                piece = cell.getPiece();
                isYellow = piece.isYellow();
                
                if (piece instanceof TimePiece) {
                    cell.setPiece(new PlusPiece(isYellow));
                }
                else if (piece instanceof PlusPiece) {
                    cell.setPiece(new TimePiece(isYellow));
                }

                board.setCell(row, col, cell);
            }
        }
    }
}
