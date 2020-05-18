package model;

import view.EditorView;
import view.Mediator;
import view.View;

public class ApplyButton extends javafx.scene.control.Button implements Component {
    private Mediator mediator;

    public ApplyButton(){
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

    private void applyButtonHandler() {
        setOnAction(e -> {
            EditorView.getInstance().shapeSaves.clear();
            for (Shape s : Canvas.getInstance().getShapes()) {
                if (s.isSelected()) {
                    EditorView.getInstance().shapeSaves.put(s.getId(), s.clone());
                    s.setColor(String.valueOf(EditorView.getInstance().colorPicker.getValue()));
                    s.setRotation(Float.parseFloat(EditorView.getInstance().textFields.get("Rotation").getText()));
                    if (s instanceof Rectangle) {
                        ((Rectangle) s).setWidth(Float.parseFloat(EditorView.getInstance().textFields.get("Width").getText()));
                        ((Rectangle) s).setHeight(Float.parseFloat(EditorView.getInstance().textFields.get("Height").getText()));
                        ((Rectangle) s).setBorderRadius(Float.parseFloat(EditorView.getInstance().textFields.get("Border radius").getText()));
                    } else if (s instanceof Polygon) {
                        ((Polygon) s).setEdges(Integer.parseInt(EditorView.getInstance().textFields.get("Edges").getText()));
                        ((Polygon) s).setLength(Float.parseFloat(EditorView.getInstance().textFields.get("Length").getText()));
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
                                for (Shape subShape : ((CompoundShape) s).getShapes()) {
                                    ((Rectangle) subShape).setWidth(Float.parseFloat(EditorView.getInstance().textFields.get("Width").getText()));
                                    ((Rectangle) subShape).setHeight(Float.parseFloat(EditorView.getInstance().textFields.get("Height").getText()));
                                    ((Rectangle) subShape).setBorderRadius(Float.parseFloat(EditorView.getInstance().textFields.get("Border radius").getText()));
                                }

                            } else if (firstShape instanceof Polygon) {
                                for (Shape subShape : ((CompoundShape) s).getShapes()) {
                                    ((Polygon) subShape).setEdges(Integer.parseInt(EditorView.getInstance().textFields.get("Edges").getText()));
                                    ((Polygon) subShape).setLength(Float.parseFloat(EditorView.getInstance().textFields.get("Length").getText()));
                                }
                            }
                        }
                    }
                    View.getInstance().canvas.getChildren().clear();
                    Canvas.getInstance().notifyAllShapes();
                    View.getInstance().canvas.getChildren().add(EditorView.getInstance().gridPane);
                }
            }
        });
    }
}
