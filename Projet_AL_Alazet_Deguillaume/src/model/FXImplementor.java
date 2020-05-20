package model;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import model.mediatorFX.*;
import model.mediatorFX.ContextMenu;
import org.w3c.dom.ls.LSOutput;
import utils.FXContextMenuHandlers;
import utils.FXMouseHandlers;
import view.View;

import java.io.*;
import java.util.*;
import java.util.List;

public final class FXImplementor implements Implementor, Serializable {
    private static final long serialVersionUID = 3734212650399506405L;

    private transient Shape lastSelected;
    private transient javafx.scene.shape.Shape lastFXSelected;
    private transient Pane canvas;
    private transient Pane toolBar;
    private transient Pane bin;
    private transient  ScrollPane toolBarWrapper;
    private transient Color BORDER_COLOR;
    private transient static FXImplementor instance;
    private transient static Stage stage;
    private transient Popup popup;
    private transient Map<Long, javafx.scene.shape.Shape> SHAPES;

    /**
     * Singleton constructor
     */
    private FXImplementor() {
    }

    /**
     * Singleton pattern
     */
    public static FXImplementor getInstance() {
        if (instance == null) {
            instance = new FXImplementor();
        }
        return instance;
    }

    /*****************
     *    GETTERS    *
     *****************/
    public Map<Long, javafx.scene.shape.Shape> getSHAPES() {
        return SHAPES;
    }

    public Pane getCanvas() {
        return canvas;
    }

    public Pane getToolbar() {
        return toolBar;
    }

    public Pane getBin() {
        return bin;
    }

    public javafx.scene.control.ContextMenu getContextMenu() {
        return View.getInstance().getContextMenu();
    }

    public Shape getLastSelected() {
        return lastSelected;
    }

    public javafx.scene.shape.Shape getLastFXSelected() {
        return lastFXSelected;
    }

    public static Stage getStage() {
        return stage;
    }

    public Popup getPopup() {
        return popup;
    }

    /**
     * Initializes the javafx application
     */
    public void initializeFX() {
        View mediator = View.getInstance();

        mediator.registerComponent(new SaveButton("", "ressources/ico/save.png"));
        mediator.registerComponent(new LoadButton("", "ressources/ico/load.png"));
        mediator.registerComponent(new UndoButton("", "ressources/ico/undo.png"));
        mediator.registerComponent(new RedoButton("", "ressources/ico/redo.png"));
        mediator.registerComponent(new Bin("ressources/ico/bin.png"));
        mediator.registerComponent(new FXToolbar());
        mediator.registerComponent(new FXCanvas());
        mediator.registerComponent(new model.mediatorFX.Popup());
        ContextMenu menu = new ContextMenu("contextMenu");
        menu.addItem(new MenuItemGroup("Group"));
        menu.addItem(new MenuItemUngroup("Ungroup"));
        menu.addItem(new MenuItemEdit("Edit"));
        mediator.registerComponent(menu);
    }


    /**
     * Initializes the FXImplementor
     */
    public void initializeFXImplementor(Stage primaryStage) {
        SHAPES = new HashMap<>();
        BORDER_COLOR = new Color(68.0 / 255, 114.0 / 255, 196.0 / 255, 1);
        toolBar = View.getInstance().getToolbar();
        toolBarWrapper = View.getInstance().getToolbarWrapper();
        canvas = View.getInstance().getCanvas();
        bin = View.getInstance().getBin();
        popup = View.getInstance().getPopup();
        stage = primaryStage;
    }


    /**
     * Starts the javafx application
     *
     * @param primaryStage the stage to start
     * @throws Exception
     */

