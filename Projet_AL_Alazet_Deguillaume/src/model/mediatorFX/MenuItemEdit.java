package model.mediatorFX;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import model.*;
import view.EditorView;
import view.View;

public class MenuItemEdit extends MenuItem {
    EditorView mediator;

    public MenuItemEdit(String text) {
        super(text);
        EditItemHandler();
    }

    /**
     * On click, opens the right editor depending of the selected Shape(s)
     */
    private void EditItemHandler() {
        setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mediator = EditorView.getInstance();
                EditorView.getInstance().getShapeSaves().clear();

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
                    EditorView.getInstance().getShapeSaves().put(s.getId(), s.clone());
                    if (s instanceof Rectangle) {
                        createRectangleEditor(s);

                    } else if (s instanceof Polygon) {
                        createPolygonEditor(s);
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
                            createMixedEditor(s);
                        }
                    }
                }
            }


        });
    }

    /**
     * Creates all the FX components needed for the rectangle (or compound shape of rectangle) editor window
     */
    private void createRectangleEditor(Shape s) {
        mediator.registerComponent(new Label("Width"));
        mediator.registerComponent(new Label("Height"));
        mediator.registerComponent(new Label("Border radius"));
        createSharedComponents(s);


//        mediator.registerComponent(new TextField("Width"));
//        mediator.registerComponent(new TextField("Height"));
//        mediator.registerComponent(new TextField("Border radius"));
        if (s instanceof CompoundShape) {
            if (((CompoundShape) s).sameWidth())
                mediator.registerComponent(new TextField("Width", String.valueOf(((CompoundShape) s).getShapes().get(0).getWidth())));
            else
                mediator.registerComponent(new TextField("Width"));
            if (((CompoundShape) s).sameHeight())
                mediator.registerComponent(new TextField("Height", String.valueOf(((CompoundShape) s).getShapes().get(0).getHeight())));
            else
                mediator.registerComponent(new TextField("Height"));
            if (((CompoundShape) s).sameBorderRadius()) {
                Rectangle rect = (Rectangle) ((CompoundShape) s).getShapes().get(0);
                mediator.registerComponent(new TextField("Border radius", String.valueOf(rect.getBorderRadius())));
            } else
                mediator.registerComponent(new TextField("Border radius"));

        } else {
            mediator.registerComponent(new TextField("Width", String.valueOf(s.getWidth())));
            mediator.registerComponent(new TextField("Height", String.valueOf(s.getHeight())));
            mediator.registerComponent(new TextField("Border radius", String.valueOf(((Rectangle) s).getBorderRadius())));
        }

        mediator.createRectangleEditor();
    }


    /**
     * Creates all the FX components needed for the rectangle (or compound shape of rectangle) editor window
     */
    private void createPolygonEditor(Shape s) {
        mediator.registerComponent(new Label("Edges"));
        mediator.registerComponent(new Label("Length"));

        if (s instanceof CompoundShape) {
            if (((CompoundShape) s).sameEdges())
                mediator.registerComponent(new TextField("Edges", String.valueOf((((Polygon) ((CompoundShape) s).getShapes().get(0)).getEdges()))));
            else
                mediator.registerComponent(new TextField("Edges"));
            if (((CompoundShape) s).sameLength())
                mediator.registerComponent(new TextField("Length", String.valueOf((((Polygon) ((CompoundShape) s).getShapes().get(0)).getLength()))));
            else
                mediator.registerComponent(new TextField("Length"));
        } else {
            mediator.registerComponent(new TextField("Edges", String.valueOf(((Polygon) s).getEdges())));
            mediator.registerComponent(new TextField("Length", String.valueOf(((Polygon) s).getLength())));
        }
        createSharedComponents(s);
        mediator.createPolygonEditor();
    }

    /**
     * Creates all the FX components needed for the compound shape of both polygons and rectangles editor window
     */
    private void createMixedEditor(Shape s) {
        createSharedComponents(s);
        mediator.createMixedEditor();
    }

    /**
     * Creates all the FX components that are shared between all editors
     */
    private void createSharedComponents(Shape s) {
        mediator.registerComponent(new GridPane());
        mediator.registerComponent(new Label("Rotation"));
        mediator.registerComponent(new GridPane());
        mediator.registerComponent(new Label("Rotation"));
        mediator.registerComponent(new OkButton());
        mediator.registerComponent(new ApplyButton());
        mediator.registerComponent(new CancelButton());

        if (s instanceof CompoundShape && ((CompoundShape) s).getShapes().size() == 1) {
            mediator.registerComponent(new ColorPicker(Color.valueOf(((CompoundShape) s).getShapes().get(0).getColor())));
            mediator.registerComponent(new TextField("Rotation", String.valueOf(((CompoundShape) s).getShapes().get(0).getRotation())));
        } else if (s instanceof CompoundShape) {
            if (((CompoundShape) s).sameColor())
                mediator.registerComponent(new ColorPicker((Color.valueOf(((CompoundShape) s).getShapes().get(0).getColor()))));
            else mediator.registerComponent(new ColorPicker());
            if (((CompoundShape) s).sameRotation())
                mediator.registerComponent(new TextField("Rotation", String.valueOf(((CompoundShape) s).getShapes().get(0).getRotation())));
            else
                mediator.registerComponent(new TextField("Rotation"));
        } else {
            mediator.registerComponent(new ColorPicker(Color.valueOf(s.getColor())));
            mediator.registerComponent(new TextField("Rotation", String.valueOf(s.getRotation())));
        }
    }
}
