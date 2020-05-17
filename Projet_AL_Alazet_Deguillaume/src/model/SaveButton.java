package model;

import view.Mediator;

public class SaveButton extends FXButton {
    private Mediator mediator;

    public SaveButton(String s, String imageSrc) {
        super(s, imageSrc);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "SaveButton";
    }
}
