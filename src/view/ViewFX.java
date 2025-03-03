package view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.*;
import model.mediatorFX.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public final class ViewFX implements Mediator {

    private static ViewFX instance;
    private Popup popup;
    private SaveButtonWithImage save;
    private LoadButtonWithImage load;
    private UndoButtonWithImage undo;
    private RedoButtonWithImage redo;
    private Bin bin;
    private Menu menu;
    private ScrollPane toolbarWrapper;
    private FXToolbar toolbar;
    private FXCanvas canvas;
    private ContextMenu contextMenu;
    private MenuItem menuItem;
    public double TOOLBAR_WIDTH = 128;
    public double TOOLBAR_HEIGHT = 530;
    public double MENU_HEIGHT = 51;
    public double SCENE_WIDTH = 1080;
    public double SCENE_HEIGHT = 650;
    private Group root;


    /**
     * Assigns component to the correct matching field
     *
     * @param component
     */
    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "SaveButton":
                save = (SaveButtonWithImage) component;
                break;
            case "Popup":
                popup = (Popup) component;
                break;
            case "LoadButton":
                load = (LoadButtonWithImage) component;
                break;
            case "UndoButton":
                undo = (UndoButtonWithImage) component;
                break;
            case "RedoButton":
                redo = (RedoButtonWithImage) component;
                break;
            case "BinButton":
                bin = (Bin) component;
                break;
            case "FXMenu":
                menu = (Menu) component;
                break;
            case "FXToolbar":
                toolbar = (FXToolbar) component;
                break;
            case "FXCanvas":
                canvas = (FXCanvas) component;
                break;
            case "FXContextMenu":
                contextMenu = (ContextMenu) component;
                break;
            case "FXMenuItem":
                menuItem = (MenuItem) component;
                break;
        }
    }

    /**
     * Creates all the FX components needed for the first screen of the application then displays them
     */
    @Override
    public void createGUI() {
        root = new Group();
        Stage primaryStage = FXImplementor.getStage();
        primaryStage.setResizable(false);
        primaryStage.setTitle("BeWizU");
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        ToolBar concreteMenu = new ToolBar();
        concreteMenu.prefWidthProperty().bind(primaryStage.widthProperty());
        concreteMenu.setPrefHeight(MENU_HEIGHT);

        concreteMenu.getItems().add(save);
        save.setStage(primaryStage);
        concreteMenu.getItems().add(new Separator());

        concreteMenu.getItems().add(load);
        load.setStage(primaryStage);
        concreteMenu.getItems().add(new Separator());

        concreteMenu.getItems().add(undo);
        concreteMenu.getItems().add(new Separator());

        toolbar.setPrefSize(TOOLBAR_WIDTH, TOOLBAR_HEIGHT);
        toolbar.setStyle("-fx-background-color: #ebeee6");
        toolbar.setLayoutY(concreteMenu.getPrefHeight());

        toolbarWrapper = new ScrollPane(toolbar);
        toolbarWrapper.fitToWidthProperty().set(true);
        toolbarWrapper.setPrefSize(TOOLBAR_WIDTH, TOOLBAR_HEIGHT + 2);
        toolbarWrapper.setStyle("-fx-background-color: #ebeee6");
        toolbarWrapper.setLayoutY(concreteMenu.getPrefHeight());

        bin.setPrefSize(TOOLBAR_WIDTH + 1, SCENE_HEIGHT - (MENU_HEIGHT + TOOLBAR_HEIGHT));
        bin.setLayoutY(MENU_HEIGHT + TOOLBAR_HEIGHT);
        bin.setStyle("-fx-background-color: #ebeee6");
        bin.setAlignment(Pos.CENTER);

        concreteMenu.getItems().add(redo);
        root.getChildren().add(concreteMenu);
        root.getChildren().add(toolbarWrapper);
        root.getChildren().add(bin);
        root.requestFocus();

        canvas.setPrefSize(SCENE_WIDTH - TOOLBAR_WIDTH, SCENE_HEIGHT - MENU_HEIGHT);
        canvas.setLayoutX(TOOLBAR_WIDTH);
        canvas.setLayoutY(MENU_HEIGHT);
        root.getChildren().add(canvas);

        toolbarWrapper.toFront();
        bin.toFront();
        concreteMenu.toFront();
        try {
            primaryStage.getIcons().add(new Image(new FileInputStream("resources/ico/ico.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        primaryStage.show();
    }

    /**
     * Singleton pattern
     *
     * @returns a new View if it's the first time it's called, the previously created instance otherwise
     */
    public static ViewFX getInstance() {
        if (instance == null) {
            instance = new ViewFX();
        }
        return instance;
    }

    /*****************
     *    GETTERS    *
     *****************/

    public Popup getPopup() {
        return popup;
    }

    public Bin getBin() {
        return bin;
    }

    public FXToolbar getToolbar() {
        return toolbar;
    }

    public FXCanvas getCanvas() {
        return canvas;
    }

    public ScrollPane getToolbarWrapper() {
        return toolbarWrapper;
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

}
