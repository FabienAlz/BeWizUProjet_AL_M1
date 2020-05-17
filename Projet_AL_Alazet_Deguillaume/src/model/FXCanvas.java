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
    private List<Node> subComponents;

    public FXCanvas(List<Node> subComponents) {
        this.subComponents = new ArrayList<>();
        this.subComponents.addAll(subComponents);
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
        rectangleSelection.setOpacity(0.3);
        rectangleSelection.setFill(new Color(1,0,1,1));
        rectangleSelection.setStroke(new Color(0, 0, 1, 1));
        rectangleSelection.setStrokeWidth(2);
        FXMouseHandlers myHandler = new FXMouseHandlers(null, rectangleSelection);
        FXDragAndDropHandlers myDragAndDropHandler = new FXDragAndDropHandlers();

        /*******************************************************
         *                    DRAG EVENTS                      *
         *******************************************************/
        this.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myDragAndDropHandler.canvasOnDragDropped(event);
            }
        });

        ((Pane)this).setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myDragAndDropHandler.canvasOnDragOver(event);
            }
        });

        this.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {

                System.out.println("TEST");
                myDragAndDropHandler.canvasOnDragEntered(dragEvent);
            }
        });

        this.setOnDragExited(new EventHandler<DragEvent>() {
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

        this.setOnMousePressed(setGestureStarted);

        EventHandler<MouseEvent> updateSelectionRectangle = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                myHandler.updateSelectionRectangle(e);
            }
        };
        this.setOnMouseDragged(updateSelectionRectangle);

        EventHandler<MouseEvent> endSelection = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                myHandler.endSelection(e);
            }

        };

        this.addEventFilter(MouseEvent.MOUSE_RELEASED, endSelection);
    }
}
