package model;

import view.Mediator;

public class Popup extends javafx.stage.Popup implements Component {
    private Mediator mediator;
    private String text;

    public Popup() {
        super();
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
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
