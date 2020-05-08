package model;

import javafx.stage.Stage;

public interface Implementor {
    public void start(Stage stage) throws Exception;
    public void draw(Shape s);
    public void remove();
}
