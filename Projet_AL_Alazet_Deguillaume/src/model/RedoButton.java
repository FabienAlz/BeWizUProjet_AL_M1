package model;

import view.Mediator;

public class RedoButton extends FXButton {
    private Mediator mediator;

    public RedoButton(String s, String imageSrc) {
        super(s, imageSrc);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "RedoButton";
    }
}
