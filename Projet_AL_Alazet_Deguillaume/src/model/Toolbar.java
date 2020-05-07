package model;

import java.util.ArrayList;
import java.util.List;

public final class Toolbar {
    private Toolbar instance;
    private List<Shape> shapes;
    private Toolbar() {
        shapes = new ArrayList<>();
    }

    public Toolbar getInstance() {
        if(instance == null)
            instance = new Toolbar();
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
