package model.mediatorFX;

import javafx.scene.control.MenuItem;
import model.Component;
import view.Mediator;

public class ContextMenu extends javafx.scene.control.ContextMenu implements Component {
    private Mediator mediator;

    public ContextMenu(String id) {
        super();
        setId(id);
    }

    public void addItem(MenuItem item) {
        getItems().add(item);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "FXContextMenu";
    }
}
