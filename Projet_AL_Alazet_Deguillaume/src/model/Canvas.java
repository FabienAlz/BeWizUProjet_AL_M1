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

    public void add(Shape s) {
        shapes.add(s);
        s.notifyObserver();
    }

    public void remove(Shape s) {
        shapes.remove(s);
        s.notifyObserver();
    }

    public void clear() {
        shapes.clear();
    }
}
