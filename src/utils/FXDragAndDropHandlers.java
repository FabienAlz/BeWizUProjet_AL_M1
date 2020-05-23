package utils;

import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import model.*;
import model.Toolbar;
import view.Originator;
import view.ViewFX;
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
     *
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
            if (Canvas.getInstance().contains(id)) {
                Shape original = Canvas.getInstance().getShape(id);
                // If the original isn't selected, only adds it to the toolbar
                if (!original.isSelected()) {
                    Shape copy = original.clone();
                    copy.setId();
                    if(copy instanceof Rectangle) {
                        copy.setPosition(new ToolbarPosition());
                        Toolbar.getInstance().addAndNotify(copy);

                    } else {

                        if (copy instanceof CompoundShape) {
                            implementor.createToolbarCompoundShape((CompoundShape) copy);
                            copy.setPosition(new ToolbarPosition());
                            Toolbar.getInstance().add(copy);
                            float ratio = (float) (copy.getWidth() / (ViewFX.getInstance().getToolbar().getPrefWidth() - 24));
                            if (ViewFX.getInstance().getToolbar().getHeight() < copy.getPositionI().getY() + copy.getHeight() / ratio) {
                                ViewFX.getInstance().getToolbar().setPrefHeight(copy.getPositionI().getY() + (copy.getHeight() / ratio) + 10);
                            }
                        } else {
                            copy.setPosition(new ToolbarPosition());
                            Toolbar.getInstance().addAndNotify(copy);
                        }
                    }
                }
                // Adds all the selected shapes in the toolbar
                else {
                    for (Shape s : Canvas.getInstance().getShapes()) {
                        if (s.isSelected()) {
                            Shape copy = s.clone();
                            copy.setId();
                            s.setSelected(false);
                            if (copy instanceof CompoundShape) {
                                implementor.createToolbarCompoundShape((CompoundShape) copy);
                                copy.setPosition(new ToolbarPosition());
                                Toolbar.getInstance().add(copy);
                            }
                            else {
                                copy.setPosition(new ToolbarPosition());
                                Toolbar.getInstance().addAndNotify(copy);
                            }
                        }
                    }
                }
            }
            success = true;
            Originator.getInstance().saveState();
        }
        /* let the source know whether the string was successfully
         * transferred and used */
        dragEvent.setDropCompleted(success);
        implementor.getCanvas().getChildren().clear();
        Canvas.getInstance().resetSelection();
        Canvas.getInstance().notifyAllShapes();
        ViewFX.getInstance().getToolbar().updateDisplay();
        dragEvent.consume();
    }

    /**
     * Shows the popup and move it according to the mouse
     *
     * @param dragEvent
     */
    public void toolbarOnDragOver(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()) {
            long id = Long.parseLong(db.getString());
            if (Canvas.getInstance().contains(id)) {
                implementor.getPopup().setX((int) (dragEvent.getScreenX()) - implementor.getPopup().getWidth() / 2);
                implementor.getPopup().setY((int) (dragEvent.getScreenY()) - implementor.getPopup().getHeight() * 2);
                implementor.getPopup().show(implementor.getStage());
            }
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);

        }
        dragEvent.consume();
    }

    /**
     * Initializes the popup
     *
     * @param dragEvent
     */
    public void toolbarOnDragEntered(DragEvent dragEvent) {
        Label label = new Label("Add to Toolbar");
        label.setStyle("-fx-background-color: #8a7876;");
        implementor.getPopup().getContent().clear();
        implementor.getPopup().getContent().add(label);
        dragEvent.consume();
    }

    /**
     * Hides the popup
     *
     * @param dragEvent
     */
    public void toolbarOnDragExited(DragEvent dragEvent) {
        implementor.getPopup().hide();
        dragEvent.consume();
    }

    /************************************************************
     *                   CANVAS HANDLERS                        *
     ************************************************************/

    /**
     * Handles the cases where something is dropped on the canvas
     *
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
            if (Canvas.getInstance().contains(id)) {

                Shape original = Canvas.getInstance().getShape(id);

                double posX = dragEvent.getX() - original.getWidth()/2;
                double posY = dragEvent.getY() - original.getHeight()/2;
                if (original instanceof CompoundShape) {
                    posX -= original.getPositionI().getX();
                    posY -= original.getPositionI().getY();

                    ((CompoundShape) original).translate(
                            new Translation(posX, posY));
                    implementor.getCanvas().getChildren().clear();
                    Canvas.getInstance().notifyAllShapes();
                } else {
                    original.setPosition(new CanvasPosition(posX, posY));
                    original.notifyObserver();
                }

            }
            // If the shape comes from the toolbar, adds it to the canvas
            else {
                Shape original = Toolbar.getInstance().getShape(id);
                Shape copy = original.clone();
                copy.setId();
                double posX = dragEvent.getX() - original.getWidth()/2;
                double posY = dragEvent.getY() - original.getHeight()/2;
                if (original instanceof CompoundShape) {
                    posX -= original.getPositionI().getX();
                    posY -= original.getPositionI().getY();
                    ((CompoundShape) copy).translate(new Translation(posX, posY));
                } else {
                    copy.setPosition(new CanvasPosition(posX, posY));
                }
                Canvas.getInstance().resetSelection();
                Canvas.getInstance().add(copy);

            }

            success = true;
            Originator.getInstance().saveState();

        }
        /* let the source know whether the string was successfully
         * transferred and used */
        dragEvent.setDropCompleted(success);
        implementor.getCanvas().getChildren().clear();
        Canvas.getInstance().notifyAllShapes();
        dragEvent.consume();

    }

    /**
     * Updates the popup and makes it follow the mouse if the shape comes from the toolbar
     *
     * @param dragEvent
     */
    public void canvasOnDragOver(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()) {
            long id = Long.parseLong(db.getString());
            if (Toolbar.getInstance().contains(id)) {
                implementor.getPopup().setX((int) (dragEvent.getScreenX()) - implementor.getPopup().getWidth() / 2);
                implementor.getPopup().setY((int) (dragEvent.getScreenY()) - implementor.getPopup().getHeight() * 2);
                implementor.getPopup().show(implementor.getStage());
            }
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);

        }
        dragEvent.consume();
    }

    /**
     * Initialize the popup
     *
     * @param dragEvent
     */
    public void canvasOnDragEntered(DragEvent dragEvent) {
        Label label = new Label("Add to Canvas");
        label.setStyle("-fx-background-color: #8a7876;");
        implementor.getPopup().getContent().clear();
        implementor.getPopup().getContent().add(label);

        dragEvent.consume();
    }

    /**
     * Hides the popup
     *
     * @param dragEvent
     */
    public void canvasOnDragExited(DragEvent dragEvent) {
        implementor.getPopup().hide();
        dragEvent.consume();

    }

    /************************************************************
     *                      BIN HANDLERS                        *
     ************************************************************/

    /**
     * Removes a Shape if it's dropped
     *
     * @param dragEvent
     */
    public void binOnDragDropped(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            long id = Long.parseLong(db.getString());
            // Remove from canvas
            if (Canvas.getInstance().contains(id)) {
                Shape original = Canvas.getInstance().getShape(id);
                // Remove all the selected shapes
                if (original.isSelected()) {
                    List<Shape> selectedShapes = new ArrayList<>();
                    for (Shape s : Canvas.getInstance().getShapes()) {
                        if (s.isSelected()) {
                            selectedShapes.add(s);
                        }
                    }
                    for (Shape s : selectedShapes) {
                        Canvas.getInstance().remove(s);
                        if (s instanceof CompoundShape) {
                            implementor.getCanvas().getChildren().clear();
                            for (Shape shape : ((CompoundShape) s).getShapes()) {
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
                        for (Shape s : ((CompoundShape) original).getShapes()) {
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

                float ratio = (float) (original.getWidth() / (ViewFX.getInstance().getToolbar().getPrefWidth() - 24));
                if (original instanceof Rectangle) {
                    if (original.getRotation() == 90 && (ViewFX.getInstance().getToolbar().getPrefHeight() - (((Rectangle) original).getAppearingHeight())) < ViewFX.getInstance().TOOLBAR_HEIGHT)
                        ViewFX.getInstance().getToolbar().setPrefHeight(ViewFX.getInstance().TOOLBAR_HEIGHT);
                    else if (original.getRotation() == 90)
                        ViewFX.getInstance().getToolbar().setPrefHeight(ViewFX.getInstance().getToolbar().getPrefHeight() - (((Rectangle) original).getAppearingHeight()) +10 );
                    else {
                        if ((ViewFX.getInstance().getToolbar().getPrefHeight() - (((Rectangle) original).getAppearingHeight() / ratio)) < ViewFX.getInstance().TOOLBAR_HEIGHT)
                            ViewFX.getInstance().getToolbar().setPrefHeight(ViewFX.getInstance().TOOLBAR_HEIGHT);
                            else  ViewFX.getInstance().getToolbar().setPrefHeight(ViewFX.getInstance().getToolbar().getPrefHeight() - (((Rectangle) original).getAppearingHeight() / ratio) +10 );
                    }
                }
                else if(ViewFX.getInstance().getToolbar().getHeight() > ViewFX.getInstance().TOOLBAR_HEIGHT) {
                    if((ViewFX.getInstance().getToolbar().getPrefHeight() - (original.getHeight() / ratio)) < ViewFX.getInstance().TOOLBAR_HEIGHT)
                           ViewFX.getInstance().getToolbar().setPrefHeight(ViewFX.getInstance().TOOLBAR_HEIGHT);
                    else
                           ViewFX.getInstance().getToolbar().setPrefHeight(ViewFX.getInstance().getToolbar().getPrefHeight() - (original.getHeight() / ratio) - 10);
                }

                Toolbar.getInstance().remove(original);

                implementor.getSHAPES().remove(original.getId());
                implementor.remove();
            }

            success = true;
            Originator.getInstance().saveState();

        }
        /* let the source know whether the string was successfully
         * transferred and used */
        dragEvent.setDropCompleted(success);
        implementor.getCanvas().getChildren().clear();

        Canvas.getInstance().resetSelection();
        Canvas.getInstance().notifyAllShapes();
        dragEvent.consume();
    }

    /**
     * Changes the opacity of the bin if the mouse is over it
     *
     * @param dragEvent
     */
    public void binOnDragEntered(DragEvent dragEvent) {
        Label label = new Label("Delete");
        label.setStyle("-fx-background-color: #8a7876;");
        implementor.getPopup().getContent().clear();
        implementor.getPopup().getContent().add(label);
        implementor.getBin().setOpacity(0.5);
        dragEvent.consume();
    }

    /**
     * Puts back the opacity of the bin
     *
     * @param dragEvent
     */
    public void binOnDragExited(DragEvent dragEvent) {
        Label label = new Label("Add to Toolbar");
        label.setStyle("-fx-background-color: #8a7876;");
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()) {
            implementor.getPopup().getContent().clear();
            implementor.getPopup().getContent().add(label);
            implementor.getBin().setOpacity(1);
            implementor.getPopup().hide();
        }
        dragEvent.consume();
    }

    /**
     * Shows the popup and move it according to the mouse
     *
     * @param dragEvent
     */
    public void binOnDragOver(DragEvent dragEvent) {
        Dragboard db = dragEvent.getDragboard();
        if (db.hasString()) {
            implementor.getPopup().setX((int) (dragEvent.getScreenX()) - implementor.getPopup().getWidth() / 2);
            implementor.getPopup().setY((int) (dragEvent.getScreenY()) - implementor.getPopup().getHeight() * 2);
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            implementor.getPopup().show(implementor.getStage());
        }
        dragEvent.consume();
    }
}
