package model.mediatorFX;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.StackPane;
import model.Component;
import utils.FXDragAndDropHandlers;
import view.Mediator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Bin extends StackPane implements Component {
    private Mediator mediator;

    public Bin(String imageSrc) {
        super();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imageSrc);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image img = new Image(fis);
        ImageView bin = new ImageView(img);

        getChildren().add(bin);
        setBinHandlers();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "BinButton";
    }

    /**
     * Binds the handlers to the bin
     */
    private void setBinHandlers() {
        FXDragAndDropHandlers myDragAndDropHandler = new FXDragAndDropHandlers();
        setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myDragAndDropHandler.binOnDragDropped(event);
            }
        });

        setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myDragAndDropHandler.binOnDragOver(event);
            }
        });

        setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myDragAndDropHandler.binOnDragEntered(dragEvent);
            }
        });

        setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myDragAndDropHandler.binOnDragExited(dragEvent);
            }
        });
    }
}
