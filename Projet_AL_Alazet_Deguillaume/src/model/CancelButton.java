package model;

import view.Mediator;

public class CancelButton extends javafx.scene.control.Button implements Component {
    private Mediator mediator;


    public CancelButton(){
        super("Cancel");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "CancelButton";
    }
}
