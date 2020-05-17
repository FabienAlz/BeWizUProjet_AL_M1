package model;

import javafx.scene.Node;
import view.Mediator;

import java.util.ArrayList;
import java.util.List;

public class FXMenu extends javafx.scene.control.ToolBar implements Component {
    private Mediator mediator;
    private List<Node> subComponents;
    public FXMenu(List<Node> subComponents) {
        this.subComponents = new ArrayList<>();
        this.subComponents.addAll(subComponents);
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "FXMenu";
    }
}
