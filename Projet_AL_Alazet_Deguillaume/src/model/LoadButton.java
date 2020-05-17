package model;

import view.Mediator;

public class LoadButton extends FXButton {
    private Mediator mediator;

    public LoadButton(String s, String imageSrc) {
        super(s, imageSrc);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "LoadButton";
    }
}
