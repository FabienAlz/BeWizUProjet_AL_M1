package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class FXMenuItemDegroup extends FXMenuItem {

    public FXMenuItemDegroup(String text) {
        super(text);
        DegroupItemHandler();
    }

    private void DegroupItemHandler() {
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
                System.out.println(Canvas.getInstance().getShapes());
                for (Shape s : shapesToRemove) {
                    FXImplementor.getInstance().getSHAPES().remove(s.getId());
                    Canvas.getInstance().remove(s);
                }
                System.out.println(Canvas.getInstance().getShapes());
                for (Shape s : shapesToAdd) {
                    Canvas.getInstance().add(s);
                }
                View.getInstance().canvas.getChildren().clear();
                Canvas.getInstance().notifyAllShapes();
                System.out.println(Canvas.getInstance().getShapes());
                System.out.println(FXImplementor.getInstance().getSHAPES());
            }
        });
    }
}
