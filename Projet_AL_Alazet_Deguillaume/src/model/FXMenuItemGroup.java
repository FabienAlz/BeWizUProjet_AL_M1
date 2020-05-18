package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.View;

public class FXMenuItemGroup extends FXMenuItem {

    public FXMenuItemGroup(String text) {
        super(text);
        groupItemHandler();
    }

    private void groupItemHandler() {
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CompoundShape compoundShape = new CompoundShape(FXImplementor.getInstance(), new CanvasPosition(0, 0));
                for (Shape s : Canvas.getInstance().createCompound()) {
                    compoundShape.add(s);
                }
                for (Shape s : compoundShape.getShapes()) {
                    View.getInstance().canvas.getChildren().remove(FXImplementor.getInstance().getSHAPES().get(s.getId()));
                }
                ShapeObserver obs = new ConcreteShapeObserver();
                compoundShape.addObserver(obs);
                Canvas.getInstance().addAndNotify(compoundShape);
                Caretaker.getInstance().saveState();
            }
        });
    }
}
