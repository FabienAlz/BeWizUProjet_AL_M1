package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import view.EditorView;
import view.View;

public class FXMenuItemEdit extends FXMenuItem {
            EditorView mediator;
    public FXMenuItemEdit(String text) {
        super(text);
        EditItemHandler();
    }

    private void EditItemHandler() {
    setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mediator = EditorView.getInstance();
                EditorView.getInstance().shapeSaves.clear();

                for (Shape shape : Canvas.getInstance().getShapes()) {
                    if (!(shape instanceof CompoundShape) && shape.isSelected()) {
                        Canvas.getInstance().resetSelection();
                        FXImplementor.getInstance().getLastSelected().setSelected(true);
                        View.getInstance().getCanvas().getChildren().clear();
                        View.getInstance().getCanvas().getChildren().add(FXImplementor.getInstance().getLastFXSelected());
                        Canvas.getInstance().notifyAllShapes();
                    }
                }

                Shape s = null;
                for (Shape shape : Canvas.getInstance().getShapes()) {
                    if (shape.isSelected()) s = shape;
                }
                if (s.isSelected()) {
                    EditorView.getInstance().shapeSaves.put(s.getId(), s.clone());
                    if (s instanceof Rectangle) {
                        createRectangleEditor((Rectangle)s);

                    } else if (s instanceof Polygon) {
                        createPolygonEditor((Polygon)s);
                    } else if (s instanceof CompoundShape) {
                        boolean sameClass = true;
                        Shape firstShape = ((CompoundShape) s).getShapes().get(0);
                        for (Shape subShape : ((CompoundShape) s).getShapes()) {
                            if (!firstShape.getClass().equals(subShape.getClass())) {
                                sameClass = false;
                            }
                        }
                        if (sameClass) {
                            if (firstShape instanceof Rectangle) {
                                createRectangleEditor(s);
                            } else if (firstShape instanceof Polygon) {
                                createPolygonEditor(s);
                            }
                        } else {
                            createMixedEditor(firstShape);
                        }
                    }
                }
            }


    });
    }

    private void createRectangleEditor(Shape s) {
        if (s instanceof CompoundShape) s = ((CompoundShape) s).getShapes().get(0);
        createSharedComponents(s);
        mediator.registerComponent(new Label("Width"));
        mediator.registerComponent(new TextField("Width", String.valueOf(s.getWidth())));
        mediator.registerComponent(new Label("Height"));
        mediator.registerComponent(new TextField("Height", String.valueOf(s.getHeight())));
        mediator.registerComponent(new Label("Border radius"));
        mediator.registerComponent(new TextField("Border radius", String.valueOf(((Rectangle)s).getBorderRadius())));
        mediator.createRectangleEditor();
    }

    private void createPolygonEditor(Shape s) {
        if (s instanceof CompoundShape) s = ((CompoundShape) s).getShapes().get(0);
        createSharedComponents(s);
        mediator.registerComponent(new Label("Edges"));
        mediator.registerComponent(new TextField("Edges",String.valueOf(((Polygon)s).getEdges())));
        mediator.registerComponent(new Label("Length"));
        mediator.registerComponent(new TextField("Length",String.valueOf(((Polygon)s).getLength())));
        mediator.createPolygonEditor();
    }

    private void createMixedEditor(Shape s) {
        createSharedComponents(s);
        mediator.createMixedEditor();
    }

    private void createSharedComponents(Shape s) {
        if (s instanceof CompoundShape) s = ((CompoundShape) s).getShapes().get(0);

        mediator.registerComponent(new GridPane());
        mediator.registerComponent(new ColorPicker(Color.valueOf(s.getColor())));
        mediator.registerComponent(new Label("Rotation"));
        mediator.registerComponent(new TextField("Rotation", String.valueOf(s.getRotation())));
        mediator.registerComponent(new OkButton());
        mediator.registerComponent(new ApplyButton());
        mediator.registerComponent(new CancelButton());
    }
}
