package model;

import view.Mediator;

public abstract class FXMenuItem extends javafx.scene.control.MenuItem implements Component {
    private Mediator mediator;

    public FXMenuItem(String text) {
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
