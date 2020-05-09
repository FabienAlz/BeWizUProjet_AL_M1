package model;

import java.util.ArrayList;
import java.util.List;

public final class Toolbar {
    private static Toolbar instance;
    private List<Shape> shapes;
    private ToolbarPosition nextPosition;


    private Toolbar() {
        shapes = new ArrayList<>();
        nextPosition = new ToolbarPosition(10,10);
    }


    public static Toolbar getInstance() {
        if(instance == null)
            instance = new Toolbar();
        return instance;
    }

    public void add(Shape s) {
        shapes.add(s);
        s.notifyObserver();
    }

    public void remove(Shape s) {
        shapes.remove(s);
    }

    public void clear() {
        shapes.clear();
    }

    public List<Shape> getShapes() {
        List<Shape> copy = new ArrayList<>();
        for(Shape s : shapes) {
            copy.add(s.clone());
        }
        return copy;
    }

    public ToolbarPosition getNextPosition() {
        ToolbarPosition currentPosition = new ToolbarPosition(nextPosition.getX(), nextPosition.getY());
        return currentPosition;
    }

    public void setNextPosition(int height) {
        this.nextPosition = new ToolbarPosition(nextPosition.getX(), nextPosition.getY()+10+height);
    }

    public void resetPosition() {
        this.nextPosition = new ToolbarPosition(10, 10);
    }
}
