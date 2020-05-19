package model.mediatorFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import view.Mediator;
import view.View;

import java.util.List;

public class UndoButton extends Button {
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

                            float ratio = (float) (s.getWidth() / (View.getInstance().getToolbar().getPrefWidth() - 24));
                            if (s.getPositionI().getY() + s.getHeight() / ratio < View.getInstance().TOOLBAR_HEIGHT)
                                View.getInstance().getToolbar().setPrefHeight(View.getInstance().TOOLBAR_HEIGHT);
                            else {
                                View.getInstance().getToolbar().setPrefHeight(s.getPositionI().getY() + (s.getHeight() / ratio) + 10);
                            }
                        }
                    }
                    Canvas.getInstance().notifyAllShapes();
                    Toolbar.getInstance().notifyAllShapes();
                }
            }
        });

    }
}
