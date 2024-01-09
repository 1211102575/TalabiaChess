import javax.swing.Icon;
import javax.swing.ImageIcon;

import java.awt.image.*;

public abstract class Piece {
    private String type;
    private Icon icon;
    private int x;
    private int y;
    private boolean isWhite;
    private boolean isAlive;

    public Piece(String type, int x, int y, boolean isWhite) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.isWhite = true;
        isAlive = true;
        switch (type) {
          case "sun":
            icon =  new ImageIcon("sun.png");
            break;
          case "plus":
            icon =  new ImageIcon("plus.png");
            break;
          case "time":
            icon =  new ImageIcon("time.png");
            break;
          case "hourglass":
            icon =  new ImageIcon("hourglass.png");
            break;
          case "point":
            icon =  new ImageIcon("point.png");
            break;
        }
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public Icon getIcon() {
        return icon;
    }
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public int getX() {
        return x;
    }
    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }
    public void setY(int y) {
        this.y = y;
    }

    public boolean getIsWhite() {
        return isWhite;
    }
    public void setIsWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean getIsAlive() {
        return isAlive;
    }
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public abstract boolean canMove(Board board, Piece piece, int newX, int newY);

    //public abstract void changeState(Piece piece);
}