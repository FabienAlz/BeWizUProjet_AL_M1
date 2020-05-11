package model;

import java.util.ArrayList;
import java.util.List;

public class CompoundShape extends ObservableSuperClass implements Shape {
    private List<Shape> shapes;
    private PositionI translation;
    private boolean isSelected;
    private Implementor implementor;

    public CompoundShape(Implementor implementor) {
        this.implementor = implementor;
        this.shapes = new ArrayList<>();
        this.translation = new Position(0,0);
        this.isSelected = false;
    }

    @Override
    public PositionI getPositionI() {
        return null;
    }

    @Override
    public void setPosition(PositionI positionI) {
    }

    @Override
    public float getRotation() {
        return 0;
    }

    @Override
    public PositionI getRotationCenter() {
        return null;
    }

    @Override
    public PositionI getTranslation() {
        return translation;
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public Implementor getImplementor() {
        return implementor;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public void setSelected(boolean b) {
        isSelected = b;
        for(Shape s : shapes) {
            s.setSelected(b);
        }
    }

    public List<Shape> getShapes() {
        List<Shape> copy = new ArrayList<>();
        for(Shape s : shapes) {
            copy.add(s.clone());
        }
        return copy;
    }

    public void add(Shape s) {
        if(s instanceof CompoundShape) {
            for (Shape shape : ((CompoundShape) s).getShapes()) {
                shape.setSelected(false);
                shapes.add(shape);
            }
        }
        else {
            s.setSelected(false);
            shapes.add(s);
        }
    }

    public void remove(Shape s) {
        shapes.remove(s);
    }

    public void clear() {
        shapes.clear();
    }



}
