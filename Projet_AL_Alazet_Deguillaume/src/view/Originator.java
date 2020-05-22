package view;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class Originator {
    private static List<Shape> state;
    private static Originator instance;

    private Originator() {
    }

    public static Originator getInstance() {
        if (instance == null) {
            instance = new Originator();
        }
        state = new ArrayList<>();
        state.addAll(Canvas.getInstance().getShapes());
        state.addAll(Toolbar.getInstance().getShapes());
        return instance;
    }

    /**
     * Saves the state of the application
     */
    public void saveState() {
        Caretaker.getInstance().remove();
        Memento m = new Memento(state);
        Caretaker.getInstance().add(m);
    }

    public void restoreState() {
        FXImplementor implementor = FXImplementor.getInstance();
        if (Caretaker.getInstance().getCurrent() != 0) {
            Caretaker.getInstance().decrement();

            Memento memento = Caretaker.getInstance().get(Caretaker.getInstance().getCurrent());
            List<Shape> state = memento.getState();
            // Deletes all the Shapes
            Canvas.getInstance().clear();
            Toolbar.getInstance().clear();
            implementor.getCanvas().getChildren().clear();
            implementor.getToolbar().getChildren().clear();
            // Recreates the saved state
            for (Shape s : state) {
                if (s.getPositionI() instanceof CanvasPosition) {
                    Canvas.getInstance().add(s);
                } else if (s.getPositionI() instanceof ToolbarPosition) {
                    Toolbar.getInstance().add(s);
                }
            }
            Canvas.getInstance().notifyAllShapes();
            Toolbar.getInstance().notifyAllShapes();
            ViewFX.getInstance().getToolbar().updateDisplay();
        }
    }

    public void redoState() {
        FXImplementor implementor = FXImplementor.getInstance();
        int oldState = Caretaker.getInstance().getCurrent();
        Caretaker.getInstance().increment();
        if (oldState != Caretaker.getInstance().getCurrent()) {
            Memento memento = Caretaker.getInstance().get(Caretaker.getInstance().getCurrent());
            List<Shape> state = memento.getState();
            // Deletes all the Shapes
            Canvas.getInstance().clear();
            Toolbar.getInstance().clear();
            implementor.getCanvas().getChildren().clear();
            implementor.getToolbar().getChildren().clear();
            // Recreates the saved state
            for (Shape s : state) {
                if (s.getPositionI() instanceof CanvasPosition) {
                    Canvas.getInstance().add(s);
                } else if (s.getPositionI() instanceof ToolbarPosition) {
                    Toolbar.getInstance().add(s);
                }
            }
            Canvas.getInstance().notifyAllShapes();
            Toolbar.getInstance().notifyAllShapes();
            ViewFX.getInstance().getToolbar().updateDisplay();

        }

    }
}
