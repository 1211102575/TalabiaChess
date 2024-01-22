public class Board {
    Cell[][] cells;

    public Board() {
        this.resetBoard();
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void resetBoard() {
        // Yellow bottom row pieces
        cells[0][0] = new Cell(0, 0, new PlusPiece(true));
        cells[1][0] = new Cell(1, 0, new HourglassPiece(true));
        cells[2][0] = new Cell(2, 0, new TimePiece(true));
        cells[3][0] = new Cell(3, 0, new SunPiece(true));
        cells[4][0] = new Cell(4, 0, new TimePiece(true));
        cells[5][0] = new Cell(5, 0, new HourglassPiece(true));
        cells[6][0] = new Cell(6, 0, new PlusPiece(true));

        // Yellow point pieces
        for (int i = 0; i < 7; i++) {
            cells[i][1] = new Cell(i, 1, new PointPiece(true, true));
        }

        // Middle empty spaces
        for (int i = 0; i < 7; i++) {
            for (int j = 2; j <= 3; j++) {
                cells[i][j] = new Cell(i, j, null);
            }
        }

        // Blue point pieces
        for (int i = 0; i < 7; i++) {
            cells[i][4] = new Cell(i, 4, new PointPiece(false, false));
        }

        // Blue bottom row pieces
        cells[0][6] = new Cell(0, 6, new PlusPiece(false));
        cells[1][6] = new Cell(1, 6, new HourglassPiece(false));
        cells[2][6] = new Cell(2, 6, new TimePiece(false));
        cells[3][6] = new Cell(3, 6, new SunPiece(false));
        cells[4][6] = new Cell(4, 6, new TimePiece(false));
        cells[5][6] = new Cell(5, 6, new HourglassPiece(false));
        cells[6][6] = new Cell(6, 6, new PlusPiece(false));
    }

    
}
