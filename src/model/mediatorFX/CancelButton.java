package model.mediatorFX;

import model.Canvas;
import model.Component;
import model.Shape;
import view.EditorViewFX;
import view.Mediator;
import view.ViewFX;
import java.util.Map;

public class CancelButton extends javafx.scene.control.Button implements Component {
    private Mediator mediator;

    public CancelButton() {
        super("Cancel");
        cancelButtonHandler();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "CancelButton";
    }

    /**
     * On click, revert all Shapes to their state at the opening of the editor, closes the editor and unselects all the selected Shapes
     */
    private void cancelButtonHandler() {
        setOnAction(e -> {
            if (EditorViewFX.getInstance().getShapeSaves().size() != 0) {
                Map.Entry<Long, Shape> entry = EditorViewFX.getInstance().getShapeSaves().entrySet().iterator().next();
                Long key = entry.getKey();
                Shape shapeToRevert = EditorViewFX.getInstance().getShapeSaves().get(key);
                Canvas.getInstance().getShapes().remove(Canvas.getInstance().getShape(key));
                Canvas.getInstance().getShapes().add(shapeToRevert);
            }
            Canvas.getInstance().resetSelection();
            ViewFX.getInstance().getCanvas().getChildren().clear();
            Canvas.getInstance().notifyAllShapes();
        });
    }
}
