package model.mediatorFX;

import model.*;
import view.EditorViewFX;
import view.Mediator;
import view.ViewFX;

public class ApplyButton extends ValidateButton implements Component {
    private Mediator mediator;

    public ApplyButton() {
        super("Apply");
        applyButtonHandler();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "ApplyButton";
    }

    /**
     * On click, updates the selected Shape(s) according to the values extracted from the editor text fields and color picker
     */
    private void applyButtonHandler() {
        setOnAction(e -> {
            for (Shape s : Canvas.getInstance().getShapes()) {
                if (s.isSelected()) {
                    editShape(s);
                    ViewFX.getInstance().getCanvas().getChildren().clear();
                    Canvas.getInstance().notifyAllShapes();
                    ViewFX.getInstance().getCanvas().getChildren().add(EditorViewFX.getInstance().getGridPane());
                }
            }
        });
    }
}
