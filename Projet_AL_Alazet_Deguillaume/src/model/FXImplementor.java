package model;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXML;

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
            r.setWidth(((Rectangle) s).getWidth());
            r.setHeight(((Rectangle) s).getHeight());
            r.setArcWidth(((Rectangle) s).getBorderRadius() / 2);
            r.setArcHeight(((Rectangle) s).getBorderRadius() / 2);
            r.setFill(Color.valueOf(s.getColor()));

            if(s.getPosition() instanceof ToolbarPosition) {
                leftBar.getChildren().add(r);

                EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent e) {
                        javafx.scene.shape.Rectangle rCopy = new javafx.scene.shape.Rectangle();
                        rCopy.setX(s.getPosition().getX());
                        rCopy.setY(s.getPosition().getY());
                        rCopy.setWidth(((Rectangle) s).getWidth());
                        rCopy.setHeight(((Rectangle) s).getHeight());
                        rCopy.setArcWidth(((Rectangle) s).getBorderRadius() / 2);
                        rCopy.setArcHeight(((Rectangle) s).getBorderRadius() / 2);
                        rCopy.setFill(Color.valueOf(s.getColor()));
                        canvas.getChildren().add(rCopy);
                    }
                };

                r.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

            }
            else {
                canvas.getChildren().add(r);
            }

        }

        if (s instanceof Polygon) {
            //TODO
        }

        return;
    }

    @Override
    public void remove(Shape s) {
        return;
    }

}
