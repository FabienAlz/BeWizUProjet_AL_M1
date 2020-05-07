package model;

public class ConcreteShapeObserver implements ShapeObserver {

    //TODO
    @Override
    public void update(ObservableSuperClass o) {
        // refresh la view les boutons et jspquoi (genre shape.getImplementor.draw et .remove tmtc bonne chance)
        o.getImplementor().draw(o);
        return;
    }
}
