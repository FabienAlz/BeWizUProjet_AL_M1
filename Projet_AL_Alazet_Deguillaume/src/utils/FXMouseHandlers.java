package utils;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.Canvas;
import model.FXImplementor;
import model.Shape;

/*
 * This class contains all the mouse handlers used by the implementor
 */
public class FXMouseHandlers {
    private final Shape shape;
    private final javafx.scene.shape.Shape FXShape;
    private final FXImplementor implementor;
    public FXMouseHandlers(Shape s, javafx.scene.shape.Shape FXShape) {
        this.shape = s;
        this.FXShape = FXShape;
        this.implementor = FXImplementor.getInstance();

    }

    /*
     * Changes the color of the shape the user has his mouse on
     */
    public void hoverColor(MouseEvent mouseEvent) {
        FXShape.setCursor(Cursor.HAND);
        Color c = (Color) FXShape.getFill();
        double r = c.getRed() * 255;
        double g = c.getGreen() * 255;
        double b = c.getBlue() * 255;
        double rt, gt, bt;
        rt = r + (0.25 * (255 - r));
        gt = g + (0.25 * (255 - g));
        bt = b + (0.25 * (255 - b));
        rt /= 255;
        gt /= 255;
        bt /= 255;

        FXShape.setFill(new Color(rt, gt, bt, 1));
    }

    /*
     * Sets the color of the shape back when the mouse exits the shape
     */
    public void setBackColor(MouseEvent mouseEvent) {
        FXShape.setFill(Color.valueOf(shape.getColor()));
    }

    /*
     * Permits to add a stroke to the selected shapes
     */
    public void selection(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            boolean isSelected = shape.isSelected();
            System.out.println(implementor.getCanvas().getChildren());
            implementor.getCanvas().getChildren().clear();
            if (!mouseEvent.isControlDown()) {
                Canvas.getInstance().resetSelection();
            }
            shape.setSelected(!isSelected);
            Canvas.getInstance().notifyAllShapes();
        }
    }

    /*
     *
     */



}
