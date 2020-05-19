package view;

import javafx.stage.Stage;
import model.Component;

public interface Mediator {
    void registerComponent(Component component);

    void createGUI(Stage primaryStage);
}
