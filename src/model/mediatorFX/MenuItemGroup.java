package model.mediatorFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import view.Originator;
import view.ViewFX;

public class MenuItemGroup extends MenuItem {

    public MenuItemGroup(String text) {
        super(text);
        groupItemHandler();
    }

    /**
     * On click, groups the selected Shape(s)
     */
    private void groupItemHandler() {
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CompoundShape compoundShape = new CompoundShape(FXImplementor.getInstance(), new CanvasPosition(0, 0));
                for (Shape s : Canvas.getInstance().createCompound()) {
                    compoundShape.add(s);
                }
                for (Shape s : compoundShape.getShapes()) {
                    ViewFX.getInstance().getCanvas().getChildren().remove(FXImplementor.getInstance().getSHAPES().get(s.getId()));
                }

                ShapeObserver obs = new ConcreteShapeObserver();
                compoundShape.addObserver(obs);
                Canvas.getInstance().addAndNotify(compoundShape);
                Originator.getInstance().saveState();
            }
        });
    }
}
