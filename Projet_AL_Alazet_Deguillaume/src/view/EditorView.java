package view;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EditorView implements Mediator {


    public Map<Long, Shape> shapeSaves = new HashMap<>();
    private static EditorView instance;
    public GridPane gridPane;
    /**
     * Singleton pattern
     * @return
     */
    public static EditorView getInstance() {
        if (instance == null) {
            instance = new EditorView();
        }
        return instance;
    }


    public ColorPicker colorPicker;
    private OkButton ok;
    private ApplyButton apply;
    private CancelButton cancel;
    public Map<String, Label> labels = new HashMap<>();
    public Map<String, TextField> textFields = new HashMap<>();

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "ColorPicker":
                colorPicker = (model.ColorPicker) component;
                break;
            case "OkButton":
                ok = (OkButton) component;
                break;
            case "ApplyButton":
                apply = (ApplyButton) component;
                break;
            case "CancelButton":
                cancel = (CancelButton) component;
                break;
            case "Label":
                labels.put(((Label) component).getID(), (Label) component);
                break;
            case "TextField":
                textFields.put(((TextField) component).getID(), (TextField) component);
                break;

            case "GridPane":
                gridPane = (GridPane)component;
                break;
        }
    }

    @Override
    public void createGUI(Stage primaryStage) {
    }


    public void createRectangleEditor() {
        HBox hbValues = new HBox();
        hbValues.getChildren().addAll(labels.get("Width"),textFields.get("Width"));
        hbValues.getChildren().addAll(labels.get("Height"),textFields.get("Height"));
        hbValues.getChildren().addAll(labels.get("Border radius"),textFields.get("Border radius"));
        createSharedComponents(hbValues);

    }


    public void createPolygonEditor() {
        HBox hbValues = new HBox();
        hbValues.getChildren().addAll(labels.get("Edges"),textFields.get("Edges"));
        hbValues.getChildren().addAll(labels.get("Length"),textFields.get("Length"));
        createSharedComponents(hbValues);
    }

    public void createMixedEditor() {
        createSharedComponents(new HBox());
    }

    public void createSharedComponents(HBox hbValues) {
        hbValues.setSpacing(10);
        hbValues.getChildren().addAll(labels.get("Rotation"),textFields.get("Rotation"));

        HBox hbColorPicker = new HBox();
        hbColorPicker.getChildren().add(colorPicker);


        HBox hbButtons = new HBox();
        hbButtons.getChildren().add(ok);
        hbButtons.getChildren().add(apply);
        hbButtons.getChildren().add(cancel);
        hbButtons.setSpacing(15);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(hbColorPicker,0,0);
        gridPane.add(hbValues,0,1);
        gridPane.add(hbButtons,0,2);
        gridPane.setStyle("-fx-background-color: #FFFFFF");
        View.getInstance().getCanvas().getChildren().addAll(gridPane);
    }

}
