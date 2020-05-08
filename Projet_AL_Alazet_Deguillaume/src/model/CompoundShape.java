package model;

import java.util.List;

public class CompoundShape extends AbstractShape {
    private List<Shape> shapes;

    public CompoundShape(PositionI positionI, float rotation, PositionI rotationCenter, PositionI translation, String color, Implementor implementor) {
        super(positionI, rotation, rotationCenter, translation, color, implementor);
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
