package model.mediatorFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import view.Mediator;
import view.View;

import java.util.List;

public class RedoButtonWithImage extends ButtonWithImage {
    private Mediator mediator;

    public RedoButtonWithImage(String s, String imageSrc) {
        super(s, imageSrc);
        setRedoButtonHandler();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "RedoButton";
    }

    private void setRedoButtonHandler() {
        /**
         * Redo the last undone action
         */
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
                    View.getInstance().getToolbar().updateDisplay();

                }
            }
        });
    }
}
