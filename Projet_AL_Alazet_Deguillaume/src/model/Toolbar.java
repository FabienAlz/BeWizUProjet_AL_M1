package model;

import java.util.ArrayList;
import java.util.List;

public final class Toolbar {
    private static Toolbar instance;
    private List<Shape> shapes;
    private Toolbar() {
        shapes = new ArrayList<>();
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
        s.notifyObserver();
    }

    public void clear() {
        shapes.clear();
    }
}
