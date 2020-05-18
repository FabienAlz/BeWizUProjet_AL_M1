package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.Mediator;

import java.sql.SQLOutput;
import java.util.List;

public class RedoButton extends FXButton {
    private Mediator mediator;

    public RedoButton(String s, String imageSrc) {
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
                }
            }
        });
    }
}
