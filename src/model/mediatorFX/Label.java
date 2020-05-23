package model.mediatorFX;

import model.Component;
import view.Mediator;

public class Label extends javafx.scene.control.Label implements Component {
    private Mediator mediator;
    private String id;


    public Label(String text) {
        super(text);
        id = text;
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Label";
    }

    public String getID() {
        return id;
    }

}
