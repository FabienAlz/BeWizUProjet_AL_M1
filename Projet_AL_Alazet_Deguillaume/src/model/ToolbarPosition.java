package model;

import java.io.Serializable;

public class ToolbarPosition implements PositionI, Serializable {
    private static final long serialVersionUID = 599980917088810958L;

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

    @Override
    public double getX() {
        return this.x;
    }

    @Override
    public double getY() {
        return this.y;
    }

    @Override
    public void setX(double x) {
        this.x = x;
    }

    @Override
    public void setY(double y) {
        this.y = y;
    }
}
