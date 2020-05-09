package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {
    Implementor implementor;


    public void initialize() {

        Canvas canvas = Canvas.getInstance();
        Toolbar toolbar = Toolbar.getInstance();

        Shape r = new Rectangle(new ToolbarPosition(), 0, new Position(0,0), new Position(0,0), "#4472c4", 30, 20, 0, implementor);
        Shape r2 = new Rectangle(new ToolbarPosition(), 0, new Position(0,0), new Position(0,0), "#202020", 40, 20, 0, implementor);
        Shape r3 = new Rectangle(new CanvasPosition(50,60), 0, new Position(0,0), new Position(0,0), "#a823b3", 300, 200, 0, implementor);

        Shape p = new Polygon(new CanvasPosition(100,100), -18, new Position(0,0), new Position(0,0), "#4472c4", 5, 100, implementor);

        ShapeObserver obs = new ConcreteShapeObserver();
        r.addObserver(obs);
        r2.addObserver(obs);
        r3.addObserver(obs);
        p.addObserver(obs);

        toolbar.add(r);
        toolbar.add(r2);
        toolbar.add(p);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        implementor = FXImplementor.getInstance();
        implementor.start(primaryStage);
        initialize();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
