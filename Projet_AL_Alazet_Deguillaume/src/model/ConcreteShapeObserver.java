package model;

import java.io.Serializable;

public class ConcreteShapeObserver implements ShapeObserver, Serializable {

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
