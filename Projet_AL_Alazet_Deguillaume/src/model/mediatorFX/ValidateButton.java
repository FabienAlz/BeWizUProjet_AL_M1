package model.mediatorFX;

import model.*;
import view.EditorView;

public abstract class ValidateButton extends javafx.scene.control.Button implements Component {

    public ValidateButton(String text) {
        super(text);
    }

    /**
     * Edits s according to the values extracted from the editor text fields and color picker
     *
     * @param s the Shape to edit
     */
    protected void editShape(Shape s) {

        s.setColor(String.valueOf(EditorView.getInstance().getColorPicker().getValue()));
        s.setRotation(Float.parseFloat(EditorView.getInstance().getTextFields().get("Rotation").getText()));
        if (s instanceof Rectangle) {
            ((Rectangle) s).setWidth(Float.parseFloat(EditorView.getInstance().getTextFields().get("Width").getText()));
            ((Rectangle) s).setHeight(Float.parseFloat(EditorView.getInstance().getTextFields().get("Height").getText()));
            ((Rectangle) s).setBorderRadius(Float.parseFloat(EditorView.getInstance().getTextFields().get("Border radius").getText()));
        } else if (s instanceof Polygon) {
            ((Polygon) s).setEdges(Integer.parseInt(EditorView.getInstance().getTextFields().get("Edges").getText()));
            ((Polygon) s).setLength(Float.parseFloat(EditorView.getInstance().getTextFields().get("Length").getText()));
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
                        ((Rectangle) subShape).setWidth(Float.parseFloat(EditorView.getInstance().getTextFields().get("Width").getText()));
                        ((Rectangle) subShape).setHeight(Float.parseFloat(EditorView.getInstance().getTextFields().get("Height").getText()));
                        ((Rectangle) subShape).setBorderRadius(Float.parseFloat(EditorView.getInstance().getTextFields().get("Border radius").getText()));
                    }

                } else if (firstShape instanceof Polygon) {
                    for (Shape subShape : ((CompoundShape) s).getShapes()) {
                        ((Polygon) subShape).setEdges(Integer.parseInt(EditorView.getInstance().getTextFields().get("Edges").getText()));
                        ((Polygon) subShape).setLength(Float.parseFloat(EditorView.getInstance().getTextFields().get("Length").getText()));
                    }
                }
            }
        }
    }
}
