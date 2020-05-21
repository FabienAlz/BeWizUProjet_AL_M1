package model.mediatorFX;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import view.Mediator;
import view.View;
import java.io.*;
import java.util.List;

public class LoadButton extends Button {
    private Mediator mediator;
    private Stage primaryStage;

    public void setStage(Stage stage) {
        primaryStage = stage;
    }

    public LoadButton(String s, String imageSrc) {
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
     * On click, open a file chooser to pick a file. If the file is valid (not null and .ser extension) it will load its content
     */
    private void loadButtonHandler() {
        setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("ressources/saves"));
            File file = fileChooser.showOpenDialog(primaryStage);
            List<Shape> loadShapes = null;
            if (file != null) {
                if (file.toString().substring(file.toString().length() - 3).compareTo("ser") == 0) {
                    try {
                        FileInputStream fileIn = new FileInputStream(file);
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        loadShapes = (List<Shape>) in.readObject();
                        Canvas.getInstance().getShapes().clear();
                        Toolbar.getInstance().getShapes().clear();
                        Toolbar.getInstance().resetPosition();
                        View.getInstance().getCanvas().getChildren().clear();
                        View.getInstance().getToolbar().getChildren().clear();
                        View.getInstance().getToolbar().setPrefHeight(View.getInstance().TOOLBAR_HEIGHT);

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
                    } catch (IOException i) {
                        i.printStackTrace();
                        return;
                    } catch (ClassNotFoundException c) {
                        c.printStackTrace();
                        return;
                    }
                    View.getInstance().saveState();
                }
            }
        });
    }
}
