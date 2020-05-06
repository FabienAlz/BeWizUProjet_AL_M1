package model;

public class Memento {
    private Shape state;

    public Memento(Shape state) {
        this.state = state;
    }

    public Shape getState() {
        return this.state;
    }
}
