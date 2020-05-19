package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import model.Toolbar;

public class Main extends Application {
    Implementor implementor;


    public void initialize() {
        Canvas canvas = Canvas.getInstance();
        Toolbar toolbar = Toolbar.getInstance();

        Shape r = new Rectangle(new ToolbarPosition(), 0, new Position(0,0), new Translation(0,0), "#4472c4", 30, 20, 0, implementor);
        Shape r2 = new Rectangle(new ToolbarPosition(), 0, new Position(0,0), new Translation(0,0), "#202020", 40, 20, 0, implementor);
        Shape r3 = new Rectangle(new CanvasPosition(50,60), 0, new Position(0,0), new Translation(0,0), "#a823b3", 300, 200, 0, implementor);
        Shape r4 = new Rectangle(new CanvasPosition(200,60), 0, new Position(0,0), new Translation(0,0), "#a823b3", 300, 320, 0, implementor);
        Shape p = new Polygon(new CanvasPosition(150,300), -18, new Position(0,0), new Translation(0,0), "#4472c4", 5, 100, implementor);

        ShapeObserver obs = new ConcreteShapeObserver();

//        r.addObserver(obs);
//        r2.addObserver(obs);
        r3.addObserver(obs);
        r4.addObserver(obs);
        p.addObserver(obs);

//        toolbar.addAndNotify(r);
//        toolbar.addAndNotify(r2);
        canvas.addAndNotify(r3);
        canvas.addAndNotify(p);
        canvas.addAndNotify(r4);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        implementor = FXImplementor.getInstance();
        implementor.initializeFX();
        implementor.start(primaryStage);
        initialize();
        Caretaker.getInstance().saveState();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
