package utils;

import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import model.*;

import java.util.ArrayList;
import java.util.List;

/*
 * This class contains the handlers to drag and drop shapes
 */
public class FXDragAndDropHandlers {
    private final FXImplementor implementor;
    public FXDragAndDropHandlers() {
        this.implementor = FXImplementor.getInstance();
    }

    /************************************************************
     *                  TOOLBAR HANDLERS                        *
     ************************************************************/

    /**
     * Adds a shape from the canvas to the toolbar
     * @param dragEvent
     */
    public void toolbarOnDragDropped(DragEvent dragEvent) {
        /* data dropped */
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            long id = Long.parseLong(db.getString());
            // Checks if the event comes from the canvas
            // If it's the case, add the shape in the toolbar
            if(Canvas.getInstance().contains(id)) {
                Shape original = Canvas.getInstance().getShape(id);
                // If the original isn't selected, only adds it to the toolbar
                if(!original.isSelected()) {
                    Shape copy = original.clone();
                    copy.setId();
                    if (copy instanceof CompoundShape) {
                        implementor.createToolbarCompoundShape((CompoundShape) copy);
                        copy.setPosition(new ToolbarPosition());
                        Toolbar.getInstance().add(copy);
                    } else {
                        copy.setPosition(new ToolbarPosition());
                        Toolbar.getInstance().addAndNotify(copy);
                    }
                }
                // Add all the selected shapes in the toolbar
                else {
                    for (Shape s : Canvas.getInstance().getShapes()) {
                        if (s.isSelected()) {
                            Shape copy = s.clone();
                            copy.setId();
                            if (copy instanceof CompoundShape) {
                                implementor.createToolbarCompoundShape((CompoundShape) copy);
                                copy.setPosition(new ToolbarPosition());
                                Toolbar.getInstance().add(copy);
                            } else {
                                copy.setPosition(new ToolbarPosition());
                                Toolbar.getInstance().addAndNotify(copy);
                            }
                        }
                    }
                }
                Canvas.getInstance().resetSelection();
            }
            success = true;
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        dragEvent.setDropCompleted(success);

        dragEvent.consume();
    }

