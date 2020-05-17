package model;

import view.Mediator;

public class ApplyButton extends javafx.scene.control.Button implements Component {
    private Mediator mediator;


    public ApplyButton(){
        super("Apply");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "ApplyButton";
    }
}
