package model;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.Mediator;
import view.View;

import javax.tools.Tool;
import java.io.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class LoadButton extends FXButton {
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

    private void loadButtonHandler() {
        setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("C:/Users/Shadow/Desktop/GL/AL/projet/BeWizUProjet_AL_M1/Projet_AL_Alazet_Deguillaume/ressources/saves"));
            File file = fileChooser.showOpenDialog(primaryStage);
            List<Shape> loadShapes = null;
            boolean scrollToDisable = true;
            if (file != null) {
                if (file.toString().substring(file.toString().length() - 3).compareTo("ser") == 0) {
                    try {
                        FileInputStream fileIn = new FileInputStream(file);
                        ObjectInputStream in = new ObjectInputStream(fileIn);
                        loadShapes = (List<Shape>) in.readObject();
                        Canvas.getInstance().getShapes().clear();
                        Toolbar.getInstance().getShapes().clear();
                        Toolbar.getInstance().resetPosition();
                        View.getInstance().canvas.getChildren().clear();
                        View.getInstance().toolbar.getChildren().clear();
                        View.getInstance().toolbar.setPrefHeight(View.getInstance().TOOLBAR_HEIGHT);

                        ShapeObserver obs = new ConcreteShapeObserver();
                        for (Shape s : loadShapes) {
                            try {
                                s.getImplementor().initializeFXImplementor(primaryStage);
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
                                float ratio = (float) (s.getWidth() / (View.getInstance().toolbar.getPrefWidth() - 24));
                                if (View.getInstance().toolbar.getHeight() < Toolbar.getInstance().getNextPosition().getY()) {
                                    View.getInstance().toolbar.setPrefHeight(View.getInstance().toolbar.getPrefHeight() + (s.getHeight() / ratio) + 10);
                                    scrollToDisable = false;
                                }
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
                    Caretaker.getInstance().saveState();

                    if (scrollToDisable)
                        View.getInstance().toolbar.setPrefHeight(View.getInstance().TOOLBAR_HEIGHT);

                }
            }
        });
    }
}
