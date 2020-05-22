package model;

public class ConcreteShapeObserver implements ShapeObserver{

    /**
     * Draws the Shapes
     * @param o
     */
    @Override
    public void update(ObservableSuperClass o) {
        o.getImplementor().draw(o);
        return;
    }
}
