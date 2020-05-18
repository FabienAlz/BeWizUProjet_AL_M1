package utils;

import javafx.scene.Cursor;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Canvas;
import model.FXImplementor;
import model.Position;
import model.Shape;
import view.View;

/**
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

    /***********************************************************
     *                     COMMON HANDLERS                     *
     ***********************************************************/
    /**
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

    /**
     * Sets the color of the shape back when the mouse exits the shape
     */
    public void setBackColor(MouseEvent mouseEvent) {
        FXShape.setFill(Color.valueOf(shape.getColor()));
    }

    /**
     * Permits to add a stroke to the selected shapes
     */
    public void selection(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            boolean isSelected = shape.isSelected();
            implementor.getCanvas().getChildren().clear();
            if (!mouseEvent.isControlDown()) {
                Canvas.getInstance().resetSelection();
            }
            shape.setSelected(!isSelected);
            Canvas.getInstance().notifyAllShapes();
        }
    }

    /*****************************************************
     *                  MOUSE SELECTION                  *
     *****************************************************/

    /**
     * Initializes the selection rectangle
     */
    public void setGestureStarted(MouseEvent mouseEvent) {
        if (FXShape instanceof Rectangle) {
            if (!implementor.getCanvas().getChildren().contains(mouseEvent.getTarget())) {
                Canvas.getInstance().resetSelection();
                implementor.getCanvas().getChildren().clear();
                Canvas.getInstance().notifyAllShapes();
                Canvas.getInstance().setStartSelectPos(mouseEvent.getX(), mouseEvent.getY());
                Canvas.getInstance().setSelection(true);
                implementor.getCanvas().getChildren().add(FXShape);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Updates the selection rectangle to make it follow the mouse
     */
    public void updateSelectionRectangle(MouseEvent mouseEvent) {
        if (Canvas.getInstance().getSelection()) {
            double posX = mouseEvent.getX();
            double posY = mouseEvent.getY();
            if(posX < 0) {
                posX = 0;
            }
            else if(posX > View.getInstance().canvas.getWidth()) {
                posX = View.getInstance().canvas.getWidth();
            }
            if(posY < 0) {
                posY = 0;
            } else if(posY > View.getInstance().canvas.getHeight()) {
                posY = View.getInstance().canvas.getHeight();
            }
            if (FXShape instanceof Rectangle) {
                Position firstPos = Canvas.getInstance().getStartSelectPos();
                // top-left to bottom-right
                if (posX > firstPos.getX() && posY > firstPos.getY()) {
                    ((Rectangle) FXShape).setX(firstPos.getX());
                    ((Rectangle) FXShape).setY(firstPos.getY());
                    ((Rectangle) FXShape).setWidth(posX - firstPos.getX());
                    ((Rectangle) FXShape).setHeight(posY - firstPos.getY());

                }
                // bottom-right to top-left
                else if (posX < firstPos.getX() && posY < firstPos.getY()) {
                    ((Rectangle) FXShape).setX(posX);
                    ((Rectangle) FXShape).setY(posY);
                    ((Rectangle) FXShape).setWidth(firstPos.getX() - posX);
                    ((Rectangle) FXShape).setHeight(firstPos.getY() - posY);
                }
                // bottom-left to top-right
                else if (posX > firstPos.getX() && posY < firstPos.getY()) {
                    ((Rectangle) FXShape).setX(firstPos.getX());
                    ((Rectangle) FXShape).setY(posY);
                    ((Rectangle) FXShape).setWidth(posX - firstPos.getX());
                    ((Rectangle) FXShape).setHeight(firstPos.getY() - posY);
                }
                // top-right to bottom-left
                else if (posX < firstPos.getX() && posY > firstPos.getY()) {
                    ((Rectangle) FXShape).setX(posX);
                    ((Rectangle) FXShape).setY(firstPos.getY());
                    ((Rectangle) FXShape).setWidth(firstPos.getX() - posX);
                    ((Rectangle) FXShape).setHeight(posY - firstPos.getY());
                }
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Selects the shapes inside the rectangle selection
     */
    public void endSelection(MouseEvent mouseEvent) {
        if (FXShape instanceof Rectangle) {
            if (Canvas.getInstance().getSelection()) {
                Position firstPos = Canvas.getInstance().getStartSelectPos();
                Canvas.getInstance().setSelection(false);
                double posX = mouseEvent.getX();
                double posY = mouseEvent.getY();
                if(posX < 0) {
                    posX = 0;
                }
                else if(posX > View.getInstance().canvas.getWidth()) {
                    posX = View.getInstance().canvas.getWidth();
                }
                if(posY < 0) {
                    posY = 0;
                } else if(posY > View.getInstance().canvas.getHeight()) {
                    posY = View.getInstance().canvas.getHeight();
                }
                Position secondPos = new Position(posX, posY);
                for (Shape s : Canvas.getInstance().getShapes()) {
                    // top-left to bottom-right
                    if (secondPos.getX() > firstPos.getX() && secondPos.getY() > firstPos.getY() && s.isInside(firstPos, secondPos)) {
                        s.setSelected(true);
                    }
                    // bottom-right to top-left
                    else if (secondPos.getX() < firstPos.getX() && secondPos.getY() < firstPos.getY() && s.isInside(secondPos, firstPos)) {
                        s.setSelected(true);
                    }
                    // bottom-left to top-right
                    else if (secondPos.getX() > firstPos.getX() && secondPos.getY() < firstPos.getY()) {
                        Position firstIntermediatePos = new Position(secondPos.getX(), firstPos.getY());
                        Position secondIntermediatePos = new Position(firstPos.getX(), secondPos.getY());
                        if (s.isInside(secondIntermediatePos, firstIntermediatePos)) {
                            s.setSelected(true);
                        }
                    }
                    // top-right to bottom-left
                    else if (secondPos.getX() < firstPos.getX() && secondPos.getY() > firstPos.getY()) {
                        Position firstIntermediatePos = new Position(secondPos.getX(), firstPos.getY());
                        Position secondIntermediatePos = new Position(firstPos.getX(), secondPos.getY());
                        if (s.isInside(firstIntermediatePos, secondIntermediatePos)) {
                            s.setSelected(true);
                        }
                    }

                }
                implementor.getCanvas().getChildren().clear();
                Canvas.getInstance().notifyAllShapes();
            }
            ((Rectangle) FXShape).setWidth(0);
            ((Rectangle) FXShape).setHeight(0);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public void onDragDetected(MouseEvent mouseEvent) {
        String id = Long.toString(shape.getId());
        Dragboard db = FXShape.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cp = new ClipboardContent();
        cp.putString(id);
        db.setContent(cp);
    }
}