    /**
     * Shows the popup and move it according to the mouse
     * @param dragEvent
     */
    public void toolbarOnDragOver(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()) {
            long id = Long.parseLong(db.getString());
            if (Canvas.getInstance().contains(id)) {
                implementor.popup.setX((int) (dragEvent.getScreenX()) - implementor.popup.getWidth()/2);
                implementor.popup.setY((int) (dragEvent.getScreenY())- implementor.popup.getHeight()*2);
                implementor.popup.show(implementor.stage);
            }
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);

        }
        dragEvent.consume();
    }

    /**
     * Initializes the popup
     * @param dragEvent
     */
    public void toolbarOnDragEntered(DragEvent dragEvent) {
        Label label = new Label("Add to Toolbar");
        label.setStyle("-fx-background-color: #8a7876;");
        implementor.popup.getContent().clear();
        implementor.popup.getContent().add(label);
        dragEvent.consume();
    }

    /**
     * Hides the popup
     * @param dragEvent
     */
    public void toolbarOnDragExited(DragEvent dragEvent) {
        implementor.popup.hide();
        dragEvent.consume();
    }

    /************************************************************
     *                   CANVAS HANDLERS                        *
     ************************************************************/

    /**
     * Handles the cases where something is dropped on the canvas
     * @param dragEvent
     */
    public void canvasOnDragDropped(DragEvent dragEvent) {
        /* data dropped */
        /* if there is a string data on dragboard, read it and use it */
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            long id = Long.parseLong(db.getString());
            // If the shape comes from the canvas, changes its position
            if(Canvas.getInstance().contains(id)) {
                Shape original = Canvas.getInstance().getShape(id);
                Shape copy = original.clone();
                Canvas.getInstance().remove(original);
                if(original instanceof CompoundShape) {
                    ((CompoundShape)copy).translate(new Position(dragEvent.getX()-((CompoundShape) original).getTopLeft().getX(),
                            dragEvent.getY()-((CompoundShape) original).getTopLeft().getY()));
                    implementor.getCanvas().getChildren().clear();
                    Canvas.getInstance().add(copy);
                    Canvas.getInstance().notifyAllShapes();
                }
                else {
                    implementor.getCanvas().getChildren().remove(implementor.getSHAPES().get(original.getId()));
                    copy.setPosition(new CanvasPosition(dragEvent.getX(), dragEvent.getY()));
                    Canvas.getInstance().addAndNotify(copy);
                }
            }
            // If the shape comes from the toolbar, adds it to the canvas
            else {
                Shape original = Toolbar.getInstance().getShape(id);
                Shape copy = original.clone();
                copy.setId();
                if(original instanceof CompoundShape) {
                    ((CompoundShape)copy).translate(new Position(dragEvent.getX()-((CompoundShape) original).getTopLeft().getX(),
                            dragEvent.getY()-((CompoundShape) original).getTopLeft().getY()));
                }
                else {
                    copy.setPosition(new CanvasPosition(dragEvent.getX(), dragEvent.getY()));
                }
                Canvas.getInstance().resetSelection();
                Canvas.getInstance().add(copy);
                implementor.getCanvas().getChildren().clear();
                Canvas.getInstance().notifyAllShapes();
            }

            success = true;
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        dragEvent.setDropCompleted(success);

        dragEvent.consume();
    }

    /**
     * Updates the popup and makes it follow the mouse if the shape comes from the toolbar
     * @param dragEvent
     */
    public void canvasOnDragOver(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()) {
            long id = Long.parseLong(db.getString());
            if (Toolbar.getInstance().contains(id)) {
                implementor.popup.setX((int) (dragEvent.getScreenX()) - implementor.popup.getWidth()/2);
                implementor.popup.setY((int) (dragEvent.getScreenY())- implementor.popup.getHeight()*2);
                implementor.popup.show(implementor.stage);
            }
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);

        }
        dragEvent.consume();
    }

    /**
     * Initialize the popup
     * @param dragEvent
     */
    public void canvasOnDragEntered(DragEvent dragEvent) {
        Label label = new Label("Add to Canvas");
        label.setStyle("-fx-background-color: #8a7876;");
        implementor.popup.getContent().clear();
        implementor.popup.getContent().add(label);
        dragEvent.consume();

    }

    /**
     * Hides the popup
     * @param dragEvent
     */
    public void canvasOnDragExited(DragEvent dragEvent) {
        implementor.popup.hide();
        dragEvent.consume();

    }

    /************************************************************
     *                      BIN HANDLERS                        *
     ************************************************************/

    /**
     * Removes a Shape if it's dropped
     * @param dragEvent
     */
    public void binOnDragDropped(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            long id = Long.parseLong(db.getString());
            // Remove from canvas
            if(Canvas.getInstance().contains(id)) {
                Shape original = Canvas.getInstance().getShape(id);
                // Remove all the selected shapes
                if(original.isSelected()) {
                    List<Shape> selectedShapes = new ArrayList<>();
                    for(Shape s : Canvas.getInstance().getShapes()) {
                        if(s.isSelected()) {
                           selectedShapes.add(s);
                        }
                    }
                    for(Shape s : selectedShapes) {
                        Canvas.getInstance().remove(s);
                        if (s instanceof CompoundShape) {
                            implementor.getCanvas().getChildren().clear();
                            for(Shape shape : ((CompoundShape) s).getShapes()) {
                                implementor.getSHAPES().remove(shape.getId());
                            }
                            Canvas.getInstance().notifyAllShapes();
                        } else {
                            implementor.getCanvas().getChildren().remove(implementor.getSHAPES().get(s.getId()));
                            implementor.getSHAPES().remove(s.getId());
                        }
                    }
                }
                // Remove only the original
                else {
                    Canvas.getInstance().remove(original);
                    if (original instanceof CompoundShape) {
                        implementor.getCanvas().getChildren().clear();
                        for(Shape s : ((CompoundShape) original).getShapes()) {
                            implementor.getSHAPES().remove(s.getId());
                        }
                        Canvas.getInstance().notifyAllShapes();
                    } else {
                        implementor.getCanvas().getChildren().remove(implementor.getSHAPES().get(id));
                        implementor.getSHAPES().remove(original.getId());
                    }
                }
            }
            // Remove from Toolbar
            else {
                Shape original = Toolbar.getInstance().getShape(id);
                Toolbar.getInstance().remove(original);
                System.out.println(implementor.getSHAPES());
                implementor.getSHAPES().remove(original.getId());
                System.out.println(implementor.getSHAPES());

                implementor.remove();
            }

            success = true;
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    /**
     * Changes the opacity of the bin if the mouse is over it
     * @param dragEvent
     */
    public void binOnDragEntered(DragEvent dragEvent) {
        Label label = new Label("Delete");
        label.setStyle("-fx-background-color: #8a7876;");
        implementor.popup.getContent().clear();
        implementor.popup.getContent().add(label);
        implementor.getBin().setOpacity(0.5);
        dragEvent.consume();
    }

    /**
     * Puts back the opacity of the bin
     * @param dragEvent
     */
    public void binOnDragExited(DragEvent dragEvent) {
        Label label = new Label("Add to Toolbar");
        label.setStyle("-fx-background-color: #8a7876;");
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()) {
            implementor.popup.getContent().clear();
            implementor.popup.getContent().add(label);
            implementor.getBin().setOpacity(1);
            long id = Long.parseLong(db.getString());
            if (Toolbar.getInstance().contains(id)) {
                implementor.popup.hide();
            }
        }
        dragEvent.consume();
    }

    /**
     * Shows the popup and move it according to the mouse
     * @param dragEvent
     */
    public void binOnDragOver(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()) {
            implementor.popup.setX((int) (dragEvent.getScreenX()) - implementor.popup.getWidth()/2);
            implementor.popup.setY((int) (dragEvent.getScreenY())- implementor.popup.getHeight()*2);
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);

            long id = Long.parseLong(db.getString());
            if (Toolbar.getInstance().contains(id)) {
                implementor.popup.show(implementor.stage);
            }
        }
        dragEvent.consume();
    }
}