package model;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

    @FXML
    private ImageView bin;

    private final Color BORDER_COLOR = new Color(68.0 / 255, 114.0 / 255, 196.0 / 255, 1);

    private final Position TOOLBAR_ORIGIN = new Position(10, 10);

    private static Implementor instance;

    private Map<Long, javafx.scene.shape.Shape> SHAPES = new HashMap<>();;

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
        bin = (ImageView) root.lookup("#bin");
        // Create ContextMenu
        contextMenu = new ContextMenu();
        contextMenu.setId("contextMenu");
        javafx.scene.control.MenuItem group = new javafx.scene.control.MenuItem("Group");

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
        group.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                CompoundShape compoundShape = new CompoundShape(FXImplementor.getInstance(), new CanvasPosition(0,0));
                for (Shape s : Canvas.getInstance().createCompound()) {
                    compoundShape.add(s);
                }
                for(Shape s : compoundShape.getShapes()) {
                    canvas.getChildren().remove(SHAPES.get(s.getId()));
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

        contextMenu.getItems().addAll(group, edit);

        addToolbarHandlers();
        addCanvasHandlers();
        addBinHandlers();

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

    private void addToolbarHandlers() {
        leftBar.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    long id = Long.parseLong(db.getString());
                    if(Canvas.getInstance().contains(id)) {
                        Shape original = Canvas.getInstance().getShape(id);
                        original.setSelected(false);
                        Shape copy = original.clone();
                        copy.setId();
                        if(copy instanceof CompoundShape) {
                            createToolbarCompoundShape((CompoundShape)copy);
                            copy.setPosition(new ToolbarPosition());
                            Toolbar.getInstance().add(copy);
                        }
                        else {
                            copy.setPosition(new ToolbarPosition());
                            Toolbar.getInstance().addAndNotify(copy);
                        }

                    }
                    else {
                        System.out.println("FROM TOOLBAR TO TOOLBAR");
                    }
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

        leftBar.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node
                 * and if it has a string data */
                if (event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        leftBar.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                //leftBar.setOpacity(0.00001);
            }
        });

        leftBar.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                //leftBar.setOpacity(1);

            }
        });



    }

    private void addCanvasHandlers() {
        canvas.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    long id = Long.parseLong(db.getString());
                    if(Canvas.getInstance().contains(id)) {
                        Shape original = Canvas.getInstance().getShape(id);
                        Shape copy = original.clone();
                        Canvas.getInstance().remove(original);

                        if(original instanceof CompoundShape) {
                            ((CompoundShape)copy).translate(new Position(event.getX()-((CompoundShape) original).getTopLeft().getX(),
                                    event.getY()-((CompoundShape) original).getTopLeft().getY()));
                            canvas.getChildren().clear();
                            Canvas.getInstance().add(copy);
                            Canvas.getInstance().notifyAllShapes();
                        }
                        else {
                            canvas.getChildren().remove(SHAPES.get(original.getId()));
                            copy.setPosition(new CanvasPosition(event.getX(), event.getY()));
                            Canvas.getInstance().addAndNotify(copy);
                        }
                    }
                    else {
                        Shape original = Toolbar.getInstance().getShape(id);
                        Shape copy = original.clone();
                        if(original instanceof CompoundShape) {
                            copy.setId();
                            ((CompoundShape)copy).translate(new Position(event.getX()-((CompoundShape) original).getTopLeft().getX(),
                                    event.getY()-((CompoundShape) original).getTopLeft().getY()));
                        }
                        else {
                            copy = original.clone();
                            copy.setId();
                            copy.setPosition(new CanvasPosition(event.getX(), event.getY()));
                        }
                        Canvas.getInstance().addAndNotify(copy);
                        System.out.println(Canvas.getInstance().getShapes());
                        System.out.println(canvas.getChildren());
                    }

                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

        canvas.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node
                 * and if it has a string data */
                if (event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        canvas.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
            }
        });

        canvas.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
            }
        });

        EventHandler<MouseEvent> setGestureStarted = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Canvas.getInstance().resetSelection();
                canvas.getChildren().clear();
                Canvas.getInstance().notifyAllShapes();
                Canvas.getInstance().setStartSelectPos(e.getX(), e.getY());
                Canvas.getInstance().setSelection(true);

            }
        };


        canvas.addEventFilter(MouseEvent.MOUSE_PRESSED, setGestureStarted);

        EventHandler<MouseEvent> changeRectangle = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(Canvas.getInstance().getSelection()) {
                    Position start = Canvas.getInstance().getStartSelectPos();
                    javafx.scene.shape.Rectangle selectionRectangle = new javafx.scene.shape.Rectangle();
                    selectionRectangle.setX(start.getX());
                    selectionRectangle.setY(start.getY());
                    selectionRectangle.setOpacity(0.5);
                    selectionRectangle.setHeight(e.getX() - start.getX());
                    selectionRectangle.setWidth(e.getY() - start.getY());
                    canvas.getChildren().add(selectionRectangle);
                }
            }
        };

        //canvas.addEventFilter(MouseEvent.MOUSE_MOVED, changeRectangle);

        EventHandler<MouseEvent> endSelection = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(Canvas.getInstance().getSelection()) {
                    Position firstPos = Canvas.getInstance().getStartSelectPos();
                    Canvas.getInstance().setSelection(false);
                    Position secondPos = new Position(e.getX(), e.getY());
                    for(Shape s : Canvas.getInstance().getShapes()) {
                        if(secondPos.getX() > firstPos.getX() && secondPos.getY() > firstPos.getY() && s.isInside(firstPos, secondPos)) {
                            s.setSelected(true);
                            s.notifyObserver();
                        }
                        else if(secondPos.getX() < firstPos.getX() && secondPos.getY() < firstPos.getY() && s.isInside(secondPos, firstPos)) {
                            s.setSelected(true);
                            s.notifyObserver();
                        }
                        else if(secondPos.getX() > firstPos.getX() && secondPos.getY() < firstPos.getY()) {
                            Position firstIntermediatePos = new Position(secondPos.getX(), firstPos.getY());
                            Position secondIntermediatePos = new Position(firstPos.getX(),secondPos.getY());
                            if(s.isInside(secondIntermediatePos, firstIntermediatePos)) {
                                s.setSelected(true);
                                s.notifyObserver();
                            }
                        }
                        else if(secondPos.getX() < firstPos.getX() && secondPos.getY() > firstPos.getY()) {
                            Position firstIntermediatePos = new Position(secondPos.getX(), firstPos.getY());
                            Position secondIntermediatePos = new Position(firstPos.getX(),secondPos.getY());
                            if(s.isInside(firstIntermediatePos, secondIntermediatePos)) {
                                s.setSelected(true);
                                s.notifyObserver();
                            }
                        }

                    }
                }
            }
        };

        canvas.addEventFilter(MouseEvent.MOUSE_RELEASED, endSelection);
    }

    private void addBinHandlers() {
        bin.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    long id = Long.parseLong(db.getString());
                    if(Canvas.getInstance().contains(id)) {
                        Shape original = Canvas.getInstance().getShape(id);
                        Canvas.getInstance().remove(original);
                        if(original instanceof CompoundShape) {
                            canvas.getChildren().clear();
                            Canvas.getInstance().notifyAllShapes();
                        }
                        else {
                            canvas.getChildren().remove(SHAPES.get(id));
                        }
                    }
                    else {
                        Shape original = Toolbar.getInstance().getShape(id);
                        Toolbar.getInstance().remove(original);
                        remove();
                    }

                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);

                event.consume();
            }
        });

        bin.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                /* accept it only if it is not dragged from the same node
                 * and if it has a string data */
                if (event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        bin.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                bin.setOpacity(0.5);
            }
        });

        bin.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent dragEvent) {
                bin.setOpacity(1);
            }
        });

    }

    private void commonHandlers(Shape s, javafx.scene.shape.Shape newShape) {
        newShape.setCursor(Cursor.HAND);
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

            newShape.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {

                @Override
                public void handle(ContextMenuEvent event) {
                    boolean anySelected = false;
                    for(Shape s : Canvas.getInstance().getShapes()) {
                        if (s.isSelected()) {
                            anySelected = true;
                        }
                    }
                    if (!anySelected) {
                        Canvas.getInstance().resetSelection();
                        s.setSelected(true);
                        Canvas.getInstance().notifyAllShapes();
                    }
                    contextMenu.show(newShape, event.getScreenX(), event.getScreenY());
                }
            });
        }
    }

    private void compoundShapeHandlers(CompoundShape s, Map<Shape,javafx.scene.shape.Shape> compoundShapes) {
        EventHandler<MouseEvent> hoverColor = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()){
                    newShape.getValue().setCursor(Cursor.HAND);
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



            for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()){
                newShape.getValue().addEventFilter(MouseEvent.MOUSE_CLICKED, selection);
            }
        }

        for (Map.Entry<Shape,javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()){
            newShape.getValue().addEventFilter(MouseEvent.MOUSE_ENTERED, hoverColor);
            newShape.getValue().addEventFilter(MouseEvent.MOUSE_EXITED, setBackColor);
        }
    }

    private void dragAndDropHandlers(Shape shape, javafx.scene.shape.Shape newShape) {

        newShape.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                String id = Long.toString(shape.getId());
                Dragboard db = newShape.startDragAndDrop(TransferMode.ANY);
                ClipboardContent cp = new ClipboardContent();
                cp.putString(id);
                db.setContent(cp);
            }
        });

        newShape.setOnDragDone((t) -> {
        });

        /*newShape.setOnMouseDragged((t) -> {
            javafx.scene.shape.Rectangle c = (javafx.scene.shape.Rectangle)(t.getSource());

            c.setX(t.getX());
            c.setY(t.getY());

            s.getPositionI().setX(t.getSceneX());
            s.getPositionI().setY(t.getSceneY());
        });*/


    }

    private void dragAndDropHandlersCompound(CompoundShape s, Map<Shape,javafx.scene.shape.Shape> compoundShapes) {
        EventHandler setOnDragEntered = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (Map.Entry<Shape, javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
                    String id = Long.toString(s.getId());
                    Dragboard db = newShape.getValue().startDragAndDrop(TransferMode.ANY);
                    ClipboardContent cp = new ClipboardContent();
                    cp.putString(id);
                    db.setContent(cp);
                }
            }
        };

        EventHandler test = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("CCCCCCCCCCC" + s.getId());
                for(Shape ss : s.getShapes()){
                    System.out.println(ss.getId());
                }
            }
        };


        for (Map.Entry<Shape, javafx.scene.shape.Shape> newShape : compoundShapes.entrySet()) {
            newShape.getValue().setOnDragDetected(setOnDragEntered);
            newShape.getValue().setOnMouseEntered(test);
        }


        /*newShape.setOnMouseDragged((t) -> {
            javafx.scene.shape.Rectangle c = (javafx.scene.shape.Rectangle)(t.getSource());

            c.setX(t.getX());
            c.setY(t.getY());

            s.getPositionI().setX(t.getSceneX());
            s.getPositionI().setY(t.getSceneY());
        });*/


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
                compoundShapes.put(shape, rectangle);
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
        dragAndDropHandlersCompound(s, compoundShapes);

    }

    private void createCompoundShape(Shape s) {
        Map<Shape, javafx.scene.shape.Shape> compoundShapes = new HashMap<>();
        for (Shape shape : ((CompoundShape) s).getShapes()) {
            if (shape instanceof Rectangle) {
                compoundShapes.put(shape, createRectangle((Rectangle) shape));
            } else if (shape instanceof Polygon) {
                compoundShapes.put(shape, createPolygon((Polygon) shape));
            }
        }

        compoundShapeHandlers((CompoundShape) s, compoundShapes);
        dragAndDropHandlersCompound((CompoundShape) s, compoundShapes);
    }

    @Override
    public void draw(Shape s) {
        if (s instanceof CompoundShape) {
            createCompoundShape(s);
        } else if (s instanceof Rectangle) {
            javafx.scene.shape.Shape newShape = createRectangle((Rectangle) s);
            commonHandlers(s, newShape);
            dragAndDropHandlers(s, newShape);

            SHAPES.put(s.getId(), newShape);

        } else if (s instanceof Polygon) {
            javafx.scene.shape.Shape newShape = createPolygon((Polygon) s);
            commonHandlers(s, newShape);
            dragAndDropHandlers(s, newShape);
            SHAPES.put(s.getId(), newShape);

        }
        return;
    }

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