    public void start(Stage primaryStage) throws Exception {
        View.getInstance().createGUI(primaryStage);
        initializeFXImplementor(primaryStage);

        File load = new File("ressources/saves/autosave.ser");
        List<Shape> loadShapes = null;
        if (load.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(load);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                loadShapes = (List<Shape>) in.readObject();
                ShapeObserver obs = new ConcreteShapeObserver();

                for (Shape s : loadShapes) {
                    try {
                        s.setId();
                        s.setImplementor(this);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    if (s instanceof CompoundShape) {
                        for (Shape subShape : ((CompoundShape) s).getShapes()) {
                            subShape.setId();
                            subShape.setImplementor(this);
                            subShape.addObserver(obs);
                        }
                    }
                    s.addObserver(obs);
                    if (s.getPositionI() instanceof ToolbarPosition) {
                        Toolbar.getInstance().addAndNotify(s);
                    }
                }
                in.close();
                fileIn.close();
            } catch (IOException i) {
                i.printStackTrace();
                return;
            } catch (ClassNotFoundException c) {
                c.printStackTrace();
                return;
            }
            Caretaker.getInstance().saveState();
            View.getInstance().getToolbar().updateDisplay();
        }

        primaryStage.setOnCloseRequest(event -> {
            File file = new File("ressources/saves/autosave.ser");
            if (file != null) {
                try {
                    FileOutputStream fileOut =
                            new FileOutputStream(file);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(Toolbar.getInstance().getShapes());
                    out.close();
                    fileOut.close();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                } catch (IOException i) {
                    i.printStackTrace();
                } catch (NullPointerException np) {
                    np.printStackTrace();
                }
            }
        });
    }

