package model.mediatorFX;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Canvas;
import model.Shape;
import model.Toolbar;
import view.Mediator;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaveButton extends Button {
    private Mediator mediator;
    private Stage primaryStage;

    public void setStage(Stage stage) {
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

    /**
     * On click, open a file chooser to name a file then saves the toolbar and canvas shapes inside
     */
    private void saveButtonHandler() {
        setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File("ressources/saves"));
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("ser files (*.ser)", "*.ser");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
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
        });
    }
}
