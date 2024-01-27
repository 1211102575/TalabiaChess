import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Game {
    private Board board;
    private int turns;
    private boolean currentTurnIsYellow;
    private String gameState; // "IN_PLAY" "YELLOW_WIN" "BLUE_WIN"
    private ArrayList<Move> moves;
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

    // When a player selects a cell on the board
    // 'n' = clear board, 'x' = return error, 'm' = make move, 'h' = highlight moves
    public char selectCell(Cell cell) {
        Piece piece = cell.getPiece();
        if (previousCell == null) {
            if (piece == null) { // if no piece in cell
                return 'n';
            } else if (piece.isYellow() != currentTurnIsYellow) { // if piece selected is not yellow
                System.out.println("Please select the piece according to turn!");
                return 'x';
            }
            previousCell = cell;
        } else {
            if (!moves.isEmpty()) { // if no piece have not been selected
                for (Move move : moves) {
                    if (move.getEnd().getRow() == cell.getRow() && move.getEnd().getCol() == cell.getCol()) { // if
                        // valid
                        // move is
                        // made
                        changeState(move);
                        makeMove(move);
                        moves.clear();
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
                ((PointPiece) movePiece).changeDirectionUp();
            }
        }

        board.getCell(eRow, eCol).setPiece(movePiece);
    }

    public void changeState(Move move) {
        // Next player turn
        currentTurnIsYellow = !currentTurnIsYellow;
        // Add turns
        turns++;
        if (turns % 4 == 0) {
            transformPiece();
        }

        // Win condition check
        Cell endCell = move.getEnd();
        int endRow = endCell.getRow();
        int endCol = endCell.getCol();
        endCell = board.getCell(endRow, endCol);
        Piece endPiece = endCell.getPiece();

        if (endPiece instanceof SunPiece) {
            if (endPiece.isYellow()) { // If Yellow's SunPiece captured
                gameState = "BLUE_WIN";
                return;
            }
            // If Blue's SunPiece captured
            gameState = "YELLOW_WIN";
            return;
        }
        System.out.println("Success");
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
                } else if (piece instanceof PlusPiece) {
                    cell.setPiece(new TimePiece(cell.getPiece().isYellow()));
                }

                board.setCell(row, col, cell);
            }
        }
    }

    // Save the current state of the game
    public String saveGame() {
        StringBuilder savedGame = new StringBuilder();

        // Append turns, currentTurnIsYellow, and gameState
        savedGame.append("Turns: ").append(turns).append("\n");
        savedGame.append("Current Player: ").append(currentTurnIsYellow ? "Yellow" : "Blue").append("\n");
        savedGame.append("Game State: ").append(gameState).append("\n");

        // Append board state
        savedGame.append("Board State:\n");
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                Cell cell = board.getCell(row, col);
                Piece piece = cell.getPiece();

                // Append piece information
                savedGame.append("(").append(row).append(",").append(col).append("): ");
                if (piece == null) {
                    savedGame.append("Empty");
                } else {
                    savedGame.append(piece.getClass().getSimpleName()).append(" (")
                            .append(piece.isYellow() ? "Yellow" : "Blue").append(")");
                }
                savedGame.append("\t");
            }
            savedGame.append("\n");
        }

        return savedGame.toString();
    }

    public void saveToFile(String fileName) {
        try {
            Files.write(Path.of(fileName), saveGame().getBytes(), StandardOpenOption.CREATE);
            System.out.println("Game saved to " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the game from a saved state
    public void loadGame(String saveData) {
        String[] lines = saveData.split("\n");

        for (String line : lines) {
            if (line.startsWith("Turns:")) {
                this.turns = Integer.parseInt(line.split(":")[1].trim());
            } else if (line.startsWith("Current Player:")) {
                this.currentTurnIsYellow = line.split(":")[1].trim().equals("Yellow");
            } else if (line.startsWith("Game State:")) {
                this.gameState = line.split(":")[1].trim();
            } else if (line.startsWith("Board State:")) {
                // Skip the header line
                continue;
            } else {
                // Parse board state
                String[] tokens = line.trim().split("\t");
                for (String token : tokens) {
                    String[] parts = token.substring(1, token.length() - 1).split(":");
                    int row = Integer.parseInt(parts[0].split(",")[0]);
                    int col = Integer.parseInt(parts[0].split(",")[1]);
                    String pieceType = parts[1].trim();  // Adjust if necessary
                    boolean isYellow = parts[2].trim().equals("Yellow");  // Adjust if necessary
                    // Assuming you have a board object and a method placePiece in your Board class
                    this.board.placePiece(createPiece(pieceType, isYellow), row, col);
                }
            }
        }
    }

    private Piece createPiece(String pieceType, boolean isYellow) {
        // Instantiate the appropriate subclass of Piece based on the pieceType
        switch (pieceType) {
            case "PointPiece":
                return new PointPiece(isYellow);
            case "TimePiece":
                return new TimePiece(isYellow);
            case "PlusPiece":
                return new PlusPiece(isYellow);
            // Add more cases for other piece types if needed
            default:
                throw new IllegalArgumentException("Unknown piece type: " + pieceType);
        }
    }
    
    public void loadGameFromFile(String filePath) {
        try {
            String savedState = new String(Files.readAllBytes(Paths.get(filePath)));
            loadGame(savedState);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
