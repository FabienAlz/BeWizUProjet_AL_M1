package model.mediatorFX;

import javafx.scene.paint.Color;
import model.*;
import view.EditorViewFX;

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
        if (EditorViewFX.getInstance().getColorPicker().getValue() != Color.WHITE)
            s.setColor(String.valueOf(EditorViewFX.getInstance().getColorPicker().getValue()));
        if (!EditorViewFX.getInstance().getTextFields().get("Rotation").getText().trim().isEmpty())
            s.setRotation(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Rotation").getText()));
        if (s instanceof Rectangle) {
            if (!EditorViewFX.getInstance().getTextFields().get("Width").getText().trim().isEmpty())
                if (Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Width").getText()) >= 2)
                    ((Rectangle) s).setWidth(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Width").getText()));
            if (!EditorViewFX.getInstance().getTextFields().get("Height").getText().trim().isEmpty())
                if (Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Height").getText()) >= 2)
                    ((Rectangle) s).setHeight(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Height").getText()));
            if (!EditorViewFX.getInstance().getTextFields().get("Border radius").getText().trim().isEmpty())
                ((Rectangle) s).setBorderRadius(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Border radius").getText()));
        } else if (s instanceof Polygon) {
            if (!EditorViewFX.getInstance().getTextFields().get("Edges").getText().trim().isEmpty())
                if (Integer.parseInt(EditorViewFX.getInstance().getTextFields().get("Edges").getText()) > 2)
                    ((Polygon) s).setEdges(Integer.parseInt(EditorViewFX.getInstance().getTextFields().get("Edges").getText()));
            if (!EditorViewFX.getInstance().getTextFields().get("Length").getText().trim().isEmpty())
                if (Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Length").getText()) > 0)
                    ((Polygon) s).setLength(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Length").getText()));
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
                        if (!EditorViewFX.getInstance().getTextFields().get("Width").getText().trim().isEmpty())
                            if (Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Width").getText()) >= 2)
                                ((Rectangle) subShape).setWidth(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Width").getText()));
                        if (!EditorViewFX.getInstance().getTextFields().get("Height").getText().trim().isEmpty())
                            if (Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Height").getText()) >= 2)
                                ((Rectangle) subShape).setHeight(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Height").getText()));
                        if (!EditorViewFX.getInstance().getTextFields().get("Border radius").getText().trim().isEmpty())
                            ((Rectangle) subShape).setBorderRadius(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Border radius").getText()));
                    }

                } else if (firstShape instanceof Polygon) {
                    for (Shape subShape : ((CompoundShape) s).getShapes()) {
                        if (!EditorViewFX.getInstance().getTextFields().get("Edges").getText().trim().isEmpty())
                            if (Integer.parseInt(EditorViewFX.getInstance().getTextFields().get("Edges").getText()) > 2)
                                ((Polygon) subShape).setEdges(Integer.parseInt(EditorViewFX.getInstance().getTextFields().get("Edges").getText()));
                        if (!EditorViewFX.getInstance().getTextFields().get("Length").getText().trim().isEmpty())
                            if (Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Length").getText()) > 0)
                                ((Polygon) subShape).setLength(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Length").getText()));
                    }
                }
            }
        }
    }
}
