public class Board {
    Cell[][] cells;

    public Board() {
        this.resetBoard();
    }

    public Cell getCell(int x, int y) {
        return cells[x][y];
    }

    public void resetBoard() {
        cells[0][0] = new Cell(0, 0, new PlusPiece(false));
        //cells[1][0] = new Cell(1, 0, new Hour(false));

        for (int i = 0; i <= 7; i++) {
            for (int j = 2; j <= 3; j++) {
                cells[i][j] = new Cell(i, j, null);
            }
        }
    }



}
