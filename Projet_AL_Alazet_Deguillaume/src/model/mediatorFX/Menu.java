package model.mediatorFX;

import javafx.scene.Node;
import model.Component;
import view.Mediator;
import java.util.ArrayList;
import java.util.List;

public class Menu extends javafx.scene.control.ToolBar implements Component {
    private Mediator mediator;
    private List<Node> subComponents;

    public Menu(List<Node> subComponents) {
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
