package sample;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.stage.Stage;
import model.*;
import view.View;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    Implementor implementor;


    public void initialize() {
        Canvas canvas = Canvas.getInstance();
        Toolbar toolbar = Toolbar.getInstance();

        ShapeObserver obs = new ConcreteShapeObserver();
        Shape r = new Rectangle(new ToolbarPosition(), 0, new Translation(0,0), "#4472c4", 30, 20, 0, implementor);
        r.addObserver(obs);
        toolbar.addAndNotify(r);
        Shape r2 = new Rectangle(new ToolbarPosition(), 0, new Translation(0,0), "#202020", 40, 20, 0, implementor);
        //Shape r3 = new Rectangle(new CanvasPosition(50,60), 0, new Translation(0,0), "#a823b3", 300, 200, 0, implementor);
        Shape r4 = new Rectangle(new CanvasPosition(100,100), 0, new Translation(0,0), "#a823b3", 100, 50, 0, implementor);


        r2.addObserver(obs);
//        r3.addObserver(obs);
//       r4.addObserver(obs);


        toolbar.addAndNotify(r2);

        Shape p = new Polygon(new ToolbarPosition(), -18, new Translation(0,0), "#4472c4", 5, 100, implementor);
        p.addObserver(obs);

//        canvas.addAndNotify(r3);
        toolbar.addAndNotify(p);

//        canvas.addAndNotify(r4);
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
