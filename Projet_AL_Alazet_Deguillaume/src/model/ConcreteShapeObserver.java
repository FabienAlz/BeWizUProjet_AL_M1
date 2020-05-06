package model;

public class ConcreteShapeObserver implements ShapeObserver {
    private Shape shape;
    private ObservableSuperClass o;

    public ConcreteShapeObserver(ObservableSuperClass o) {
        this.o = o;
    }

    @Override
    public void update() {
        // refresh la view les boutons et jspquoi (genre shape.getImplementor.draw et .remove tmtc bonne chance)
        return;
    }
}
