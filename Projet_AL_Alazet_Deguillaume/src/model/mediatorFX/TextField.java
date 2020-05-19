package model.mediatorFX;

import model.Component;
import view.Mediator;

public class TextField extends javafx.scene.control.TextField implements Component {
    private Mediator mediator;
    private String id;

    public TextField(String id) {
        super();
        this.id = id;
    }

    public TextField(String id, String text) {
        super(text);
        this.id = id;
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "TextField";
    }

    public String getID() {
        return id;
    }

}
