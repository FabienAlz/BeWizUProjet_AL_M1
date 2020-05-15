package model;

import java.util.ArrayList;
import java.util.List;

public final class Canvas {
    private static Canvas instance;
    private List<Shape> shapes;
    private Canvas() {
        shapes = new ArrayList<>();
    }

    public static Canvas getInstance() {
        if(instance == null)
            instance = new Canvas();
        return instance;
    }

    public List<Shape> getShapes() {
        List<Shape> copy = new ArrayList<>();
        for(Shape s : shapes) {
            copy.add(s.clone());
        }
        return copy;
    }

    public List<Shape> createCompound() {
        List<Shape> compoundShape = new ArrayList<>();
        for (Shape s : shapes) {
            if(s.isSelected()) {
                s.setSelected(false);
                compoundShape.add(s);
            }
        }
        for (Shape s : compoundShape) {
                shapes.remove(s);
        }
        return compoundShape;
    }

    public void resetSelection() {
        for(Shape s : shapes) {
            s.setSelected(false);
        }
    }

    public boolean contains(long id) {
        for (Shape s : shapes) {
            if(s.getId() == id) return true;
        }
        return false;
    }

    public Shape getShape(long id) {
        for (Shape s : shapes) {
            if(s.getId() == id) return s;
        }
        return null;
    }

    public void removeById(long id) {
        for(Shape s : shapes) {
            if(s.getId() == id) {
                shapes.remove(s);
                break;
            }
        }
    }

    public void notifyAllShapes() {
        for(Shape s : shapes) {
            s.notifyObserver();
        }
    }

    public void add(Shape s) {
        shapes.add(s);
        s.notifyObserver();
    }

    public void remove(Shape s) {
        shapes.remove(s);
        // s.removeAllObservers();
    }

    public void clear() {
        shapes.clear();
    }
}
