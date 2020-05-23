package model.mediatorFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import view.Mediator;
import view.Originator;
import view.ViewFX;

import java.util.List;

public class UndoButtonWithImage extends ButtonWithImage {
    private Mediator mediator;

    public UndoButtonWithImage(String s, String imageSrc) {
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
                Originator.getInstance().restoreState();
            }
        });
    }
}
