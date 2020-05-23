package model.mediatorFX;

import model.Component;
import view.Mediator;

public class GridPane extends javafx.scene.layout.GridPane implements Component {
    Mediator mediator;

    public GridPane() {
        super();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "GridPane";
    }
}
