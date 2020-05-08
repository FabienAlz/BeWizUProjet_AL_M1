package model;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.net.StandardSocketOptions;
import java.util.ArrayList;
import java.util.List;

public final class FXImplementor implements Implementor {
    private Pane root;

    @FXML
    private Pane canvas;
    @FXML
    private Pane leftBar;

    private static Implementor instance;

    public static Implementor getInstance() {
        if (instance == null)
            instance = new FXImplementor();
        return instance;
    }

    public Pane getRoot() {
        return root;
    }

    public Pane getCanvas() {
        return canvas;
    }

    public Pane getLeftBar() {
        return leftBar;
    }

    public void start(Stage primaryStage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("../view/view.fxml"));
        canvas = (Pane) root.lookup("#canvas");
        leftBar = (Pane) root.lookup("#leftBar");
        primaryStage.setResizable(false);
        primaryStage.setTitle("BeWizU");
        primaryStage.setScene(new Scene(root, 1080, 650));
        primaryStage.show();
    }

    @Override
    public void draw(Shape s) {
        if (s instanceof Rectangle) {
            javafx.scene.shape.Rectangle r = new javafx.scene.shape.Rectangle();
            r.setX(s.getPositionI().getX());
            r.setY(s.getPositionI().getY());
            r.setArcWidth(((Rectangle) s).getBorderRadius() / 2);
            r.setArcHeight(((Rectangle) s).getBorderRadius() / 2);
            r.setFill(Color.valueOf(s.getColor()));
            r.setWidth(((Rectangle) s).getWidth());
            r.setHeight(((Rectangle) s).getHeight());
            if (s.getPositionI() instanceof ToolbarPosition) {
                if(((Rectangle) s).getWidth() > leftBar.getWidth() - 24) {
                    float ratio = (float)(((Rectangle) s).getWidth() / (leftBar.getWidth() - 24));
                    ((Rectangle) s).setRatio(ratio);
                    r.setWidth(((Rectangle) s).getWidth()/ratio);
                    r.setHeight(((Rectangle) s).getHeight()/ratio);
                }
                s.setPosition(Toolbar.getInstance().getNextPosition());
                r.setX(s.getPositionI().getX());
                r.setY(s.getPositionI().getY());
                Toolbar.getInstance().setNextPosition((int) r.getHeight());
                leftBar.getChildren().add(r);

                // delete on click
/*
               EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Toolbar.getInstance().remove(s);
                        remove();
                    }
                };
*/
               // on click copy sur le canvas
                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        javafx.scene.shape.Rectangle rCopy = new javafx.scene.shape.Rectangle();
                        rCopy.setX(s.getPositionI().getX());
                        rCopy.setY(s.getPositionI().getY());
                        rCopy.setWidth(((Rectangle) s).getWidth());
                        rCopy.setHeight(((Rectangle) s).getHeight());
                        rCopy.setArcWidth(((Rectangle) s).getBorderRadius() / 2);
                        rCopy.setArcHeight(((Rectangle) s).getBorderRadius() / 2);
                        rCopy.setFill(Color.valueOf(s.getColor()));
                        canvas.getChildren().add(rCopy);
                        Shape sCopy = ((Rectangle) s).clone();
                        sCopy.setPosition(new CanvasPosition(s.getPositionI().getX(), s.getPositionI().getY()));
                        Canvas.getInstance().add(sCopy);
                    }
                };

                r.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);


            } else {
                canvas.getChildren().add(r);

                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Shape sToolbar = ((Rectangle) s).clone();
                        sToolbar.setPosition(Toolbar.getInstance().getNextPosition());
                        Toolbar.getInstance().add(sToolbar);
                        //ShapeObserver obs = new ConcreteShapeObserver();
                        //sToolbar.addObserver(obs);
                    }
                };

                r.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }

        }

        if (s instanceof Polygon) {
            //TODO
        }

        return;
    }


    @Override
    public void remove() {
        List<Node> nodes  = new ArrayList<>();
        for (Node n : leftBar.getChildren()) {
            if (n instanceof javafx.scene.shape.Rectangle) {
               nodes.add(n);
            }
        }
        for (Node n : nodes) {
            leftBar.getChildren().remove(n);
        }
        Toolbar.getInstance().resetPosition();
        for(Shape s : Toolbar.getInstance().getShapes()) {
            s.setPosition(Toolbar.getInstance().getNextPosition());
            s.notifyObserver();
        }
    }

}
