package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;
import model.Toolbar;

public class Main extends Application {
    Implementor implementor;


    public void initialize() {
        Toolbar toolbar = Toolbar.getInstance();

        ShapeObserver obs = new ConcreteShapeObserver();
        Shape r = new Rectangle(new ToolbarPosition(), 0, new Translation(0,0), "#4472c4", 30, 20, 0, implementor);
        r.addObserver(obs);
        toolbar.addAndNotify(r);
        Shape r2 = new Rectangle(new ToolbarPosition(), 0, new Translation(0,0), "#202020", 40, 20, 0, implementor);


        r2.addObserver(obs);
//        r3.addObserver(obs);
//       r4.addObserver(obs);


        toolbar.addAndNotify(r2);

        Shape p = new Polygon(new ToolbarPosition(), -18, new Translation(0,0), "#4472c4", 5, 100, implementor);
        p.addObserver(obs);

//        canvas.addAndNotify(r3);
        toolbar.addAndNotify(p);

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        implementor = FXImplementor.getInstance();
        ((FXImplementor)implementor).initializeFX();
        ((FXImplementor)implementor).start(primaryStage);
       //initialize();
        Caretaker.getInstance().saveState();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
