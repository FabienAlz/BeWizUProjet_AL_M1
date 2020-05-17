package view;

import javafx.stage.Stage;
import model.Component;

public interface Mediator {
    void registerComponent(Component component);
    void hideElements(boolean flag);
    void createGUI(Stage primaryStage);
}
