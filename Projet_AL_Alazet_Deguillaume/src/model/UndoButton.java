package model;

import view.Mediator;

public class UndoButton extends FXButton {
    private Mediator mediator;

    public UndoButton(String s, String imageSrc) {
        super(s, imageSrc);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "UndoButton";
    }
}
