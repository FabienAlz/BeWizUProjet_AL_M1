package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import utils.FXContextMenuHandlers;
import utils.FXDragAndDropHandlers;
import utils.FXMouseHandlers;

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
    @FXML
    private ImageView bin;

    public final Color BORDER_COLOR = new Color(68.0 / 255, 114.0 / 255, 196.0 / 255, 1);

    private static FXImplementor instance;

    public static Stage stage;

    public Popup popup;

    private Map<Long, javafx.scene.shape.Shape> SHAPES = new HashMap<>();

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

    public Pane getRoot() {
        return root;
    }

    public Pane getCanvas() {
        return canvas;
    }

    public Pane getLeftBar() {
        return leftBar;
    }

    public ImageView getBin() {
        return bin;
    }

    public ContextMenu getContextMenu() { return contextMenu; }

    /**
     * Starts the javafx application
     * @param primaryStage the stage to start
     * @throws Exception
     */
    public void start(Stage primaryStage) throws Exception {
        popup = new Popup();
        stage = primaryStage;
        root = FXMLLoader.load(getClass().getResource("../view/view.fxml"));
        canvas = (Pane) root.lookup("#canvas");
        leftBar = (Pane) root.lookup("#leftBar");
        bin = (ImageView) root.lookup("#bin");
        // Create ContextMenu
        contextMenu = new ContextMenu();
        contextMenu.setId("contextMenu");

        GridPane editRectangleGrid = new GridPane();

        editRectangleGrid.setPadding(new Insets(10, 10, 10, 10));
        Label widthLabel = new Label("Width:");
        TextField widthTextField = new TextField();
        Label heightLabel = new Label("Height:");
        TextField heightTextField = new TextField ();
        Label borderRadiusLabel = new Label("Border radius:");
        TextField borderRadiusTextField = new TextField ();
        Label rotationRectangleLabel = new Label("Rotation:");
        TextField rotationRectangleTextField = new TextField();


        HBox hbRectangleColorPicker = new HBox();
        final ColorPicker colorPickerRectangle = new ColorPicker();
        hbRectangleColorPicker.getChildren().add(colorPickerRectangle);

        HBox hbRectangleValues = new HBox();
        hbRectangleValues.getChildren().addAll(widthLabel, widthTextField);
        hbRectangleValues.getChildren().addAll(heightLabel, heightTextField);
        hbRectangleValues.getChildren().addAll(borderRadiusLabel, borderRadiusTextField);
        hbRectangleValues.getChildren().addAll(rotationRectangleLabel, rotationRectangleTextField);
        hbRectangleValues.setSpacing(10);

        HBox hbRectangleButtons = new HBox();
        Button okRectangleButton = new Button("Ok");
        Button applyRectangleButton = new Button("Apply");
        Button cancelRectangleButton = new Button("Cancel");
        hbRectangleButtons.getChildren().add(okRectangleButton);
        hbRectangleButtons.getChildren().add(applyRectangleButton);
        hbRectangleButtons.getChildren().add(cancelRectangleButton);
        hbRectangleButtons.setSpacing(15);
        editRectangleGrid.setHgap(10);
        editRectangleGrid.setVgap(10);
        editRectangleGrid.setPadding(new Insets(10, 10, 10, 10));
        editRectangleGrid.add(hbRectangleColorPicker,0,0);
        editRectangleGrid.add(hbRectangleValues,0,1);
        editRectangleGrid.add(hbRectangleButtons, 0,2);
        editRectangleGrid.setStyle("-fx-background-color: #FFFFFF");
        canvas.getChildren().add(editRectangleGrid);
        editRectangleGrid.setVisible(false);



        GridPane editPolygonGrid = new GridPane();

        editRectangleGrid.setPadding(new Insets(10, 10, 10, 10));
        Label lengthLabel = new Label("Length:");
        TextField lengthTextField = new TextField();
        Label edgesLabel = new Label("edges:");
        TextField edgesTextField = new TextField ();
        Label rotationPolygonLabel = new Label("Rotation:");
        TextField rotationPolygonTextField = new TextField ();

        HBox hbPolygonColorPicker = new HBox();
        final ColorPicker colorPickerPolygon = new ColorPicker();
        hbPolygonColorPicker.getChildren().add(colorPickerPolygon);

        HBox hbPolygonValues = new HBox();
        hbPolygonValues.getChildren().addAll(lengthLabel, lengthTextField);
        hbPolygonValues.getChildren().addAll(edgesLabel, edgesTextField);
        hbPolygonValues.getChildren().addAll(rotationPolygonLabel, rotationPolygonTextField);
        hbPolygonValues.setSpacing(10);

        HBox hbPolygonButtons = new HBox();
        Button okPolygonButton = new Button("Ok");
        Button applyPolygonButton = new Button("Apply");
        Button cancelPolygonButton = new Button("Cancel");
        hbPolygonButtons.getChildren().add(okPolygonButton);
        hbPolygonButtons.getChildren().add(applyPolygonButton);
        hbPolygonButtons.getChildren().add(cancelPolygonButton);
        hbPolygonButtons.setSpacing(15);
        editPolygonGrid.setHgap(10);
        editPolygonGrid.setVgap(10);
        editPolygonGrid.setPadding(new Insets(10, 10, 10, 10));
        editPolygonGrid.add(hbPolygonColorPicker,0,0);
        editPolygonGrid.add(hbPolygonValues,0,1);
        editPolygonGrid.add(hbPolygonButtons, 0,2);
        editPolygonGrid.setStyle("-fx-background-color: #FFFFFF");
        canvas.getChildren().add(editPolygonGrid);
        editPolygonGrid.setVisible(false);


        // Handler to create group of shapes
        // Handler to create group of shapes
        javafx.scene.control.MenuItem group = new javafx.scene.control.MenuItem("Group");
        group.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CompoundShape compoundShape = new CompoundShape(FXImplementor.getInstance(), new CanvasPosition(0,0));
                for (Shape s : Canvas.getInstance().createCompound()) {
                    compoundShape.add(s);
                }
                for(Shape s : compoundShape.getShapes()) {
                    canvas.getChildren().remove(SHAPES.get(s.getId()));
                    System.out.println(s.getId());
                }
                ShapeObserver obs = new ConcreteShapeObserver();
                compoundShape.addObserver(obs);
                Canvas.getInstance().addAndNotify(compoundShape);
            }
        });

        javafx.scene.control.MenuItem edit = new MenuItem("Edit");
        // Handler to edit the shapes
        edit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("EDIT BUTTON ");
                for(Shape s : Canvas.getInstance().getShapes()) {
                    if (s.isSelected()) {
                        if (s instanceof Rectangle) {
                            colorPickerRectangle.setValue(Color.valueOf(s.getColor()));
                            widthTextField.setText(String.valueOf(s.getWidth()));
                            heightTextField.setText(String.valueOf(s.getHeight()));
                            rotationRectangleTextField.setText(String.valueOf(s.getRotation()));
                            borderRadiusTextField.setText(String.valueOf(((Rectangle) s).getBorderRadius()));
                            editRectangleGrid.setVisible(true);
                            editPolygonGrid.setVisible(false);
                            editRectangleGrid.toFront();
                        }
                        else if (s instanceof Polygon) {
                            colorPickerPolygon.setValue(Color.valueOf(s.getColor()));
                            rotationPolygonTextField.setText(String.valueOf(s.getRotation()));
                            lengthTextField.setText(String.valueOf(((Polygon) s).getLength()));
                            edgesTextField.setText(String.valueOf(((Polygon) s).getEdges()));
                            editPolygonGrid.setVisible(true);
                            editRectangleGrid.setVisible(false);
                            editPolygonGrid.toFront();
                        }
                    }
                }


            }
        });

        javafx.scene.control.MenuItem degroup = new MenuItem("Degroup");
        // Handler to edit the shapes
        degroup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                List<Shape> shapesToRemove = new ArrayList<>();
                List<Shape> shapesToAdd = new ArrayList<>();

                for(Shape s : Canvas.getInstance().getShapes()) {
                    if(s.isSelected() && s instanceof CompoundShape) {
                        for(Shape compoundShape : ((CompoundShape) s).getShapes()) {
                            Shape copy = compoundShape.clone();
                            copy.setSelected(false);
                            copy.setId();
                            shapesToAdd.add(copy);

                        }
                        shapesToRemove.add(s);
                    }
                    else s.setSelected(false);
                }
                for(Shape s : shapesToRemove) {
                    Canvas.getInstance().remove(s);
                }
                for(Shape s : shapesToAdd) {
                    Canvas.getInstance().add(s);
                }
                canvas.getChildren().clear();
                Canvas.getInstance().notifyAllShapes();
            }
        });


        contextMenu.getItems().addAll(group, degroup, edit);

        setToolbarHandlers();
        setCanvasHandlers();
        setBinHandlers();

        primaryStage.setResizable(false);
        primaryStage.setTitle("BeWizU");
        primaryStage.setScene(new Scene(root, 1080, 650));
        primaryStage.show();

        EventHandler<MouseEvent> okButtonClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                canvas.getChildren().clear();
                for(Shape s : Canvas.getInstance().getShapes()) {
                    if (s.isSelected()) {
                        if (s instanceof Rectangle) {
                            canvas.getChildren().add(editRectangleGrid);
                            ((Rectangle) s).setWidth(Float.parseFloat(widthTextField.getText()));
                            ((Rectangle) s).setHeight(Float.parseFloat(heightTextField.getText()));
                            ((Rectangle) s).setBorderRadius(Float.parseFloat(borderRadiusTextField.getText()));
                            ((Rectangle) s).setRotation(Float.parseFloat(rotationRectangleTextField.getText()));
                            ((Rectangle) s).setColor(String.valueOf(colorPickerRectangle.getValue()));
                            editRectangleGrid.setVisible(false);
                        } else if (s instanceof Polygon) {
                            canvas.getChildren().add(editPolygonGrid);
                            ((Polygon) s).setEdges(Integer.parseInt(edgesTextField.getText()));
                            ((Polygon) s).setColor(String.valueOf(colorPickerPolygon.getValue()));
                            ((Polygon) s).setRotation(Float.parseFloat(rotationPolygonTextField.getText()));
                            ((Polygon) s).setLength(Float.parseFloat(lengthTextField.getText()));
                            editPolygonGrid.setVisible(false);
                        }
                    }
                }
                Canvas.getInstance().resetSelection();
                Canvas.getInstance().notifyAllShapes();
            }
        };
        okRectangleButton.addEventFilter(MouseEvent.MOUSE_CLICKED, okButtonClick);
        okPolygonButton.addEventFilter(MouseEvent.MOUSE_CLICKED, okButtonClick);

        EventHandler<MouseEvent> applyButtonClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                canvas.getChildren().clear();
                for(Shape s : Canvas.getInstance().getShapes()) {
                    if (s.isSelected()) {
                        if (s instanceof Rectangle) {
                            canvas.getChildren().add(editRectangleGrid);
                            ((Rectangle) s).setWidth(Float.parseFloat(widthTextField.getText()));
                            ((Rectangle) s).setHeight(Float.parseFloat(heightTextField.getText()));
                            ((Rectangle) s).setBorderRadius(Float.parseFloat(borderRadiusTextField.getText()));
                            ((Rectangle) s).setRotation(Float.parseFloat(rotationRectangleTextField.getText()));
                            ((Rectangle) s).setColor(String.valueOf(colorPickerRectangle.getValue()));
                            Canvas.getInstance().notifyAllShapes();
                            editRectangleGrid.toFront();
                        } else if (s instanceof Polygon) {
                            canvas.getChildren().add(editPolygonGrid);
                            ((Polygon) s).setEdges(Integer.parseInt(edgesTextField.getText()));
                            ((Polygon) s).setColor(String.valueOf(colorPickerPolygon.getValue()));
                            ((Polygon) s).setRotation(Float.parseFloat(rotationPolygonTextField.getText()));
                            ((Polygon) s).setLength(Float.parseFloat(lengthTextField.getText()));
                            Canvas.getInstance().notifyAllShapes();
                            editPolygonGrid.toFront();
                        }
                    }
                }
            }
        };
        applyRectangleButton.addEventFilter(MouseEvent.MOUSE_CLICKED, applyButtonClick);
        applyPolygonButton.addEventFilter(MouseEvent.MOUSE_CLICKED, applyButtonClick);

        EventHandler<MouseEvent> cancelButtonClick = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                editRectangleGrid.setVisible(false);
            }
        };
        cancelRectangleButton.addEventFilter(MouseEvent.MOUSE_CLICKED, cancelButtonClick);
        cancelPolygonButton.addEventFilter(MouseEvent.MOUSE_CLICKED, cancelButtonClick);
    }
    /**
     * binds the handlers to the toolbar
     */
    private void setToolbarHandlers() {
        FXDragAndDropHandlers myHandler = new FXDragAndDropHandlers();
        leftBar.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myHandler.toolbarOnDragDropped(event);
            }
        });

        leftBar.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent dragEvent) {
                myHandler.toolbarOnDragOver(dragEvent);
            }
        });

        leftBar.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myHandler.toolbarOnDragEntered(dragEvent);
            }
        });

        leftBar.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myHandler.toolbarOnDragExited(dragEvent);
            }
        });
    }

    /**
     * binds the handlers to the canvas
     */
    private void setCanvasHandlers() {
        javafx.scene.shape.Rectangle rectangleSelection = new javafx.scene.shape.Rectangle();
        rectangleSelection.setOpacity(0.3);
        rectangleSelection.setFill(BORDER_COLOR);
        rectangleSelection.setStroke(new Color(0,0,1,1));
        rectangleSelection.setStrokeWidth(2);
        FXMouseHandlers myHandler = new FXMouseHandlers(null, rectangleSelection);
        FXDragAndDropHandlers myDragAndDropHandler = new FXDragAndDropHandlers();

        /*******************************************************
         *                    DRAG EVENTS                      *
         *******************************************************/
        canvas.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myDragAndDropHandler.canvasOnDragDropped(event);
            }
        });

        canvas.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myDragAndDropHandler.canvasOnDragOver(event);
            }
        });

        canvas.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myDragAndDropHandler.canvasOnDragEntered(dragEvent);
            }
        });

        canvas.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myDragAndDropHandler.canvasOnDragExited(dragEvent);
            }
        });

        /*************************************************
         *          MOUSE HANDLERS FOR SELECTION         *
         *************************************************/
        EventHandler<MouseEvent> setGestureStarted = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                myHandler.setGestureStarted(e);
            }
        };

        canvas.setOnMousePressed(setGestureStarted);

        EventHandler<MouseEvent> updateSelectionRectangle = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                myHandler.updateSelectionRectangle(e);
            }
        };
        canvas.setOnMouseDragged(updateSelectionRectangle);

        EventHandler<MouseEvent> endSelection = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                myHandler.endSelection(e);
            }

        };

        canvas.addEventFilter(MouseEvent.MOUSE_RELEASED, endSelection);
    }
    /**
     * binds the handlers to the bin
     */
    private void setBinHandlers() {
        FXDragAndDropHandlers myDragAndDropHandler = new FXDragAndDropHandlers();
        bin.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myDragAndDropHandler.binOnDragDropped(event);
            }
        });

        bin.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                myDragAndDropHandler.binOnDragOver(event);
            }
        });

        bin.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myDragAndDropHandler.binOnDragEntered(dragEvent);
            }
        });

        bin.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                myDragAndDropHandler.binOnDragExited(dragEvent);
            }
        });

    }
    /**
     * binds the handlers to single shapes
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
     * binds the handlers to the compound shapes
     */
    private void compoundShapeHandlers(CompoundShape s, Map<Shape,javafx.scene.shape.Shape> compoundShapes) {
        EventHandler<MouseEvent> hoverColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
                    FXMouseHandlers myHandler = new FXMouseHandlers(newShape.getKey(), newShape.getValue());
                    myHandler.hoverColor(e);
                }
            }
        };

        EventHandler<MouseEvent> setBackColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
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
            for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()){
                newShape.getValue().addEventFilter(MouseEvent.MOUSE_CLICKED, selection);
            }
        }

        for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()){
            newShape.getValue().addEventFilter(MouseEvent.MOUSE_ENTERED, hoverColor);
            newShape.getValue().addEventFilter(MouseEvent.MOUSE_EXITED, setBackColor);
            newShape.getValue().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
                @Override
                public void handle(ContextMenuEvent e) {
                    FXContextMenuHandlers myContextMenuHandler = new FXContextMenuHandlers(s, newShape.getValue());
                    myContextMenuHandler.manageContextMenu(e);
                }
            });

        }
    }

    /**
     * binds the drop handlers to the single shapes
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
     * binds the drop handlers to the compound shapes
     */
    private void dragHandlersCompound(CompoundShape s, Map<Shape,javafx.scene.shape.Shape> compoundShapes) {
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
        if (s.getPositionI() instanceof ToolbarPosition) {
            newShape.setStrokeWidth(0);
            if (s.getWidth() > leftBar.getWidth() - 24) {
                float ratio = (float) (s.getWidth() / (leftBar.getWidth() - 24));
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

    /**
     * Computes a javafx.scene.shape.Rectangle from a given Rectangle in a CompoundShape
     * @param s Rectangle from a CompoundShape
     * @return a javafx.scene.shape.Rectangle in the Toolbar
     */
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
            newShape.setWidth(s.getWidth() / ratio);
            newShape.setHeight(s.getHeight() / ratio);
        }
        newShape.setX(s.getPositionI().getX());
        newShape.setY(s.getPositionI().getY());
        leftBar.getChildren().add(newShape);

        return newShape;
    }

    /**
     * Computes a javafx.scene.shape.Polygon from a shape
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
            if (s.getWidth() > leftBar.getWidth() - 35) {
                float ratio = (float) (s.getWidth() / (leftBar.getWidth() - 35));
                Polygon copy = s.clone();
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

    /**
     * Computes a javafx.scene.shape.Polygon from a given Rectangle in a CompoundShape
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

        leftBar.getChildren().add(newShape);
        return newShape;
    }

    /**
     * Puts a CompoundShape in the Toolbar and resize it if necessary
     * @param s CompoundShape to put in the Toolbar
     */
    public void createToolbarCompoundShape(CompoundShape s) {
        Map<Shape,javafx.scene.shape.Shape> compoundShapes = new HashMap<>();
        float width = s.getWidth();
        float ratio = 1;
        if(width > leftBar.getWidth()-35) {
            ratio = (float)(width/(leftBar.getWidth()-35));
        }

        for (Shape shape : s.getShapes()) {
            float posX = (float)(Toolbar.getInstance().getNextPosition().getX() +
                    (shape.getPositionI().getX()-s.getPositionI().getX())/ratio);
            float posY = (float)(Toolbar.getInstance().getNextPosition().getY() +
                    (shape.getPositionI().getY()-s.getPositionI().getY())/ratio);


            ToolbarPosition pos = new ToolbarPosition(posX, posY);
            if (shape instanceof Rectangle) {
                Rectangle copy = (Rectangle) shape.clone();
                copy.setId();
                copy.setWidth(shape.getWidth()/ratio);
                copy.setHeight(shape.getHeight()/ratio);
                copy.setPosition(pos);
                javafx.scene.shape.Rectangle rectangle = createToolbarCompoundRectangle(copy);
                SHAPES.put(copy.getId(),rectangle);
                compoundShapes.put(copy, rectangle);
            } else if (shape instanceof Polygon) {
                Polygon copy = (Polygon) shape.clone();
                copy.setId();
                copy.setLength(copy.getLength()/ratio);
                copy.setPosition(pos);
                javafx.scene.shape.Polygon polygon = createToolbarCompoundPolygon(copy);
                SHAPES.put(copy.getId(), polygon);
                compoundShapes.put(copy, polygon);
            }
        }

        s.setPosition(new ToolbarPosition());
        Toolbar.getInstance().setNextPosition((int)(s.getHeight()/ratio));
        compoundShapeHandlers(s, compoundShapes);
        dragHandlersCompound(s, compoundShapes);

    }

    /**
     * Creates a javafx CompoundShape from one or many Shapes
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
        for(Map.Entry<Shape, javafx.scene.shape.Shape> shape : compoundShapes.entrySet()) {
            SHAPES.put(shape.getKey().getId(), shape.getValue());
        }
        compoundShapeHandlers(s, compoundShapes);
        dragHandlersCompound(s, compoundShapes);
    }

    /**
     * Creates a javafx Shape of a given Shape
     * @param s a Shape to draw in javafx
     */
    @Override
    public void draw(Shape s) {
        if (s instanceof CompoundShape) {
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
        for (Node n : leftBar.getChildren()) {
            if (n instanceof javafx.scene.shape.Rectangle || n instanceof javafx.scene.shape.Polygon) {
                nodes.add(n);
            }
        }
        for (Node n : nodes) {
            leftBar.getChildren().remove(n);
        }
        Toolbar.getInstance().resetPosition();
        for (Shape s : Toolbar.getInstance().getShapes()) {
            if(s instanceof CompoundShape) {
                createToolbarCompoundShape((CompoundShape) s);
            }
            else {
                s.setPosition(Toolbar.getInstance().getNextPosition());
                s.notifyObserver();
            }
        }
    }
}
