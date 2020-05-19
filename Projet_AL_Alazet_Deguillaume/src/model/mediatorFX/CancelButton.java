package model.mediatorFX;

import model.Canvas;
import model.Component;
import model.Shape;
import view.EditorView;
import view.Mediator;
import view.View;
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
     * On click, revert all Shapes to their state at the opening of the editor, closes the editor and unselected all the selected Shapes
     */
    private void cancelButtonHandler() {
        setOnAction(e -> {
            if (EditorView.getInstance().getShapeSaves().size() != 0) {
                Map.Entry<Long, Shape> entry = EditorView.getInstance().getShapeSaves().entrySet().iterator().next();
                Long key = entry.getKey();
                Shape shapeToRevert = EditorView.getInstance().getShapeSaves().get(key);
                Canvas.getInstance().getShapes().remove(Canvas.getInstance().getShape(key));
                Canvas.getInstance().getShapes().add(shapeToRevert);
            }
            Canvas.getInstance().resetSelection();
            View.getInstance().getCanvas().getChildren().clear();
            Canvas.getInstance().notifyAllShapes();
        });
    }
}
