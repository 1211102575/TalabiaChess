import java.util.*;
import java.io.FileNotFoundException;
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
    public String getGameState() {
        return gameState;
    }
    public boolean getCurrenTurnIsYellow() {
        return currentTurnIsYellow;
    }

    // When a player selects a cell on the board
    // 'n' = clear board, 'x' = return error, 'm' = make move, 'h' = highlight moves
    public char selectCell(Cell cell) {
        Piece piece = cell.getPiece();
        if (previousCell == null) {
            if (piece == null) { // if no piece in cell
                return 'n';
            }
            else if (piece.isYellow() != currentTurnIsYellow) {    // if piece selected is not yellow
                System.out.println("Please select the piece according to turn!");
                return 'x';
            }
            previousCell = cell;
        }
        else {
            if (!moves.isEmpty()) { // if no piece have not been selected
                for (Move move : moves) {
                    if (move.getEnd().getRow() == cell.getRow() && move.getEnd().getCol() == cell.getCol()) { // if valid move is made
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
        //piece
        //board.setCell(sRow, sCol, null);
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
        
        Piece endPiece = board.getCell(eRow, eCol).getPiece();
        if (endPiece instanceof SunPiece) {
            if (endPiece.isYellow()) {    // If Yellow's SunPiece captured
                gameState = "BLUE_WIN!!!";
            }
            else {    // If Blue's SunPiece captured
                gameState = "YELLOW_WIN!!!";
            }
        }
        board.getCell(eRow, eCol).setPiece(movePiece);
    }

    public void changeState(Move move) {
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
                    savedGame.append("null").append(" (").append("null").append(")");
                } 
                else {
                    if (piece instanceof PointPiece) {
                        PointPiece pointPiece = (PointPiece) piece;
                        savedGame.append(pointPiece.getClass().getSimpleName()).append(" (").append(pointPiece.isYellow() ? "Yellow" : "Blue").append(")").append(" (").append(pointPiece.getDirectionUp() ? "Up" : "Down").append(")");
                    }
                    else {
                        savedGame.append(piece.getClass().getSimpleName()).append(" (").append(piece.isYellow() ? "Yellow" : "Blue").append(")");
                    }
                }
                savedGame.append("\t");
            }
            savedGame.append("\n");
        }

        return savedGame.toString();
    }

    public void saveToFile(String fileName) {
        try {
            Path path = Paths.get(fileName);
            Files.deleteIfExists(path);
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
                    String[] parts = token.split(":", 2);
                    String cellData = parts[0].substring(1, parts[0].length() - 1);
                    String[] coordinates = cellData.split(",");
                    int row = Integer.parseInt(coordinates[0]);
                    int col = Integer.parseInt(coordinates[1]);
                    String[] pieceData = parts[1].trim().split(" ", 3);
                    String pieceType = pieceData[0];
                    if (pieceType.equals("null")) {
                        board.setCell(row, col, new Cell(row, col, null));
                    }
                    else if (pieceType.equals("PointPiece")){
                        boolean isYellow = pieceData[1].substring(1, pieceData[1].length() - 1).equals("Yellow");
                        boolean isUp = pieceData[2].substring(1, pieceData[2].length() - 1).equals("Up");
                        this.board.setCell(row, col, new Cell(row, col, createPiece(pieceType, isYellow, isUp)));
                    }
                    else {
                        boolean isYellow = pieceData[1].substring(1, pieceData[1].length() - 1).equals("Yellow");
                        // Set Cell using information
                        this.board.setCell(row, col, new Cell(row, col, createPiece(pieceType, isYellow)));
                    }   
                }
            }
        }
    }

    private Piece createPiece(String pieceType, boolean isYellow) {
        // Instantiate the appropriate subclass of Piece based on the pieceType
        switch (pieceType) {
            case "TimePiece":
                return new TimePiece(isYellow);
            case "PlusPiece":
                return new PlusPiece(isYellow);
            case "HourglassPiece":
                return new HourglassPiece(isYellow);
            case "SunPiece":
                return new SunPiece(isYellow);
            // Add more cases for other piece types if needed
            default:
                throw new IllegalArgumentException("Unknown piece type: " + pieceType);
        }
    }

    private Piece createPiece(String pieceType, boolean isYellow, boolean isUp) {
        // Instantiate the appropriate subclass of Piece based on the pieceType
        switch (pieceType) {
            case "PointPiece":
                return new PointPiece(isYellow, isUp);
            // Add more cases for other piece types if needed
            default:
                throw new IllegalArgumentException("Unknown piece type: " + pieceType);
        }
    }

    public void loadGameFromFile(String filePath) throws FileNotFoundException {
        try {
            String savedState = new String(Files.readAllBytes(Paths.get(filePath + ".txt")));
            System.out.println("Load success");
            loadGame(savedState);
            System.out.println("laod game success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