    /**
     * Binds the handlers to single shapes
     */
    private void commonHandlers(Shape s, javafx.scene.shape.Shape newShape) {
        FXMouseHandlers myHandler = new FXMouseHandlers(s, newShape);
        FXContextMenuHandlers myContextMenuHandler = new FXContextMenuHandlers(s, newShape);
        EventHandler<MouseEvent> hoverColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                myHandler.hoverColor(e);
            }
        };
        newShape.addEventFilter(MouseEvent.MOUSE_ENTERED, hoverColor);

        EventHandler<MouseEvent> setBackColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                myHandler.setBackColor(e);
            }
        };
        newShape.addEventFilter(MouseEvent.MOUSE_EXITED, setBackColor);

        if (s.getPositionI() instanceof CanvasPosition) {
            EventHandler<MouseEvent> selection = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    myHandler.selection(e);
                }
            };
            newShape.addEventFilter(MouseEvent.MOUSE_CLICKED, selection);

            newShape.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent e) {
                    myContextMenuHandler.manageContextMenu(e);
                }
            });
        }
    }

    /**
     * Binds the handlers to the compound shapes
     */
    private void compoundShapeHandlers(CompoundShape s, Map<Shape, javafx.scene.shape.Shape> compoundShapes) {
        EventHandler<MouseEvent> hoverColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                for (Map.Entry<Shape, javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
                    FXMouseHandlers myHandler = new FXMouseHandlers(newShape.getKey(), newShape.getValue());
                    myHandler.hoverColor(e);
                }
            }
        };

        EventHandler<MouseEvent> setBackColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                for (Map.Entry<Shape, javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
                    FXMouseHandlers myHandler = new FXMouseHandlers(newShape.getKey(), newShape.getValue());
                    myHandler.setBackColor(e);
                }
            }
        };

        if (s.getPositionI() instanceof CanvasPosition) {
            EventHandler<MouseEvent> selection = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    FXMouseHandlers myHandler = new FXMouseHandlers(s, null);
                    myHandler.selection(e);

                }
            };

            for (Map.Entry<Shape, javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
                newShape.getValue().addEventFilter(MouseEvent.MOUSE_CLICKED, selection);
                newShape.getValue().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                    @Override
                    public void handle(ContextMenuEvent e) {
                        FXContextMenuHandlers myContextMenuHandler = new FXContextMenuHandlers(s, newShape.getValue());
                        myContextMenuHandler.manageContextMenu(e);
                    }
                });
            }
        }

        for (Map.Entry<Shape, javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
            newShape.getValue().addEventFilter(MouseEvent.MOUSE_ENTERED, hoverColor);
            newShape.getValue().addEventFilter(MouseEvent.MOUSE_EXITED, setBackColor);
        }
    }

    /**
     * Binds the drop handlers to the single shapes
     */
    private void dragHandlers(Shape shape, javafx.scene.shape.Shape newShape) {
        FXMouseHandlers myHandler = new FXMouseHandlers(shape, newShape);
        newShape.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                myHandler.onDragDetected(event);
            }
        });

        newShape.setOnDragDone((t) -> {
        });
    }

    /**
     * Binds the drop handlers to the compound shapes
     */
    private void dragHandlersCompound(CompoundShape s, Map<Shape, javafx.scene.shape.Shape> compoundShapes) {
        EventHandler setOnDragDetected = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Map.Entry<Shape, javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
                    FXMouseHandlers myHandler = new FXMouseHandlers(s, newShape.getValue());
                    myHandler.onDragDetected(event);
                }
            }
        };
        for (Map.Entry<Shape, javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
            newShape.getValue().setOnDragDetected(setOnDragDetected);
        }

    }

    /**
     * Computes a javafx.scene.shape.Rectangle from a shape
     *
     * @param s the Shape to create in javafx.scene.shape.Rectangle
     * @return the javafx.scene.shape.Rectangle of the given shape
     */
    private javafx.scene.shape.Rectangle createRectangle(Rectangle s) {
        javafx.scene.shape.Rectangle newShape = new javafx.scene.shape.Rectangle();
        newShape.setX(s.getPositionI().getX());
        newShape.setY(s.getPositionI().getY());
        newShape.setArcWidth(s.getBorderRadius() / 2);
        newShape.setArcHeight(s.getBorderRadius() / 2);
        newShape.setFill(Color.valueOf(s.getColor()));
        newShape.setWidth(s.getWidth());
        newShape.setHeight(s.getHeight());
        newShape.setRotate(s.getRotation());

        if (s.getPositionI() instanceof ToolbarPosition) {
            newShape.setStrokeWidth(0);
            float ratio = 1;
            // Computes the Ratio and the size of the shape in the toolbar
            if (s.getAppearingWidth() > toolBarWrapper.getPrefWidth() - 36) {
                ratio = (float) (s.getAppearingWidth() / (toolBarWrapper.getPrefWidth() - 36));
                newShape.setWidth(s.getWidth() / ratio);
                newShape.setHeight(s.getHeight() / ratio);
            }
            double radRotation = s.getRotation()%180 * Math.PI / 180;
            Position rotationCenter = new Position(s.getPositionI().getX() + s.getWidth()/(2 * ratio),
                    s.getPositionI().getY() + s.getHeight()/(2 * ratio));
            double posYtopLeft = (Math.sin(radRotation) * (s.getPositionI().getX()-rotationCenter.getX()) +
                    Math.cos(radRotation) * (s.getPositionI().getY()-rotationCenter.getY()) +
                    rotationCenter.getY());

            double posXbottomLeft = (Math.cos(radRotation) * (s.getPositionI().getX()-rotationCenter.getX()) -
                    Math.sin(radRotation) * (s.getPositionI().getY()+s.getHeight()/ratio-rotationCenter.getY()) +
                    rotationCenter.getX());

            newShape.setX(s.getPositionI().getX() + (s.getPositionI().getX() - posXbottomLeft));
            newShape.setY(s.getPositionI().getY() + (s.getPositionI().getY() - posYtopLeft));
            Toolbar.getInstance().setNextPosition((int) (s.getAppearingHeight()/ratio));
            toolBar.getChildren().add(newShape);

        } else {
            Color c = Color.valueOf(s.getColor());
            double r = c.getRed() * 255;
            double g = c.getGreen() * 255;
            double b = c.getBlue() * 255;
            // Puts a stroke to the shape if it's selected
            if (s.isSelected()) {
                // Changes the color of the stroke to avoid having a blue stroke on a blue shape
                if (r >= (BORDER_COLOR.getRed() * 255) - 10 && r <= (BORDER_COLOR.getRed() * 255) + 10 &&
                        g >= (BORDER_COLOR.getGreen() * 255) - 10 && g <= (BORDER_COLOR.getGreen() * 255) + 10 &&
                        b >= (BORDER_COLOR.getBlue() * 255) - 10 && b <= (BORDER_COLOR.getBlue() * 255) + 10)
                    newShape.setStroke(new Color(0, 0, 0, 1));
                else
                    newShape.setStroke(BORDER_COLOR);
            } else newShape.setStrokeWidth(0);
            canvas.getChildren().add(newShape);

        }
        return newShape;
    }

    /**
     * Computes a javafx.scene.shape.Rectangle from a given Rectangle in a CompoundShape
     *
     * @param s Rectangle from a CompoundShape
     * @return a javafx.scene.shape.Rectangle in the Toolbar
     */
    private javafx.scene.shape.Rectangle createToolbarCompoundRectangle(Rectangle s) {
        javafx.scene.shape.Rectangle newShape = new javafx.scene.shape.Rectangle();
        newShape.setArcWidth(s.getBorderRadius() / 2);
        newShape.setArcHeight(s.getBorderRadius() / 2);
        newShape.setFill(Color.valueOf(s.getColor()));
        newShape.setWidth(s.getWidth());
        newShape.setHeight(s.getHeight());
        newShape.setRotate(s.getRotation());
        newShape.setStrokeWidth(0);

        newShape.setX(s.getPositionI().getX() + (s.getPositionI().getX() - s.getTopLeft().getX()));

        newShape.setY(s.getPositionI().getY() + (s.getPositionI().getY() - s.getTopLeft().getY()));
        toolBar.getChildren().add(newShape);
        return newShape;
    }

    /**
     * Computes a javafx.scene.shape.Polygon from a shape
     *
     * @param s the Shape to create in javafx.scene.shape.Polygon
     * @return the javafx.scene.shape.Polygon of the given shape
     */
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
            if (s.getWidth() > toolBar.getPrefWidth() - 35) {
                float ratio = (float) (s.getWidth() / (toolBar.getPrefWidth() - 35));
                Polygon copy = s.clone();
                copy.setLength(s.getLength() / ratio);
                copy.computeVertices();
                index = 0;
                for (double vertex : copy.getVertices()) {
                    vertices[index] = vertex;
                    index++;
                }
                // Compute the position of the next shape to put in the toolbar

                Toolbar.getInstance().setNextPosition((int) copy.computeRadius() * 2);

            } else {
                Toolbar.getInstance().setNextPosition((int) s.computeRadius() * 2);
            }

            newShape = new javafx.scene.shape.Polygon(vertices);

            newShape.setRotate(s.getRotation());
            newShape.setStrokeWidth(0);
            newShape.setFill(Color.valueOf(s.getColor()));

            toolBar.getChildren().add(newShape);
        } else {
            newShape = new javafx.scene.shape.Polygon(vertices);
            Color c = Color.valueOf(s.getColor());
            double r = c.getRed() * 255;
            double g = c.getGreen() * 255;
            double b = c.getBlue() * 255;
            // Put a stroke to the shape if it's selected
            if (s.isSelected()) {
                // Change the color of the stroke to avoid having a blue stroke on a blue shape
                if (r >= (BORDER_COLOR.getRed() * 255) - 10 && r <= (BORDER_COLOR.getRed() * 255) + 10 &&
                        g >= (BORDER_COLOR.getGreen() * 255) - 10 && g <= (BORDER_COLOR.getGreen() * 255) + 10 &&
                        b >= (BORDER_COLOR.getBlue() * 255) - 10 && b <= (BORDER_COLOR.getBlue() * 255) + 10)
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

    /**
     * Computes a javafx.scene.shape.Polygon from a given Rectangle in a CompoundShape
     *
     * @param s Rectangle from a CompoundShape
     * @return a javafx.scene.shape.Polygon in the Toolbar
     */
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

        toolBar.getChildren().add(newShape);
        return newShape;
    }

    /**
     * Puts a CompoundShape in the Toolbar and resize it if necessary
     *
     * @param s CompoundShape to put in the Toolbar
     */
    public void createToolbarCompoundShape(CompoundShape s) {
        Map<Shape, javafx.scene.shape.Shape> compoundShapes = new HashMap<>();
        float width = s.getWidth();
        float ratio = 1;
        if (width > toolBarWrapper.getWidth() - 35) {
            ratio = (float) (width / (toolBarWrapper.getWidth() - 35));

        }

        for (Shape shape : s.getShapes()) {
            float posX = (float) (Toolbar.getInstance().getNextPosition().getX() +
                    (shape.getTopLeft().getX() - s.getTopLeft().getX()) / ratio);
            float posY = (float) (Toolbar.getInstance().getNextPosition().getY() +
                    (shape.getTopLeft().getY() - s.getTopLeft().getY()) / ratio);
            ToolbarPosition pos = new ToolbarPosition(posX, posY);
            if (shape instanceof Rectangle) {
                Rectangle copy = (Rectangle) shape.clone();
                copy.setId();
                copy.setWidth(shape.getWidth() / ratio);
                copy.setHeight(shape.getHeight() / ratio);
                copy.setPosition(pos);
                javafx.scene.shape.Rectangle rectangle = createToolbarCompoundRectangle(copy);
                SHAPES.put(copy.getId(), rectangle);
                compoundShapes.put(copy, rectangle);
            } else if (shape instanceof Polygon) {
                Polygon copy = (Polygon) shape.clone();
                copy.setId();
                copy.setLength(copy.getLength() / ratio);
                copy.setPosition(pos);
                javafx.scene.shape.Polygon polygon = createToolbarCompoundPolygon(copy);
                SHAPES.put(copy.getId(), polygon);
                compoundShapes.put(copy, polygon);
            }
        }

        s.setPosition(new ToolbarPosition());
        Toolbar.getInstance().setNextPosition((int) (s.getHeight() / ratio));
        compoundShapeHandlers(s, compoundShapes);
        dragHandlersCompound(s, compoundShapes);

    }

    /**
     * Creates a javafx CompoundShape from one or many Shapes
     *
     * @param s the CompoundShape to copy in javafx
     */
    private void createCompoundShape(CompoundShape s) {
        Map<Shape, javafx.scene.shape.Shape> compoundShapes = new HashMap<>();
        for (Shape shape : s.getShapes()) {
            if (shape instanceof Rectangle) {
                compoundShapes.put(shape, createRectangle((Rectangle) shape));
            } else if (shape instanceof Polygon) {
                compoundShapes.put(shape, createPolygon((Polygon) shape));
            }
        }
        for (Map.Entry<Shape, javafx.scene.shape.Shape> shape : compoundShapes.entrySet()) {
            SHAPES.put(shape.getKey().getId(), shape.getValue());
        }
        compoundShapeHandlers(s, compoundShapes);
        dragHandlersCompound(s, compoundShapes);
    }

    /**
     * Creates a javafx Shape of a given Shape
     *
     * @param s a Shape to draw in javafx
     */
    @Override
    public void draw(Shape s) {
        if (s instanceof CompoundShape) {
            if (s.getPositionI() instanceof ToolbarPosition) {
                createToolbarCompoundShape(((CompoundShape) s));
            } else
                createCompoundShape((CompoundShape) s);
        } else if (s instanceof Rectangle) {
            javafx.scene.shape.Shape newShape = createRectangle((Rectangle) s);
            commonHandlers(s, newShape);
            dragHandlers(s, newShape);
            SHAPES.put(s.getId(), newShape);
        } else if (s instanceof Polygon) {
            javafx.scene.shape.Shape newShape = createPolygon((Polygon) s);
            commonHandlers(s, newShape);
            dragHandlers(s, newShape);
            SHAPES.put(s.getId(), newShape);
        }
        return;
    }

    /**
     * Removes a shape from the toolbar and adjust it
     */
    @Override
    public void remove() {
        List<Node> nodes = new ArrayList<>();
        for (Node n : toolBar.getChildren()) {
            if (n instanceof javafx.scene.shape.Rectangle || n instanceof javafx.scene.shape.Polygon) {
                nodes.add(n);
            }
        }
        for (Node n : nodes) {
            toolBar.getChildren().remove(n);
        }
        Toolbar.getInstance().resetPosition();
        for (Shape s : Toolbar.getInstance().getShapes()) {
            if (s instanceof CompoundShape) {
                createToolbarCompoundShape((CompoundShape) s);
            } else {
                s.setPosition(Toolbar.getInstance().getNextPosition());
                s.notifyObserver();
            }
        }
    }

    public void setLastSelected(Shape shape, javafx.scene.shape.Shape shapeFX) {
        lastSelected = shape;
        lastFXSelected = shapeFX;
    }

}
