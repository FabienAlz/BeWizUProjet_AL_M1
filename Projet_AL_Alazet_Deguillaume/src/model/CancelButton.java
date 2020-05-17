package model;

import view.EditorView;
import view.Mediator;
import view.View;

import java.util.Map;

public class CancelButton extends javafx.scene.control.Button implements Component {
    private Mediator mediator;


    public CancelButton(){
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

    private void cancelButtonHandler() {
        setOnAction(e -> {
            if (EditorView.getInstance().shapeSaves.size() != 0) {
                Map.Entry<Long, Shape> entry = EditorView.getInstance().shapeSaves.entrySet().iterator().next();
                Long key = entry.getKey();
                Shape shapeToRevert = EditorView.getInstance().shapeSaves.get(key);
                Canvas.getInstance().getShapes().remove(Canvas.getInstance().getShape(key));
                Canvas.getInstance().getShapes().add(shapeToRevert);
            }
            View.getInstance().canvas.getChildren().clear();
            Canvas.getInstance().notifyAllShapes();
        });
    }
}
