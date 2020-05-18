package model;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import utils.FXDragAndDropHandlers;
import utils.FXMouseHandlers;
import view.Mediator;

import java.util.ArrayList;
import java.util.List;

public class FXCanvas extends javafx.scene.layout.Pane implements Component {
    private Mediator mediator;

    public FXCanvas() {
        setCanvasHandlers();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
         public String getName() {
             return "FXCanvas";
         }

    private void setCanvasHandlers() {
        javafx.scene.shape.Rectangle rectangleSelection = new javafx.scene.shape.Rectangle();
        rectangleSelection.setFill(new Color(1,0,1,1));
        rectangleSelection.setStroke(new Color(0, 0, 1, 1));
        rectangleSelection.setOpacity(0.3);
        rectangleSelection.setVisible(false);
        FXMouseHandlers myHandler = new FXMouseHandlers(null, rectangleSelection);
        FXDragAndDropHandlers myDragAndDropHandler = new FXDragAndDropHandlers();

        /*******************************************************
         *                    DRAG EVENTS                      *
         *******************************************************/
        setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myDragAndDropHandler.canvasOnDragDropped(event);
            }
        });

        setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myDragAndDropHandler.canvasOnDragOver(event);
            }
        });

        setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myDragAndDropHandler.canvasOnDragEntered(dragEvent);
            }
        });

        setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myDragAndDropHandler.canvasOnDragExited(dragEvent);
            }
        });

        /*************************************************
         *          MOUSE HANDLERS FOR SELECTION         *
         *************************************************/
        EventHandler<MouseEvent> setGestureStarted = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                myHandler.setGestureStarted(e);
            }
        };

        setOnMousePressed(setGestureStarted);

        EventHandler<MouseEvent> updateSelectionRectangle = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                myHandler.updateSelectionRectangle(e);
            }
        };

        setOnMouseDragged(updateSelectionRectangle);

        EventHandler<MouseEvent> endSelection = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                myHandler.endSelection(e);
            }

        };

        addEventFilter(MouseEvent.MOUSE_RELEASED, endSelection);
    }
}
