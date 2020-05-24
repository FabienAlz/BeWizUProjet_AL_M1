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
        editSharedShape(s);
        if (s instanceof Rectangle) {
            editRectangle((Rectangle) s);
        } else if (s instanceof Polygon) {
            editPolygon((Polygon) s);
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
                        editRectangle((Rectangle) subShape);
                    }
                } else if (firstShape instanceof Polygon) {
                    for (Shape subShape : ((CompoundShape) s).getShapes()) {
                        editPolygon((Polygon) subShape);
                    }
                }
            }
        }
    }

    /**
     * Edits the fields of the Rectangle r according to the values extracted from the editor text fields
     * Does not edit the fields if the values are illogical or unset
     *
     * @param r
     */
    private void editRectangle(Rectangle r) {
        if (!EditorViewFX.getInstance().getTextFields().get("Width").getText().trim().isEmpty())
            if (Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Width").getText()) >= 2)
                r.setWidth(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Width").getText()));
        if (!EditorViewFX.getInstance().getTextFields().get("Height").getText().trim().isEmpty())
            if (Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Height").getText()) >= 2)
                r.setHeight(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Height").getText()));
        if (!EditorViewFX.getInstance().getTextFields().get("Border radius").getText().trim().isEmpty())
            r.setBorderRadius(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Border radius").getText()));
    }

    /**
     * Edits the fields of the Polygon p according to the values extracted from the editor text fields
     * Does not edit the fields if the values are illogical or unset
     *
     * @param p
     */
    private void editPolygon(Polygon p) {
        if (!EditorViewFX.getInstance().getTextFields().get("Edges").getText().trim().isEmpty())
            if (Integer.parseInt(EditorViewFX.getInstance().getTextFields().get("Edges").getText()) > 2)
                p.setEdges(Integer.parseInt(EditorViewFX.getInstance().getTextFields().get("Edges").getText()));
        if (!EditorViewFX.getInstance().getTextFields().get("Length").getText().trim().isEmpty())
            if (Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Length").getText()) > 0)
                p.setLength(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Length").getText()));
    }

    /**
     * Edits the shared fields of the Shape s according to the values extracted from the editor text fields "Rotation" and color picker
     * Does not edit the fields if the values are illogical or unset (white is the unset color).
     *
     * @param s
     */
    private void editSharedShape(Shape s) {
        if (EditorViewFX.getInstance().getColorPicker().getValue() != Color.WHITE)
            s.setColor(String.valueOf(EditorViewFX.getInstance().getColorPicker().getValue()));
        if (!EditorViewFX.getInstance().getTextFields().get("Rotation").getText().trim().isEmpty())
            s.setRotation(Float.parseFloat(EditorViewFX.getInstance().getTextFields().get("Rotation").getText()));
    }
}
