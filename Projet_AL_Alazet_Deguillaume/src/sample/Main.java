package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {
    Implementor implementor;


    public void initialize() {

        Canvas canvas = Canvas.getInstance();
        Toolbar toolbar = Toolbar.getInstance();

        Shape r = new Rectangle(new Position(0,0), 0, new Position(0,0), new Position(0,0), "#4472c4", 30, 20, 0, implementor);
        Shape p = new Polygon(new Position(0,0), 0, new Position(0,0), new Position(0,0), "#4472c4", 5, 20, implementor);

        ShapeObserver obs = new ConcreteShapeObserver();
        r.addObserver(obs);

        toolbar.add(r);
        toolbar.add(p);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        implementor = new FXImplementor();
        initialize();
        implementor.start(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
