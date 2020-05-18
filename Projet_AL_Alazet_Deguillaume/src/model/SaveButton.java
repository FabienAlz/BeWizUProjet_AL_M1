package model;

import javafx.scene.control.ToolBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import view.Mediator;
import view.View;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveButton extends FXButton {
    private Mediator mediator;
    private Stage primaryStage;

    public void setStage(Stage stage){
        primaryStage = stage;
    }

    public SaveButton(String s, String imageSrc) {
        super(s, imageSrc);
        saveButtonHandler();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "SaveButton";
    }

    private void saveButtonHandler() {
        setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("Projet_AL_Alazet_Deguillaume/ressources/saves"));
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ser files (*.ser)", "*.ser");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(primaryStage);
            if(file!=null) {
                try {
                    FileOutputStream fileOut =
                            new FileOutputStream(file);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);

                    List<Shape> shapesToSave = new ArrayList<>();
                    shapesToSave.addAll(Canvas.getInstance().getShapes());
                    shapesToSave.addAll(Toolbar.getInstance().getShapes());
                    out.writeObject(shapesToSave);
                    out.close();
                    fileOut.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException i) {
                    i.printStackTrace();
                } catch (NullPointerException np) {
                    np.printStackTrace();
                }
            }

            /******
             * LOAD
             *********/

            List<Shape> loadShapes = null;
            try {
                FileInputStream fileIn = new FileInputStream("data.ser");
                ObjectInputStream in = new ObjectInputStream(fileIn);
                loadShapes = (List<Shape>) in.readObject();
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
        });
    }
}
