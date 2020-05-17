package view;

import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.*;

import javax.naming.Context;

public final class View implements Mediator {

    private static View instance;
    public Popup popup;
    private SaveButton save;
    private LoadButton load;
    private UndoButton undo;
    private RedoButton redo;
    public Bin bin;
    private FXMenu menu;
    public FXToolbar toolbar;
    public FXCanvas canvas;
    public ContextMenu contextMenu;
    public FXMenuItem menuItem;
    private double TOOLBAR_WIDTH = 128;
    private double TOOLBAR_HEIGHT = 530;
    private double MENU_HEIGHT = 51;
    private double SCENE_WIDTH = 1080;
    private double SCENE_HEIGHT = 650;

    public Group root;

    @Override
    public void registerComponent(Component component) {
        component.setMediator(this);
        switch (component.getName()) {
            case "SaveButton":
                save = (SaveButton) component;
                break;
            case "Popup":
                popup = (Popup) component;
                break;
            case "LoadButton":
                load = (LoadButton) component;
                break;
            case "UndoButton":
                undo = (UndoButton) component;
                break;
            case "RedoButton":
                redo = (RedoButton) component;
                break;
            case "BinButton":
                bin = (Bin) component;
                break;
            case "FXMenu":
                menu = (FXMenu)component;
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
                menuItem = (FXMenuItem) component;
                break;
        }
    }

    @Override
    public void hideElements(boolean flag) {

    }

    @Override
    public void createGUI(Stage primaryStage) {
        root = new Group();
        primaryStage.setResizable(false);
        primaryStage.setTitle("BeWizU");
        primaryStage.setScene(new Scene(root, SCENE_WIDTH, SCENE_HEIGHT));
        ToolBar concreteMenu = new ToolBar();
        concreteMenu.prefWidthProperty().bind(primaryStage.widthProperty());
        concreteMenu.setPrefHeight(MENU_HEIGHT);

        concreteMenu.getItems().add(save);
        concreteMenu.getItems().add(new Separator());

        concreteMenu.getItems().add(load);
        concreteMenu.getItems().add(new Separator());

        concreteMenu.getItems().add(undo);
        concreteMenu.getItems().add(new Separator());

        toolbar.setPrefSize(TOOLBAR_WIDTH,TOOLBAR_HEIGHT);
        toolbar.setStyle("-fx-background-color: #ebeee6");
        toolbar.setLayoutY(concreteMenu.getPrefHeight());

        /*StackPane binPane = new StackPane();
        binPane.getChildren().add(bin);
        */
        bin.setPrefSize(TOOLBAR_WIDTH,SCENE_HEIGHT - (MENU_HEIGHT + TOOLBAR_HEIGHT));
        bin.setLayoutY(MENU_HEIGHT + TOOLBAR_HEIGHT);
        bin.setAlignment(Pos.CENTER);

        concreteMenu.getItems().add(redo);
        root.getChildren().add(concreteMenu);
        root.getChildren().add(toolbar);
        root.getChildren().add(bin);
        root.requestFocus();

        canvas.setPrefSize(SCENE_WIDTH - TOOLBAR_WIDTH, SCENE_HEIGHT - MENU_HEIGHT);
        canvas.setLayoutX(TOOLBAR_WIDTH);
        canvas.setLayoutY(MENU_HEIGHT);
        root.getChildren().add(canvas);

        primaryStage.show();
    }


    /**
     * Singleton pattern
     * @return
     */
    public static View getInstance() {
        if (instance == null) {
            instance = new View();
        }
        return instance;
    }

}
