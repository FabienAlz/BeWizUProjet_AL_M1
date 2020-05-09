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

    public void resetSelection() {
        for(Shape s : shapes) {
            ((AbstractShape)s).setSelected(false);
        }
    }

    public void add(Shape s) {
        shapes.add(s);
        s.notifyObserver();
    }

    public void remove(Shape s) {
        shapes.remove(s);
        s.removeAllObservers();
    }

    public void clear() {
        shapes.clear();
    }
}
