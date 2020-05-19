package model.mediatorFX;

import model.Component;
import view.Mediator;

public class Popup extends javafx.stage.Popup implements Component {
    private Mediator mediator;

    public Popup() {
        super();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Popup";
    }
}
