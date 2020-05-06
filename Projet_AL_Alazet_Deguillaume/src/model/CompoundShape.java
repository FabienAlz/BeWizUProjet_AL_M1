package model;

import java.util.List;

public class CompoundShape extends AbstractShape {
    private List<Shape> shapes;

    public CompoundShape(Position position, float rotation, Position rotationCenter, Position translation, String color) {
        super(position, rotation, rotationCenter, translation, color);
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

    @Override
    public CompoundShape clone() {
        return (CompoundShape) super.clone();
    }
}
