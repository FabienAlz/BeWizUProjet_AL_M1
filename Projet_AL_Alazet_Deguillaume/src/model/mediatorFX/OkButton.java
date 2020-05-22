package model.mediatorFX;

import model.*;
import view.Mediator;
import view.Originator;
import view.ViewFX;

public class OkButton extends ValidateButton implements Component {
    private Mediator mediator;

    public OkButton() {
        super("Ok");
        okButtonHandler();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "OkButton";
    }

    /**
     * On click, updates the selected Shape(s) according to the values extracted from the editor text fields and color picker, closes the editor and unselected all the selected Shape(s)
     */
    private void okButtonHandler() {
        setOnAction(e -> {
            for (Shape s : Canvas.getInstance().getShapes()) {
                if (s.isSelected()) {
                    editShape(s);
                    Canvas.getInstance().resetSelection();
                    ViewFX.getInstance().getCanvas().getChildren().clear();
                    Canvas.getInstance().notifyAllShapes();
                }
            }
            Originator.getInstance().saveState();
        });
    }
}
