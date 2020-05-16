package utils;

import javafx.scene.Node;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import model.Canvas;
import model.FXImplementor;
import model.Shape;

/*
 * THis class contains all the context menu handlers
 */
public class FXContextMenuHandlers {
    private final Shape shape;
    private final javafx.scene.shape.Shape FXShape;
    private final FXImplementor implementor;
    public FXContextMenuHandlers(Shape s, javafx.scene.shape.Shape FXShape) {
        this.shape = s;
        this.FXShape = FXShape;
        this.implementor = FXImplementor.getInstance();
    }

    /*
     * Resets selection and shows the context menu
     */
    public void manageContextMenu(ContextMenuEvent contextMenuEvent) {
        if (!shape.isSelected()) {
            // Makes the shape selected and deselect the others
            for(Shape s : Canvas.getInstance().getShapes()) {
                if (s.isSelected()) {
                    s.setSelected(false);
                }
            }
            shape.setSelected(true);
            FXImplementor.getInstance().setLastSelected(shape, FXShape);
            // Updates the canvas
            implementor.getCanvas().getChildren().clear();
            Canvas.getInstance().notifyAllShapes();
            // Finds the new FXShape (with stroke) to bind with the context menu since the last one was destroyed
            for(Node n : implementor.getCanvas().getChildren()) {
                if (n.getClass() == FXShape.getClass()) {
                    if (n instanceof javafx.scene.shape.Rectangle) {
                        // Compares the position and the size
                        if (((Rectangle) n).getX() == ((Rectangle) FXShape).getX() && ((Rectangle) n).getY() == ((Rectangle) FXShape).getY() &&
                                ((Rectangle) n).getWidth() == ((Rectangle) FXShape).getWidth() && ((Rectangle) n).getHeight() == ((Rectangle) FXShape).getHeight()) {
                            implementor.getContextMenu().show(n, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
                            break;
                        }
                    }
                    else if(n instanceof javafx.scene.shape.Polygon) {
                        boolean isEqual = true;
                        // Compares the position of the points
                        for(int i = 0; i < ((Polygon) n).getPoints().size(); i++) {
                            if(!((Polygon) n).getPoints().get(i).equals(((Polygon) FXShape).getPoints().get(i))) {
                                isEqual = false;
                                break;
                            }
                        }
                        if(isEqual) {
                            implementor.getContextMenu().show(n, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
                            break;
                        }
                    }
                }
            }
        }
        else {
            implementor.getContextMenu().show(FXShape, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            FXImplementor.getInstance().setLastSelected(shape, FXShape);
        }
    }


}
