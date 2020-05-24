package model;

import java.util.ArrayList;
import java.util.List;

public class Memento {
    private List<Shape> state;

    public Memento(List<Shape> state) {
        this.state = new ArrayList<>();
        for (Shape s : state) {
            this.state.add(s.clone());
        }
    }

    public List<Shape> getState() {
        return this.state;
    }
}
