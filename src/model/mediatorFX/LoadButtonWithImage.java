package model.mediatorFX;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import view.Mediator;
import view.Originator;
import view.ViewFX;
import java.io.*;
import java.util.List;

public class LoadButtonWithImage extends ButtonWithImage {
    private Mediator mediator;
    private Stage primaryStage;

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    public LoadButtonWithImage(String s, String imageSrc) {
        super(s, imageSrc);
        loadButtonHandler();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "LoadButton";
    }

    /**
     * On click, opens a file chooser to pick a file. If the file is valid (not null and .ser extension) it will load its content
     */
    private void loadButtonHandler() {
        setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("resources/saves"));
            File file = fileChooser.showOpenDialog(primaryStage);
            FXImplementor.getInstance().initializeFXImplementor(primaryStage);

            if (file != null) {
                if (file.toString().substring(file.toString().length() - 3).compareTo("ser") == 0) {
                    Canvas.getInstance().getShapes().clear();
                    Toolbar.getInstance().getShapes().clear();
                    Toolbar.getInstance().resetPosition();
                    ViewFX.getInstance().getCanvas().getChildren().clear();
                    ViewFX.getInstance().getToolbar().getChildren().clear();
                    ViewFX.getInstance().getToolbar().setPrefHeight(ViewFX.getInstance().TOOLBAR_HEIGHT);
                    FXImplementor.getInstance().loadFile(file);
                }
                Originator.getInstance().saveState();
                ViewFX.getInstance().getToolbar().updateDisplay();
            }

            /*
                if (file.toString().substring(file.toString().length() - 3).compareTo("ser") == 0) {
                    try {
                        FileInputStream fileIn = new FileInputStream(file);
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        loadShapes = (List<Shape>) in.readObject();
                        Canvas.getInstance().getShapes().clear();
                        Toolbar.getInstance().getShapes().clear();
                        Toolbar.getInstance().resetPosition();
                        ViewFX.getInstance().getCanvas().getChildren().clear();
                        ViewFX.getInstance().getToolbar().getChildren().clear();
                        ViewFX.getInstance().getToolbar().setPrefHeight(ViewFX.getInstance().TOOLBAR_HEIGHT);

                        ShapeObserver obs = new ConcreteShapeObserver();
                        for (Shape s : loadShapes) {
                            try {
                                s.setImplementor(FXImplementor.getInstance());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            if (s instanceof CompoundShape) {
                                for (Shape subShape : ((CompoundShape) s).getShapes()) {
                                    subShape.addObserver(obs);
                                }
                            }
                            s.addObserver(obs);

                            if (s.getPositionI() instanceof ToolbarPosition) {
                                Toolbar.getInstance().addAndNotify(s);
                            } else if (s.getPositionI() instanceof CanvasPosition)
                                Canvas.getInstance().addAndNotify(s);
                        }
                        in.close();
                        fileIn.close();
                    } catch (IOException | ClassNotFoundException i) {
                        i.printStackTrace();
                        return;
                    }
                    Originator.getInstance().saveState();
                }
            }*/
        });
    }
}
