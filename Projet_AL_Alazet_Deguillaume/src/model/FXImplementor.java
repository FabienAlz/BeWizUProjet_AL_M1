package model;

import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import javax.naming.Context;
import javax.swing.plaf.ToolBarUI;
import javax.tools.Tool;
import java.awt.*;
import java.net.StandardSocketOptions;
import java.util.*;
import java.util.List;

public final class FXImplementor implements Implementor {
    private Pane root;

    @FXML
    private Pane canvas;
    @FXML
    private Pane leftBar;
    @FXML
    private ContextMenu contextMenu;

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


        // Create ContextMenu
        contextMenu = new ContextMenu();
        contextMenu.setId("contextMenu");
        javafx.scene.control.MenuItem group = new javafx.scene.control.MenuItem("Group");
        group.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                CompoundShape compoundShape = new CompoundShape(FXImplementor.getInstance());
                for (Shape s : Canvas.getInstance().getShapes()) {
                    if(s.isSelected()) {
                        compoundShape.add(s);
                    }
                }
                for(Shape s : compoundShape.getShapes()) {
                    Canvas.getInstance().remove(s);
            }
                ShapeObserver obs = new ConcreteShapeObserver();
                compoundShape.addObserver(obs);
                Canvas.getInstance().add(compoundShape);
            }
        });
        javafx.scene.control.MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for(Shape s : Toolbar.getInstance().getShapes()) {
                    System.out.println(s);
                }
                for(Node n : leftBar.getChildren()) {
                    System.out.println(n);
                }
                System.out.println("EDIT BUTTON");
            }
        });

        // Add MenuItem to ContextMenu
        contextMenu.getItems().addAll(group, edit);


        primaryStage.setResizable(false);
        primaryStage.setTitle("BeWizU");
        primaryStage.setScene(new Scene(root, 1080, 650));
        primaryStage.show();
    }

    private void commonHandlers(Shape s, javafx.scene.shape.Shape newShape) {
        EventHandler<MouseEvent> hoverColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Color c = (Color) newShape.getFill();
                double r = c.getRed() * 255;
                double g = c.getGreen() * 255;
                double b = c.getBlue() * 255;
                double rt, gt, bt;
                rt = r + (0.25 * (255 - r));
                gt = g + (0.25 * (255 - g));
                bt = b + (0.25 * (255 - b));
                rt /= 255;
                gt /= 255;
                bt /= 255;

                newShape.setFill(new Color(rt, gt, bt, 1));

            }
        };

        newShape.addEventFilter(MouseEvent.MOUSE_ENTERED, hoverColor);

        EventHandler<MouseEvent> setBackColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                newShape.setFill(Color.valueOf(s.getColor()));
            }
        };

        newShape.addEventFilter(MouseEvent.MOUSE_EXITED, setBackColor);

        if (s.getPositionI() instanceof CanvasPosition) {
            EventHandler<MouseEvent> selection = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        canvas.getChildren().clear();
                        if (!e.isControlDown()) {
                            Canvas.getInstance().resetSelection();
                        }
                        s.setSelected(true);
                        Canvas.getInstance().notifyAllShapes();
                    }
                }
            };
            newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, selection);

            EventHandler<MouseEvent> addToToolbar = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        Shape sToolbar = s.clone();
                        sToolbar.setSelected(false);
                        sToolbar.setPosition(Toolbar.getInstance().getNextPosition());
                        Toolbar.getInstance().add(sToolbar);
                    }
                }
            };

            newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, addToToolbar);

            newShape.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {
                    contextMenu.show(newShape, event.getScreenX(), event.getScreenY());
                }
            });
        }
        else if(s.getPositionI() instanceof ToolbarPosition) {
            EventHandler<MouseEvent> addToCanvas = new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent e) {
                    Shape sCopy = s.clone();
                    sCopy.setPosition(new CanvasPosition(s.getPositionI().getX(), s.getPositionI().getY()));
                    Canvas.getInstance().add(sCopy);
                }
            };

            newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, addToCanvas);


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
        }
    }

    private void compoundShapeHandlers(CompoundShape s, Map<Shape,javafx.scene.shape.Shape> shapes) {
        EventHandler<MouseEvent> hoverColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : shapes.entrySet()){
                    Color c = (Color) newShape.getValue().getFill();
                    double r = c.getRed() * 255;
                    double g = c.getGreen() * 255;
                    double b = c.getBlue() * 255;
                    double rt, gt, bt;
                    rt = r + (0.25 * (255 - r));
                    gt = g + (0.25 * (255 - g));
                    bt = b + (0.25 * (255 - b));
                    rt /= 255;
                    gt /= 255;
                    bt /= 255;

                    newShape.getValue().setFill(new Color(rt, gt, bt, 1));
                }
            }
        };

        EventHandler<MouseEvent> setBackColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : shapes.entrySet()) {
                    newShape.getValue().setFill(Color.valueOf(newShape.getKey().getColor()));
                }
            }
        };

        if (s.getPositionI() instanceof CanvasPosition) {
            EventHandler<MouseEvent> selection = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        canvas.getChildren().clear();
                        if (!e.isControlDown()) {
                            Canvas.getInstance().resetSelection();
                        }
                        for (Map.Entry<Shape, javafx.scene.shape.Shape> newShape : shapes.entrySet()) {
                            newShape.getKey().setSelected(true);
                        }
                        Canvas.getInstance().notifyAllShapes();
                    }

                }
            };

            /*EventHandler<MouseEvent> addToToolbar = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        Shape sToolbar = s.clone();
                        sToolbar.setSelected(false);
                        sToolbar.setPosition(Toolbar.getInstance().getNextPosition());
                        Toolbar.getInstance().add(sToolbar);
                    }
                }
            };

            newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, addToToolbar);*/

           /* newShape.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {
                    contextMenu.show(newShape, event.getScreenX(), event.getScreenY());
                }
            });*/

            for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : shapes.entrySet()){
                newShape.getValue().addEventFilter(MouseEvent.MOUSE_CLICKED, selection);

            }
        }


        for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : shapes.entrySet()){
            newShape.getValue().addEventFilter(MouseEvent.MOUSE_ENTERED, hoverColor);
            newShape.getValue().addEventFilter(MouseEvent.MOUSE_EXITED, setBackColor);
        }

    }

    private javafx.scene.shape.Rectangle createRectangle(Rectangle s) {
        javafx.scene.shape.Rectangle newShape = new javafx.scene.shape.Rectangle();
        newShape.setX(s.getPositionI().getX());
        newShape.setY(s.getPositionI().getY());
        newShape.setArcWidth(s.getBorderRadius() / 2);
        newShape.setArcHeight(s.getBorderRadius() / 2);
        newShape.setFill(Color.valueOf(s.getColor()));
        newShape.setWidth(s.getWidth());
        newShape.setHeight(s.getHeight());
        if (s.getPositionI() instanceof ToolbarPosition) {
            newShape.setStrokeWidth(0);
            if (s.getWidth() > leftBar.getWidth() - 24) {
                float ratio = (float) (s.getWidth() / (leftBar.getWidth() - 24));
                s.setRatio(ratio);
                newShape.setWidth(s.getWidth() / ratio);
                newShape.setHeight(s.getHeight() / ratio);
            }
            s.setPosition(Toolbar.getInstance().getNextPosition());
            newShape.setX(s.getPositionI().getX());
            newShape.setY(s.getPositionI().getY());
            Toolbar.getInstance().setNextPosition((int)newShape.getHeight());
            leftBar.getChildren().add(newShape);

        } else {
            Color c = Color.valueOf(s.getColor());
            double r = c.getRed() * 255;
            double g = c.getGreen() * 255;
            double b = c.getBlue() * 255;
            if (s.isSelected()) {
                if (r >= 65.0 && r <= 70.0 && g >= 110.0 && g <= 120.0 && b >= 190.0 && b <= 200.0)
                    newShape.setStroke(new Color(0, 0, 0, 1));
                else
                    newShape.setStroke(new Color(68.0 / 255, 114.0 / 255, 196.0 / 255, 1));
            } else newShape.setStrokeWidth(0);
            canvas.getChildren().add(newShape);
//            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    if (e.getButton() == MouseButton.PRIMARY) {
//                        Shape sToolbar = s.clone();
//                        sToolbar.setPosition(Toolbar.getInstance().getNextPosition());
//                        Toolbar.getInstance().add(sToolbar);
//                    }
//                }
//            };
//
//            newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
//
//
//            newShape.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
//
//                @Override
//                public void handle(ContextMenuEvent event) {
//                    contextMenu.show(newShape, event.getScreenX(), event.getScreenY());
//                }
//            });
        }
        return newShape;
    }

    private javafx.scene.shape.Polygon createPolygon(Polygon s) {
        javafx.scene.shape.Polygon newShape = new javafx.scene.shape.Polygon(s.getPoints());
        newShape.setRotate(s.getRotation());
        newShape.setFill(Color.valueOf(s.getColor()));
        if (s.getPositionI() instanceof ToolbarPosition) {
            s.setPosition(Toolbar.getInstance().getNextPosition());
            Toolbar.getInstance().setNextPosition((int) s.computeRadius() * 2);
        }

        if (s.getPositionI() instanceof ToolbarPosition) {
            newShape.setStrokeWidth(0);
            leftBar.getChildren().add(newShape);
            // on click copy sur le canvas
//            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent e) {
//                    Shape sCopy = s.clone();
//                    sCopy.setPosition(new CanvasPosition(s.getPositionI().getX(), s.getPositionI().getY()));
//                    Canvas.getInstance().add(sCopy);
//                }
//            };
//
//            newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        } else {

            Color c = Color.valueOf(s.getColor());
            double r = c.getRed() * 255;
            double g = c.getGreen() * 255;
            double b = c.getBlue() * 255;
            if (s.isSelected()) {
                if (r >= 65.0 && r <= 70.0 && g >= 110.0 && g <= 120.0 && b >= 190.0 && b <= 200.0)
                    newShape.setStroke(new Color(0, 0, 0, 1));
                else
                    newShape.setStroke(new Color(68.0 / 255, 114.0 / 255, 196.0 / 255, 1));
            } else newShape.setStrokeWidth(0);
            canvas.getChildren().add(newShape);

        }
        
        return newShape;
    }

    private void createCompoundShape(Shape s) {
        Map<Shape,javafx.scene.shape.Shape> shapes = new HashMap<>();
        for(Shape shape : ((CompoundShape) s).getShapes()) {
            if (shape instanceof CompoundShape) {
                createCompoundShape(shape);
            } else if (shape instanceof Rectangle) {
                shapes.put(shape, createRectangle((Rectangle)shape));
            } else if (shape instanceof Polygon) {

                shapes.put(shape, createPolygon((Polygon)shape));
            }
        }
        compoundShapeHandlers((CompoundShape)s, shapes);
    }


    @Override
    public void draw(Shape s) {
        if (s instanceof CompoundShape) {
            createCompoundShape(s);
        }
        else if (s instanceof Rectangle) {
            javafx.scene.shape.Shape newShape = createRectangle((Rectangle) s);
            commonHandlers(s, newShape);
        }
        else if (s instanceof Polygon) {
            javafx.scene.shape.Shape newShape = createPolygon((Polygon) s);
            commonHandlers(s, newShape);
        }
        return;
    }

    @Override
    public void remove() {
        List<Node> nodes = new ArrayList<>();
        for (Node n : leftBar.getChildren()) {
            if (n instanceof javafx.scene.shape.Rectangle) {
                nodes.add(n);
            }
        }
        for (Node n : nodes) {
            leftBar.getChildren().remove(n);
        }
        Toolbar.getInstance().resetPosition();
        for (Shape s : Toolbar.getInstance().getShapes()) {
            s.setPosition(Toolbar.getInstance().getNextPosition());
            s.notifyObserver();
        }
    }
}
