package model;

public class ToolbarPosition implements PositionI {

    private int x, y;

    public ToolbarPosition() {
        ToolbarPosition nextPosition = Toolbar.getInstance().getNextPosition();
        this.x = nextPosition.getX();
        this.y = nextPosition.getY();
    }

    public ToolbarPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
}
