package view;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.*;
import model.mediatorFX.*;

import java.util.HashMap;
import java.util.Map;

public final class EditorView implements Mediator {


    private Map<Long, Shape> shapeSaves = new HashMap<>();
    private static EditorView instance;
    private GridPane gridPane;
    private ColorPicker colorPicker;
    private OkButton ok;
    private ApplyButton apply;
    private CancelButton cancel;
    private Map<String, Label> labels = new HashMap<>();
    private Map<String, TextField> textFields = new HashMap<>();

    /**
     * Assigns component to the correct matching field
     *
     * @param component
     */
    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "ColorPicker":
                colorPicker = (ColorPicker) component;
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
                gridPane = (GridPane) component;
                break;
        }
    }

    /**
     * Unused
     */
    @Override
    public void createGUI() { }


    /**
     * Puts all the FX components needed for the Rectangle (or Compound Shape of Rectangles) editor window together
     */
    public void createRectangleEditor() {
        HBox hbValues = new HBox();
        hbValues.getChildren().addAll(labels.get("Width"), textFields.get("Width"));
        hbValues.getChildren().addAll(labels.get("Height"), textFields.get("Height"));
        hbValues.getChildren().addAll(labels.get("Border radius"), textFields.get("Border radius"));
        createSharedComponents(hbValues);

    }

    /**
     * Puts all the FX components needed for the Polygon (or Compound Shape of Polygon) editor window together
     */
    public void createPolygonEditor() {
        HBox hbValues = new HBox();
        hbValues.getChildren().addAll(labels.get("Edges"), textFields.get("Edges"));
        hbValues.getChildren().addAll(labels.get("Length"), textFields.get("Length"));
        createSharedComponents(hbValues);
    }

    /**
     * Puts all the FX components needed for the Compound Shape of both Polygons and Rectangles editor window together
     */
    public void createMixedEditor() {
        createSharedComponents(new HBox());
    }

    /**
     * Puts all the FX components that are shared between all editors together
     */
    public void createSharedComponents(HBox hbValues) {
        hbValues.setSpacing(10);
        hbValues.getChildren().addAll(labels.get("Rotation"), textFields.get("Rotation"));

        HBox hbColorPicker = new HBox();
        hbColorPicker.getChildren().add(colorPicker);

        HBox hbButtons = new HBox();
        hbButtons.getChildren().add(ok);
        hbButtons.getChildren().add(apply);
        hbButtons.getChildren().add(cancel);
        hbButtons.setSpacing(15);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(hbColorPicker, 0, 0);
        gridPane.add(hbValues, 0, 1);
        gridPane.add(hbButtons, 0, 2);
        gridPane.setStyle("-fx-background-color: #FFFFFF");
        View.getInstance().getCanvas().getChildren().addAll(gridPane);
    }

    /**
     * Singleton pattern
     *
     * @return a new EditorView if it's the first time it's called, the previously created instance otherwise
     */
    public static EditorView getInstance() {
        if (instance == null) {
            instance = new EditorView();
        }
        return instance;
    }

    /*****************
     *    GETTERS    *
     *****************/

    public Map<Long, Shape> getShapeSaves() {
        return shapeSaves;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public Map<String, TextField> getTextFields() {
        return textFields;
    }


}
