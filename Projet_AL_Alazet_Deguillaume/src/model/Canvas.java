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
    }

    public void remove(Shape s) {
        shapes.remove(s);
    }

    public void clear() {
        shapes.clear();
    }
}
