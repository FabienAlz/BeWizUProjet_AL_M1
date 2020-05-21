package model.mediatorFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import model.*;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class MenuItemUngroup extends MenuItem {

    public MenuItemUngroup(String text) {
        super(text);
        UngroupItemHandler();
    }

    /**
     * On click, if the selected Shapes are grouped, ungroups them
     */
    private void UngroupItemHandler() {
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Shape> shapesToRemove = new ArrayList<>();
                List<Shape> shapesToAdd = new ArrayList<>();

                for (Shape s : Canvas.getInstance().getShapes()) {
                    if (s.isSelected() && s instanceof CompoundShape) {
                        for (Shape shape : ((CompoundShape) s).getShapes()) {
                            Shape copy = shape.clone();
                            copy.setSelected(false);
                            copy.setId();
                            shapesToAdd.add(copy);
                            shapesToRemove.add(shape);
                        }
                        shapesToRemove.add(s);
                    } else s.setSelected(false);
                }
                for (Shape s : shapesToRemove) {
                    FXImplementor.getInstance().getSHAPES().remove(s.getId());
                    Canvas.getInstance().remove(s);
                }
                for (Shape s : shapesToAdd) {
                    Canvas.getInstance().add(s);
                }
                View.getInstance().getCanvas().getChildren().clear();
                Canvas.getInstance().notifyAllShapes();
                View.getInstance().saveState();

            }
        });
    }
}
