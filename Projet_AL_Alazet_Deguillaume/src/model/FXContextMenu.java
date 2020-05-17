package model;

import javafx.scene.control.MenuItem;
import view.Mediator;

import java.util.ArrayList;
import java.util.List;

public class FXContextMenu extends javafx.scene.control.ContextMenu implements Component {
    private Mediator mediator;

    public FXContextMenu(String id) {
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
