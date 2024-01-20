public class Board {
    Cell[][] cells;

    public Board() {
        this.resetBoard();
    }

    public Cell getCell(int x, int y) throws Exception {
        if (x < 0 || x > 6 || y < 0 || y > 5) {
            throw new Exception("Index greater than board size"); 
        }

        return cells[x][y];
    }

    public void resetBoard() {
        cells[0][0] = new Cell(0, 0, new Plus(false));
        cells[1][0] = new Cell(1, 0, new Hour(false));

        for (int i = 0; i <= 7; i++) {
            for (int j = 2; j <= 3; j++) {
                cells[i][j] = new Cell(i, j, null);
            }
        }
    }



}
