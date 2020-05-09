package model;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import javax.tools.Tool;
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
        javafx.scene.shape.Shape newShape;
        if (s instanceof Rectangle) {
             newShape = new javafx.scene.shape.Rectangle();
            ((javafx.scene.shape.Rectangle)newShape).setX(s.getPositionI().getX());
            ((javafx.scene.shape.Rectangle)newShape).setY(s.getPositionI().getY());
            ((javafx.scene.shape.Rectangle)newShape).setArcWidth(((Rectangle) s).getBorderRadius()/2);
            ((javafx.scene.shape.Rectangle)newShape).setArcHeight(((Rectangle) s).getBorderRadius()/2);
            newShape.setFill(Color.valueOf(s.getColor()));
            ((javafx.scene.shape.Rectangle)newShape).setWidth(((Rectangle) s).getWidth());
            ((javafx.scene.shape.Rectangle)newShape).setHeight(((Rectangle) s).getHeight());
            if (s.getPositionI() instanceof ToolbarPosition) {
                if(((Rectangle) s).getWidth() > leftBar.getWidth() - 24) {
                    float ratio = (float)(((Rectangle) s).getWidth() / (leftBar.getWidth() - 24));
                    ((Rectangle) s).setRatio(ratio);
                    ((javafx.scene.shape.Rectangle)newShape).setWidth(((Rectangle) s).getWidth()/ratio);
                    ((javafx.scene.shape.Rectangle)newShape).setHeight(((Rectangle) s).getHeight()/ratio);
                }
                s.setPosition(Toolbar.getInstance().getNextPosition());
                ((javafx.scene.shape.Rectangle)newShape).setX(s.getPositionI().getX());
                ((javafx.scene.shape.Rectangle)newShape).setY(s.getPositionI().getY());
                Toolbar.getInstance().setNextPosition((int) ((javafx.scene.shape.Rectangle)newShape).getHeight());
                leftBar.getChildren().add(newShape);

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
                        Shape sCopy = ((Rectangle) s).clone();
                        sCopy.setPosition(new CanvasPosition(s.getPositionI().getX(), s.getPositionI().getY()));
                        Canvas.getInstance().add(sCopy);

                    }
                };

                newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);


            } else {
                canvas.getChildren().add(newShape);
                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Shape sToolbar = ((Rectangle) s).clone();
                        sToolbar.setPosition(Toolbar.getInstance().getNextPosition());
                        Toolbar.getInstance().add(sToolbar);
                    }
                };

                newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }

        }

        if (s instanceof Polygon) {
            if (s.getPositionI() instanceof ToolbarPosition) {
                s.setPosition(Toolbar.getInstance().getNextPosition());
                Toolbar.getInstance().setNextPosition((int) ((Polygon) s).computeRadius()*2);
            }
            newShape = new javafx.scene.shape.Polygon(((Polygon) s).getPoints());
            newShape.setRotate(s.getRotation());
            newShape.setFill(Color.valueOf(s.getColor()));
            if (s.getPositionI() instanceof ToolbarPosition) {
                leftBar.getChildren().add(newShape);

                // on click copy sur le canvas
                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Shape sCopy = ((Polygon) s).clone();
                        sCopy.setPosition(new CanvasPosition(s.getPositionI().getX(), s.getPositionI().getY()));
                        Canvas.getInstance().add(sCopy);
                    }
                };

                newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }
            else {
                canvas.getChildren().add(newShape);
                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        Shape sToolbar = ((Polygon) s).clone();
                        sToolbar.setPosition(Toolbar.getInstance().getNextPosition());
                        Toolbar.getInstance().add(sToolbar);
                    }
                };

                newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
            }

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
