package model;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXML;

public class FXImplementor implements Implementor {
        public Parent root;

@FXML
private Pane canvas;

        public void start(Stage primaryStage) throws Exception{
        root = FXMLLoader.load(getClass().getResource("../view/view.fxml"));
        primaryStage.setTitle("BeWizU");
        primaryStage.setScene(new Scene(root, 1080, 650));
        primaryStage.show();
    }

    @Override
    public void draw(Shape s) {
        if (s instanceof Rectangle) {
        javafx.scene.shape.Rectangle r = new javafx.scene.shape.Rectangle();
        r.setX(s.getPosition().getX());
        r.setY(s.getPosition().getY());
        r.setWidth(((Rectangle)s).getWidth());
        r.setHeight(((Rectangle)s).getHeight());
        r.setArcWidth(((Rectangle)s).getBorderRadius()/2);
        r.setArcHeight(((Rectangle)s).getBorderRadius()/2);
        r.setFill(Color.valueOf(s.getColor()));
        //canvas.getChildren().add(r);

        }

        return;
    }

    @Override
    public void remove(Shape s) {
        return;
    }
}
