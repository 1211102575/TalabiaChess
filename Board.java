public class Board {
    private Cell[][] cells;

    public Board() {
        this.resetBoard();
    }

    public Cell getCell(int row, int col) {
        return cells[row][col];
    }

    public void resetBoard() {
        // Yellow bottom row pieces
        cells[0][0] = new Cell(0, 0, new PlusPiece(true));
        cells[0][1] = new Cell(0, 1, new HourglassPiece(true));
        cells[0][2] = new Cell(0, 2, new TimePiece(true));
        cells[0][3] = new Cell(0, 3, new SunPiece(true));
        cells[0][4] = new Cell(0, 4, new TimePiece(true));
        cells[0][5] = new Cell(0, 5, new HourglassPiece(true));
        cells[0][6] = new Cell(0, 6, new PlusPiece(true));

        // Yellow point pieces
        for (int i = 0; i < 7; i++) {
            cells[1][i] = new Cell(1, i, new PointPiece(true, true));
        }

        // Middle empty spaces
        for (int i = 0; i < 7; i++) {
            for (int j = 2; j <= 3; j++) {
                cells[j][i] = new Cell(j, i, null);
            }
        }

        // Blue point pieces
        for (int i = 0; i < 7; i++) {
            cells[4][i] = new Cell(4, i, new PointPiece(false, false));
        }

        // Blue bottom row pieces
        cells[6][0] = new Cell(6, 0, new PlusPiece(false));
        cells[6][1] = new Cell(6, 1, new HourglassPiece(false));
        cells[6][2] = new Cell(6, 2, new TimePiece(false));
        cells[6][3] = new Cell(6, 3, new SunPiece(false));
        cells[6][4] = new Cell(6, 4, new TimePiece(false));
        cells[6][5] = new Cell(6, 5, new HourglassPiece(false));
        cells[6][6] = new Cell(6, 6, new PlusPiece(false));
    }

    
}
