package model;

import view.Mediator;

public class OkButton extends javafx.scene.control.Button implements Component {
    private Mediator mediator;


    public OkButton(){
        super("Ok");
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "OkButton";
    }
}
