package model.mediatorFX;

import model.Component;
import view.Mediator;

public abstract class MenuItem extends javafx.scene.control.MenuItem implements Component {
    private Mediator mediator;

    public MenuItem(String text) {
        super(text);
        setId(text);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "FXMenuItem";
    }
}
