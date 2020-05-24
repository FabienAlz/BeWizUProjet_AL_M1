package model.mediatorFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import view.Mediator;
import view.Originator;
import view.ViewFX;

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

    /**
     * Redo the last undone action
     */
    private void setRedoButtonHandler() {
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Originator.getInstance().redoState();
            }
        });
    }
}
