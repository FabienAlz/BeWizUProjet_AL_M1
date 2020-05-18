package model;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import utils.FXDragAndDropHandlers;
import view.Mediator;

import java.util.ArrayList;
import java.util.List;

public class FXToolbar extends javafx.scene.layout.Pane implements Component {
    private Mediator mediator;

    public FXToolbar() {
        super();
        setToolbarHandlers();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "FXToolbar";
    }

    /**
     * binds the handlers to the toolbar
     */
    private void setToolbarHandlers() {
        FXDragAndDropHandlers myHandler = new FXDragAndDropHandlers();
        setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myHandler.toolbarOnDragDropped(event);
            }
        });

        setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent dragEvent) {
                myHandler.toolbarOnDragOver(dragEvent);
            }
        });

        setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myHandler.toolbarOnDragEntered(dragEvent);
            }
        });

        setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myHandler.toolbarOnDragExited(dragEvent);
            }
        });
    }
}
