package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.fxml.FXML;

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

    private final Color BORDER_COLOR = new Color(68.0 / 255, 114.0 / 255, 196.0 / 255, 1);

    private static Implementor instance;

    private Map<Long, javafx.scene.shape.Shape> shapes = new HashMap<>();;

    public static Implementor getInstance() {
        if (instance == null) {
            instance = new FXImplementor();
        }
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

        // Handler to create group of shapes
        group.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CompoundShape compoundShape = new CompoundShape(FXImplementor.getInstance(), new CanvasPosition(0,0));

                for (Shape s : Canvas.getInstance().createCompound()) {
                    compoundShape.add(s);
                }
                for(Shape s : compoundShape.getShapes()) {
                    canvas.getChildren().remove(shapes.get(s.getId()));
                }
                ShapeObserver obs = new ConcreteShapeObserver();
                compoundShape.addObserver(obs);
                Canvas.getInstance().add(compoundShape);
            }
        });
        javafx.scene.control.MenuItem edit = new MenuItem("Edit");

        // Handler to edit the shapes
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
                        boolean isSelected = s.isSelected();
                        canvas.getChildren().clear();
                        if (!e.isControlDown()) {
                            Canvas.getInstance().resetSelection();
                        }
                        s.setSelected(!isSelected);
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
                        sToolbar.setId();
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
                    sCopy.setId();
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

    private void compoundShapeHandlers(CompoundShape s, Map<Shape,javafx.scene.shape.Shape> compoundShapes) {
        EventHandler<MouseEvent> hoverColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()){
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
                for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
                    newShape.getValue().setFill(Color.valueOf(newShape.getKey().getColor()));
                }
            }
        };

        if (s.getPositionI() instanceof CanvasPosition) {
            EventHandler<MouseEvent> selection = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        boolean isSelected = s.isSelected();
                        if (!e.isControlDown()) {
                            Canvas.getInstance().resetSelection();
                        }
                        canvas.getChildren().clear();
                        s.setSelected(!isSelected);
                        for (Map.Entry<Shape, javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
                            newShape.getKey().setSelected(!isSelected);
                        }
                        Canvas.getInstance().notifyAllShapes();
                    }

                }
            };

            EventHandler<MouseEvent> addToToolbar = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        CompoundShape sCopy = s.clone();
                        sCopy.setId();
                        createToolbarCompoundShape(sCopy);
                        Toolbar.getInstance().add(sCopy);
                        s.setSelected(false);
                        canvas.getChildren().clear();
                        Canvas.getInstance().remove(s);
                        Canvas.getInstance().notifyAllShapes();
                    }
                }
            };


           /* newShape.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {
                    contextMenu.show(newShape, event.getScreenX(), event.getScreenY());
                }
            });*/

            for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()){
                newShape.getValue().addEventFilter(MouseEvent.MOUSE_CLICKED, selection);
                newShape.getValue().addEventFilter(MouseEvent.MOUSE_CLICKED, addToToolbar);
            }
        }
        else if(s.getPositionI() instanceof ToolbarPosition) {
           /* EventHandler<MouseEvent> addToCanvas = new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent e) {
                    Shape sCopy = s.clone();
                    sCopy.setId();
                    sCopy.setPosition(new CanvasPosition(s.getPositionI().getX(), s.getPositionI().getY()));
                    Canvas.getInstance().add(sCopy);
                }
            };
            for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()){
                newShape.getValue().addEventFilter(MouseEvent.MOUSE_CLICKED, addToCanvas);
            }*/
           //TODO
        }

        for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()){
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
            // Put a stroke to the shape if it's selected
            if (s.isSelected()) {
                // Change the color of the stroke to avoid having a blue stroke on a blue shape
                if (r >= (BORDER_COLOR.getRed()*255)-10 && r <= (BORDER_COLOR.getRed()*255)+10 &&
                        g >= (BORDER_COLOR.getGreen()*255)-10 && g <= (BORDER_COLOR.getGreen()*255)+10 &&
                        b >= (BORDER_COLOR.getBlue()*255)-10 && b <= (BORDER_COLOR.getBlue()*255)+10)
                    newShape.setStroke(new Color(0, 0, 0, 1));
                else
                    newShape.setStroke(BORDER_COLOR);
            } else newShape.setStrokeWidth(0);
            canvas.getChildren().add(newShape);

        }
        return newShape;
    }

    private javafx.scene.shape.Rectangle createToolbarCompoundRectangle(Rectangle s) {
        javafx.scene.shape.Rectangle newShape = new javafx.scene.shape.Rectangle();
        newShape.setX(s.getPositionI().getX());
        newShape.setY(s.getPositionI().getY());
        newShape.setArcWidth(s.getBorderRadius() / 2);
        newShape.setArcHeight(s.getBorderRadius() / 2);
        newShape.setFill(Color.valueOf(s.getColor()));
        newShape.setWidth(s.getWidth());
        newShape.setHeight(s.getHeight());

        newShape.setStrokeWidth(0);
        if (s.getWidth() > leftBar.getWidth() - 24) {
            float ratio = (float) (s.getWidth() / (leftBar.getWidth() - 24));
            s.setRatio(ratio);
            newShape.setWidth(s.getWidth() / ratio);
            newShape.setHeight(s.getHeight() / ratio);
        }
        newShape.setX(s.getPositionI().getX());
        newShape.setY(s.getPositionI().getY());
        leftBar.getChildren().add(newShape);

        return newShape;
    }


    private javafx.scene.shape.Polygon createPolygon(Polygon s) {
        double[] vertices = new double[s.getEdges() * 2];
        int index = 0;
        s.computeVertices();
        for (double vertex : s.getVertices()) {
            vertices[index] = vertex;
            index++;
        }
        javafx.scene.shape.Polygon newShape;

        if (s.getPositionI() instanceof ToolbarPosition) {
            s.setPosition(Toolbar.getInstance().getNextPosition());
            // Adjust the length of the shape in the toolbar
            if (s.getWidth() > leftBar.getWidth() - 35) {
                float ratio = (float) (s.getWidth() / (leftBar.getWidth() - 35));
                Polygon copy = s.clone();
                copy.setRatio(ratio);
                copy.setLength(s.getLength()/ratio);
                copy.computeVertices();
                index = 0;
                for(double vertex : copy.getVertices()) {
                    vertices[index] = vertex;
                    index++;
                }
                // Compute the position of the next shape to put in the toolbar

                Toolbar.getInstance().setNextPosition((int) copy.computeRadius() * 2);

            }
            else {
                Toolbar.getInstance().setNextPosition((int) s.computeRadius() * 2);
            }

            newShape = new javafx.scene.shape.Polygon(vertices);


            newShape.setStrokeWidth(0);
            newShape.setRotate(s.getRotation());
            newShape.setFill(Color.valueOf(s.getColor()));

            leftBar.getChildren().add(newShape);
        } else {
            newShape = new javafx.scene.shape.Polygon(vertices);
            Color c = Color.valueOf(s.getColor());
            double r = c.getRed() * 255;
            double g = c.getGreen() * 255;
            double b = c.getBlue() * 255;
            // Put a stroke to the shape if it's selected
            if (s.isSelected()) {
                // Change the color of the stroke to avoid having a blue stroke on a blue shape
                if (r >= (BORDER_COLOR.getRed()*255)-10 && r <= (BORDER_COLOR.getRed()*255)+10 &&
                        g >= (BORDER_COLOR.getGreen()*255)-10 && g <= (BORDER_COLOR.getGreen()*255)+10 &&
                        b >= (BORDER_COLOR.getBlue()*255)-10 && b <= (BORDER_COLOR.getBlue()*255)+10)
                    newShape.setStroke(new Color(0, 0, 0, 1));
                else
                    newShape.setStroke(BORDER_COLOR);
            } else newShape.setStrokeWidth(0);

            newShape.setRotate(s.getRotation());
            newShape.setFill(Color.valueOf(s.getColor()));
            canvas.getChildren().add(newShape);
        }
        return newShape;
    }

    private javafx.scene.shape.Polygon createToolbarCompoundPolygon(Polygon s) {
        double[] vertices = new double[s.getEdges() * 2];
        int index = 0;
        s.computeVertices();
        for (double vertex : s.getVertices()) {
            vertices[index] = vertex;
            index++;
        }
        javafx.scene.shape.Polygon newShape;
        newShape = new javafx.scene.shape.Polygon(vertices);


        newShape.setStrokeWidth(0);
        newShape.setRotate(s.getRotation());
        newShape.setFill(Color.valueOf(s.getColor()));

        leftBar.getChildren().add(newShape);

        return newShape;
    }


    private void createToolbarCompoundShape(CompoundShape s) {
        Map<Shape,javafx.scene.shape.Shape> compoundShapes = new HashMap<>();

        float width = s.getWidth();
        float ratio = 1;
        if(width > leftBar.getWidth()-35) {
            ratio = (float)(width/(leftBar.getWidth()-35));
        }
        System.out.println("AVANT " + s.getPositionI().getX()+" "+s.getPositionI().getY());

        for (Shape shape : s.getShapes()) {
            float posX = (float)(Toolbar.getInstance().getNextPosition().getX() +
                    (shape.getPositionI().getX()-s.getPositionI().getX())/ratio);
            float posY = (float)(Toolbar.getInstance().getNextPosition().getY() +
                    (shape.getPositionI().getY()-s.getPositionI().getY())/ratio);
            System.out.println("PENDANT " + posX +" "+posY);

            ToolbarPosition pos = new ToolbarPosition(posX, posY);
            if (shape instanceof Rectangle) {
                ((Rectangle) shape).setWidth(shape.getWidth()/ratio);
                ((Rectangle) shape).setHeight(shape.getHeight()/ratio);
                shape.setPosition(pos);
                compoundShapes.put(shape, createToolbarCompoundRectangle((Rectangle)shape));
            } else if (shape instanceof Polygon) {
                ((Polygon) shape).setLength(((Polygon) shape).getLength()/ratio);
                shape.setPosition(pos);
                compoundShapes.put(shape, createToolbarCompoundPolygon((Polygon) shape));
            }
        }

        s.setPosition(Toolbar.getInstance().getNextPosition());
        System.out.println("APRES " + s.getPositionI().getX()+" "+s.getPositionI().getY());
        Toolbar.getInstance().setNextPosition((int)(s.getHeight()));
        compoundShapeHandlers(s, compoundShapes);

    }

    private void createCompoundShape(Shape s) {
        Map<Shape,javafx.scene.shape.Shape> compoundShapes = new HashMap<>();
        for(Shape shape : ((CompoundShape) s).getShapes()) {
            if (shape instanceof CompoundShape) {
                createCompoundShape(shape);
            } else if (shape instanceof Rectangle) {
                compoundShapes.put(shape, createRectangle((Rectangle)shape));
            } else if (shape instanceof Polygon) {
                compoundShapes.put(shape, createPolygon((Polygon)shape));
            }
        }
        compoundShapeHandlers((CompoundShape)s, compoundShapes);
    }


    @Override
    public void draw(Shape s) {

        if (s instanceof CompoundShape) {
            createCompoundShape(s);
        }
        else if (s instanceof Rectangle) {
            javafx.scene.shape.Shape newShape = createRectangle((Rectangle) s);
            commonHandlers(s, newShape);
            shapes.put(s.getId(), newShape);

        }
        else if (s instanceof Polygon) {
            javafx.scene.shape.Shape newShape = createPolygon((Polygon) s);
            commonHandlers(s, newShape);
            shapes.put(s.getId(), newShape);

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
