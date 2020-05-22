package model.mediatorFX;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import model.*;
import utils.FXDragAndDropHandlers;
import view.Mediator;
import view.ViewFX;

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

    public void updateDisplay() {
        boolean scrollToDisable = true;
        for (Shape s : Toolbar.getInstance().getShapes()) {
            float ratio;
            if (s instanceof Polygon) {
                ratio = (float) (s.getWidth() / (ViewFX.getInstance().getToolbar().getPrefWidth() - 35));
            } else ratio = (float) (s.getWidth() / (ViewFX.getInstance().getToolbar().getPrefWidth() - 24));
            if (s instanceof Rectangle) {
                if (s.getRotation() == 90 && ViewFX.getInstance().getToolbar().getHeight() < s.getPositionI().getY() + ((Rectangle) s).getAppearingHeight()) {
                    ViewFX.getInstance().getToolbar().setPrefHeight(s.getPositionI().getY() + (((Rectangle) s).getAppearingHeight())+10);
                    scrollToDisable = false;
                } else if (ViewFX.getInstance().getToolbar().getHeight() < s.getPositionI().getY() + ((Rectangle) s).getAppearingHeight() / ratio) {
                    ViewFX.getInstance().getToolbar().setPrefHeight(s.getPositionI().getY() + (((Rectangle) s).getAppearingHeight() / ratio)+10);
                    scrollToDisable = false;
                }
            } else if (s instanceof CompoundShape) {
                if (ViewFX.getInstance().getToolbar().getHeight() < Toolbar.getInstance().getNextPosition().getY()) {
                    ViewFX.getInstance().getToolbar().setPrefHeight(Toolbar.getInstance().getNextPosition().getY());
                    scrollToDisable = false;
                }
            } else if (ViewFX.getInstance().getToolbar().getHeight() < s.getPositionI().getY() + s.getHeight() / ratio) {
                ViewFX.getInstance().getToolbar().setPrefHeight(s.getPositionI().getY() + (s.getHeight() / ratio));
                scrollToDisable = false;
            }
        }
        if (scrollToDisable)
            ViewFX.getInstance().getToolbar().setPrefHeight(ViewFX.getInstance().TOOLBAR_HEIGHT);
    }

    /**
     * Binds the handlers to the toolbar
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
