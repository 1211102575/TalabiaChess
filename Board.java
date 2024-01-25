public class Board {
    private Cell[][] cells;

    public Board() {
        this.resetBoard();
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }
    public void setCell(int row, int col, Cell cell) {
        this.cells[row][col] = cell;
    }

    public void resetBoard() {
        // Yellow bottom row pieces
        cells[5][0] = new Cell(5, 0, new PlusPiece(true));
        cells[5][1] = new Cell(5, 1, new HourglassPiece(true));
        cells[5][2] = new Cell(5, 2, new TimePiece(true));
        cells[5][3] = new Cell(5, 3, new SunPiece(true));
        cells[5][4] = new Cell(5, 4, new TimePiece(true));
        cells[5][5] = new Cell(5, 5, new HourglassPiece(true));
        cells[5][6] = new Cell(5, 6, new PlusPiece(true));

        // Yellow point pieces
        for (int i = 0; i < 7; i++) {
            cells[4][i] = new Cell(4, i, new PointPiece(true, true));
        }

        // Middle empty spaces
        for (int i = 0; i < 7; i++) {
            for (int j = 2; j <= 3; j++) {
                cells[j][i] = new Cell(j, i, null);
            }
        }

        // Blue point pieces
        for (int i = 0; i < 7; i++) {
            cells[1][i] = new Cell(1, i, new PointPiece(false, true));
        }

        // Blue bottom row pieces
        cells[0][0] = new Cell(0, 0, new PlusPiece(false));
        cells[0][1] = new Cell(0, 1, new HourglassPiece(false));
        cells[0][2] = new Cell(0, 2, new TimePiece(false));
        cells[0][3] = new Cell(0, 3, new SunPiece(false));
        cells[0][4] = new Cell(0, 4, new TimePiece(false));
        cells[0][5] = new Cell(0, 5, new HourglassPiece(false));
        cells[0][6] = new Cell(0, 6, new PlusPiece(false));
    }

    
}
