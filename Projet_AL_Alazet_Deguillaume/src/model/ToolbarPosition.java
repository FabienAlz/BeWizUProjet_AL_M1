package model;

public class ToolbarPosition implements PositionI {

    private double x, y;

    public ToolbarPosition() {
        ToolbarPosition nextPosition = Toolbar.getInstance().getNextPosition();
        this.x = nextPosition.getX();
        this.y = nextPosition.getY();
    }

    public ToolbarPosition(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
}
