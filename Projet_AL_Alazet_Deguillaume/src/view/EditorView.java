package view;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EditorView implements Mediator {

    private static EditorView instance;
    private GridPane gridPane;
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


    private TextField widthTextField;
    private ColorPicker colorPicker;
    private OkButton ok;
    private ApplyButton apply;
    private CancelButton cancel;
    private Map<String, Label> labels = new HashMap<>();
    private Map<String, TextField> textFields = new HashMap<>();

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
    public void hideElements(boolean flag) {

    }

    @Override
    public void createGUI(Stage primaryStage) {
    }


    public void createRectangleEditor() {
        System.out.println("CREATE RECTANGLE EDITOR");

        HBox hbColorPicker = new HBox();
        hbColorPicker.getChildren().add(colorPicker);

        HBox hbValues = new HBox();
        hbValues.getChildren().addAll(labels.get("Width"),textFields.get("Width"));
        hbValues.getChildren().addAll(labels.get("Height"),textFields.get("Height"));
        hbValues.getChildren().addAll(labels.get("Rotation"),textFields.get("Rotation"));
        hbValues.getChildren().addAll(labels.get("Border radius"),textFields.get("Border radius"));
        hbValues.setSpacing(10);

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
        View.getInstance().canvas.getChildren().addAll(gridPane);

    }


    public void createPolygonEditor() {
        System.out.println("CREATE POLYGON EDITOR");

        HBox hbColorPicker = new HBox();
        hbColorPicker.getChildren().add(colorPicker);

        HBox hbValues = new HBox();
        hbValues.getChildren().addAll(labels.get("Edges"),textFields.get("Edges"));
        hbValues.getChildren().addAll(labels.get("Length"),textFields.get("Length"));
        hbValues.getChildren().addAll(labels.get("Rotation"),textFields.get("Rotation"));
        hbValues.setSpacing(10);

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
        View.getInstance().canvas.getChildren().addAll(gridPane);

    }

    public void createMixedEditor() {
        System.out.println("CREATE MIXED EDITOR");

        HBox hbColorPicker = new HBox();
        hbColorPicker.getChildren().add(colorPicker);

        HBox hbValues = new HBox();
        hbValues.getChildren().addAll(labels.get("Rotation"),textFields.get("Rotation"));
        hbValues.setSpacing(10);

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
        View.getInstance().canvas.getChildren().addAll(gridPane);
    }

}
/*
    RECT
    Colorpicker width height rotation border radius ok apply cancel

    POLY
    Colorpicker edges length rotation ok apply cancel

    MIXED
    Colorpicker rotation? ok apply cancel
 */