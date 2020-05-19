package model;

import java.util.ArrayList;
import java.util.List;

public final class Canvas {
    private static Canvas instance;
    private List<Shape> shapes;
    private Position startSelectPos = new Position(0, 0);
    private boolean selection = false;

    /**
     * Singleton constructor
     */
    private Canvas() {
        shapes = new ArrayList<>();
    }

    /**
     * Singleton pattern
     *
     * @return a new Canvas if it's the first time it's called, the previously created instance otherwise
     */
    public static Canvas getInstance() {
        if (instance == null) {
            instance = new Canvas();
        }

        return instance;
    }

    /******************************
     *          GETTERS           *
     ******************************/
    public List<Shape> getShapes() {
        return shapes;
    }

    public Position getStartSelectPos() {
        return startSelectPos;
    }

    public boolean getSelection() {
        return selection;
    }

    public Shape getShape(long id) {
        for (Shape s : shapes) {
            if (s.getId() == id) return s;
        }
        return null;
    }

    /******************************
     *          SETTERS           *
     ******************************/
    public void setStartSelectPos(double x, double y) {
        startSelectPos.setX(x);
        startSelectPos.setY(y);
    }

    public void setSelection(boolean selection) {
        this.selection = selection;
    }

    /**
     * Creates a list of Shapes to group together and removes them from the Canvas
     *
     * @return a list of Shapes to group together
     */
    public List<Shape> createCompound() {
        List<Shape> compoundShape = new ArrayList<>();
        for (Shape s : shapes) {
            if (s.isSelected()) {
                s.setSelected(false);
                compoundShape.add(s);
            }
        }
        for (Shape s : compoundShape) {
            shapes.remove(s);
        }
        return compoundShape;
    }

    /**
     * Resets the selection of all the Shapes
     */
    public void resetSelection() {
        for (Shape s : shapes) {
            s.setSelected(false);
        }
    }

    /**
     * Checks if the canvas contains a Shape given its id
     *
     * @param id the id of the Shape
     * @return true if the Shape is in the canvas, false otherwise
     */
    public boolean contains(long id) {
        for (Shape s : shapes) {
            if (s.getId() == id) return true;
        }
        return false;
    }

    /**
     * Remove a Shape from the canvas given its id
     *
     * @param id the id of the Shape to remove
     */
    public void removeById(long id) {
        for (Shape s : shapes) {
            if (s.getId() == id) {
                shapes.remove(s);
                break;
            }
        }
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
     * Add a Shape in the canvas and notifies its observer
     *
     * @param s the Shape to add and notify
     */
    public void addAndNotify(Shape s) {
        shapes.add(s);
        s.notifyObserver();
    }

    /**
     * Adds a shape in the canvas
     *
     * @param s the Shape to add
     */
    public void add(Shape s) {
        shapes.add(s);
    }

    /**
     * Remove a Shape from the canvas
     *
     * @param s the Shape to remove
     */
    public void remove(Shape s) {
        shapes.remove(s);
    }

    /**
     * Clears the canvas of all its Shapes
     */
    public void clear() {
        shapes.clear();
    }
}
