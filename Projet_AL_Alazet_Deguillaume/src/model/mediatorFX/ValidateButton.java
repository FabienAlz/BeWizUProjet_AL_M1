package model.mediatorFX;

import javafx.scene.paint.Color;
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
        if (EditorView.getInstance().getColorPicker().getValue() != Color.WHITE)
            s.setColor(String.valueOf(EditorView.getInstance().getColorPicker().getValue()));
        if (!EditorView.getInstance().getTextFields().get("Rotation").getText().trim().isEmpty())
            s.setRotation(Float.parseFloat(EditorView.getInstance().getTextFields().get("Rotation").getText()));
        if (s instanceof Rectangle) {
            if (!EditorView.getInstance().getTextFields().get("Width").getText().trim().isEmpty())
                if (Float.parseFloat(EditorView.getInstance().getTextFields().get("Width").getText()) >= 2)
                ((Rectangle) s).setWidth(Float.parseFloat(EditorView.getInstance().getTextFields().get("Width").getText()));
            if (!EditorView.getInstance().getTextFields().get("Height").getText().trim().isEmpty())
                if (Float.parseFloat(EditorView.getInstance().getTextFields().get("Height").getText()) >= 2)
                ((Rectangle) s).setHeight(Float.parseFloat(EditorView.getInstance().getTextFields().get("Height").getText()));
            if (!EditorView.getInstance().getTextFields().get("Border radius").getText().trim().isEmpty())
                ((Rectangle) s).setBorderRadius(Float.parseFloat(EditorView.getInstance().getTextFields().get("Border radius").getText()));
        } else if (s instanceof Polygon) {
            if (!EditorView.getInstance().getTextFields().get("Edges").getText().trim().isEmpty())
                ((Polygon) s).setEdges(Integer.parseInt(EditorView.getInstance().getTextFields().get("Edges").getText()));
            if (!EditorView.getInstance().getTextFields().get("Length").getText().trim().isEmpty())
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
                        if (!EditorView.getInstance().getTextFields().get("Width").getText().trim().isEmpty())
                            ((Rectangle) subShape).setWidth(Float.parseFloat(EditorView.getInstance().getTextFields().get("Width").getText()));
                        if (!EditorView.getInstance().getTextFields().get("Height").getText().trim().isEmpty())
                            ((Rectangle) subShape).setHeight(Float.parseFloat(EditorView.getInstance().getTextFields().get("Height").getText()));
                        if (!EditorView.getInstance().getTextFields().get("Border radius").getText().trim().isEmpty())
                            ((Rectangle) subShape).setBorderRadius(Float.parseFloat(EditorView.getInstance().getTextFields().get("Border radius").getText()));
                    }

                } else if (firstShape instanceof Polygon) {
                    for (Shape subShape : ((CompoundShape) s).getShapes()) {
                        if (!EditorView.getInstance().getTextFields().get("Edges").getText().trim().isEmpty())
                            ((Polygon) subShape).setEdges(Integer.parseInt(EditorView.getInstance().getTextFields().get("Edges").getText()));
                        if (!EditorView.getInstance().getTextFields().get("Length").getText().trim().isEmpty())
                            ((Polygon) subShape).setLength(Float.parseFloat(EditorView.getInstance().getTextFields().get("Length").getText()));
                    }
                }
            }
        }
    }
}
