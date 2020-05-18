package model;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.Mediator;
import view.View;

import javax.tools.Tool;
import java.io.*;
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
            if(file!=null) {
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
                        ShapeObserver obs = new ConcreteShapeObserver();
                        try {
                            loadShapes.get(0).getImplementor().start(primaryStage);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        for (Shape s : loadShapes) {
                            s.addObserver(obs);
                            if (s.getPositionI() instanceof ToolbarPosition)
                                Toolbar.getInstance().addAndNotify(s);
                            else if (s.getPositionI() instanceof CanvasPosition)
                                Canvas.getInstance().addAndNotify(s);
                        }
                        in.close();
                        fileIn.close();
                    } catch (IOException i) {
                        i.printStackTrace();
                        return;
                    } catch (ClassNotFoundException c) {
                        System.out.println("List<Shape> class not found");
                        c.printStackTrace();
                        return;
                    }
                    Caretaker.getInstance().saveState();
                }
            }
        });
    }
}
