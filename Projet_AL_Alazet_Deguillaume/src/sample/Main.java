package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import model.*;

public class Main extends Application {
    Implementor implementor;


    public void initialize() {

        Canvas canvas = Canvas.getInstance();
        Toolbar toolbar = Toolbar.getInstance();

        Shape r = new Rectangle(new ToolbarPosition(50,25), 0, new ToolbarPosition(0,0), new ToolbarPosition(0,0), "#4472c4", 30, 20, 0, implementor);
        Shape r2 = new Rectangle(new ToolbarPosition(50,50), 0, new ToolbarPosition(0,0), new ToolbarPosition(0,0), "#202020", 40, 20, 0, implementor);
        Shape r3 = new Rectangle(new CanvasPosition(50,75), 0, new CanvasPosition(0,0), new CanvasPosition(0,0), "#a823b3", 30, 20, 0, implementor);

        Shape p = new Polygon(new ToolbarPosition(500,400), 0, new ToolbarPosition(0,0), new ToolbarPosition(0,0), "#4472c4", 5, 20, implementor);

        ShapeObserver obs = new ConcreteShapeObserver();
        r.addObserver(obs);
        r2.addObserver(obs);
        r3.addObserver(obs);

        toolbar.add(r);
        toolbar.add(r2);
        canvas.add(r3);
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
