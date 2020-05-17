package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.Mediator;
import java.util.List;

public class UndoButton extends FXButton {
    private Mediator mediator;

    public UndoButton(String s, String imageSrc) {
        super(s, imageSrc);
        setUndoButtonHandlers();
    }

    @Override
    public String getName() {
        return "UndoButton";
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    private void setUndoButtonHandlers() {
        /**
         * Undo the last performed action
         */
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent mouseEvent) {
                FXImplementor implementor = FXImplementor.getInstance();
                Caretaker.getInstance().decrement();
                Memento memento = Caretaker.getInstance().get(Caretaker.getInstance().getCurrent());
                List<Shape> state = memento.getState();
                // Deletes all the Shapes
                Canvas.getInstance().clear();
                Toolbar.getInstance().clear();
                implementor.getCanvas().getChildren().clear();
                implementor.getLeftBar().getChildren().clear();
                // Recreates the saved state
                for(Shape s : state) {
                    if (s.getPositionI() instanceof CanvasPosition) {
                        Canvas.getInstance().add(s);

                    } else if (s.getPositionI() instanceof ToolbarPosition) {
                        Toolbar.getInstance().add(s);
                    }
                }
                Canvas.getInstance().notifyAllShapes();
                Toolbar.getInstance().notifyAllShapes();
            }
        });

    }
}