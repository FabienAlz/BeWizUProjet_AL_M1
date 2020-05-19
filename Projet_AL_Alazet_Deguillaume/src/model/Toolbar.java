package model;

import java.util.ArrayList;
import java.util.List;

public final class Toolbar {
    private static Toolbar instance;
    private List<Shape> shapes;
    private ToolbarPosition nextPosition;

    /**
     * Singleton constructor
     */
    private Toolbar() {
        shapes = new ArrayList<>();
        nextPosition = new ToolbarPosition(10, 10);
    }

    /**
     * Singleton Pattern
     *
     * @return a new toolbar if it's the first time called, the previously created instance otherwise
     */
    public static Toolbar getInstance() {
        if (instance == null)
            instance = new Toolbar();
        return instance;
    }

    /******************************
     *          GETTERS           *
     ******************************/
    public List<Shape> getShapes() {
        return shapes;
    }

    public Shape getShape(long id) {
        for (Shape s : shapes) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    public ToolbarPosition getNextPosition() {
        ToolbarPosition currentPosition = new ToolbarPosition(nextPosition.getX(), nextPosition.getY());
        return currentPosition;
    }

    /******************************
     *          SETTERS           *
     ******************************/
    public void setNextPosition(int height) {
        this.nextPosition = new ToolbarPosition(nextPosition.getX(), nextPosition.getY() + 10 + height);
    }

    /**
     * Notifies the observers of each Shape
     */
    public void notifyAllShapes() {
        for (Shape s : shapes) {
            s.notifyObserver();
        }
    }

    /**
     * Adds a Shape in the toolbar and notifies its observers
     *
     * @param s the Shape to add and notify
     */
    public void addAndNotify(Shape s) {
        shapes.add(s);
        s.notifyObserver();
    }

    /**
     * Adds a Shape in the toolbar
     *
     * @param s the Shape to add
     */
    public void add(Shape s) {
        shapes.add(s);
    }

    /**
     * Removes a Shape from the toolbar
     *
     * @param s the Shape to remove
     */
    public void remove(Shape s) {
        shapes.remove(s);
    }

    /**
     * Clears the toolbar of all its Shapes and resets position of the next Shape
     */
    public void clear() {
        shapes.clear();
        resetPosition();
    }

    /**
     * Checks if the Toolbar contains a Shape given its id
     *
     * @param id the id of the Shape
     * @return true if the toolbar contains the Shape, false otherwise
     */
    public boolean contains(long id) {
        for (Shape s : shapes) {
            if (s.getId() == id) return true;
        }
        return false;
    }

    /**
     * Resets the position of the elements of te toolbar
     */
    public void resetPosition() {
        this.nextPosition = new ToolbarPosition(10, 10);
    }
}
