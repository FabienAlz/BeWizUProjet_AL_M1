package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompoundShape extends AbstractShape implements Serializable {
    private static final long serialVersionUID = 7L;

    private List<Shape> shapes;

    /**
     * Creates a CompoundShape
     *
     * @param implementor
     * @param position
     */
    public CompoundShape(Implementor implementor, PositionI position) {
        super(position, new Translation(0, 0), implementor);
        this.shapes = new ArrayList<>();
    }

    public CompoundShape() {
    }

    /******************************
     *          GETTERS           *
     ******************************/
    @Override
    public float getRotation() {
        return 0;
    }

    @Override
    public String getColor() {
        return null;
    }

    @Override
    public PositionI getRotationCenter() {
        return null;
    }

    @Override
    public float getWidth() {
        double minX = ((SingleShape) shapes.get(0)).getVertices().get(0);
        double maxX = ((SingleShape) shapes.get(0)).getVertices().get(0);
        for (Shape s : shapes) {
            if (s instanceof SingleShape) {
                for (int i = 0; i < ((SingleShape) s).getVertices().size(); i += 2) {
                    if (maxX < ((SingleShape) s).getVertices().get(i)) maxX = ((SingleShape) s).getVertices().get(i);
                    if (minX > ((SingleShape) s).getVertices().get(i)) minX = ((SingleShape) s).getVertices().get(i);
                }
            }
        }

        return (float) (maxX - minX);
    }

    public float getHeight() {
        double minY = ((SingleShape) shapes.get(0)).getVertices().get(1);
        double maxY = ((SingleShape) shapes.get(0)).getVertices().get(1);

        for (Shape s : shapes) {
            if (s instanceof SingleShape) {
                for (int i = 1; i < ((SingleShape) s).getVertices().size(); i += 2) {
                    if (maxY < ((SingleShape) s).getVertices().get(i)) maxY = ((SingleShape) s).getVertices().get(i);
                    if (minY > ((SingleShape) s).getVertices().get(i)) minY = ((SingleShape) s).getVertices().get(i);
                }
            }
        }
        return (float) (maxY - minY);
    }

    public Position getTopLeft() {
        double minX = shapes.get(0).getPositionI().getX();
        double minY = shapes.get(0).getPositionI().getY();
        for (Shape s : shapes) {
            if (s instanceof SingleShape) {
                for (int i = 0; i < ((SingleShape) s).getVertices().size(); i++) {
                    if (i % 2 == 0 && minX > ((SingleShape) s).getVertices().get(i))
                        minX = ((SingleShape) s).getVertices().get(i);
                    else if (i % 2 == 1 && minY > ((SingleShape) s).getVertices().get(i))
                        minY = ((SingleShape) s).getVertices().get(i);
                }
            }
        }
        return new Position(minX, minY);
    }

    public List<Shape> getShapes() {
        return shapes;
    }

    /******************************
     *          SETTERS           *
     ******************************/
    @Override
    public void setSelected(boolean b) {
        super.setSelected(b);
        for (Shape s : shapes) {
            s.setSelected(b);
        }
    }

    @Override
    public void setRotation(float rotation) {
        for (Shape s : shapes) {
            s.setRotation(rotation);
        }
    }

    @Override
    public void setColor(String color) {
        for (Shape s : shapes) {
            s.setColor(color);
        }
    }

    /**
     * Translates all the shapes of the CompoundShape to their location + the translation
     *
     * @param translation
     */
    public void translate(Translation translation) {
        CanvasPosition compoundPos = new CanvasPosition(getTopLeft().getX() + translation.getX(),
                getTopLeft().getY() + translation.getY());
        this.setTranslation(translation);
        this.setPosition(compoundPos);
        for (Shape shape : shapes) {
            double posX = shape.getPositionI().getX() + translation.getX();
            double posY = shape.getPositionI().getY() + translation.getY();
            shape.setPosition(new CanvasPosition(posX, posY));
            ((SingleShape) shape).computeVertices();

        }
    }

    /**
     * Checks if the Shape is inside a Rectangle
     *
     * @param startingPoint
     * @param arrival
     * @return
     */
    public boolean isInside(Position startingPoint, Position arrival) {
        for (Shape s : shapes) {
            if (!s.isInside(startingPoint, arrival))
                return false;
        }
        return true;
    }

    /**
     * Compares a Shape with this CompoundShape using the Shapes they're composed of
     *
     * @param s the Shape to compare
     * @return
     */
    @Override
    public boolean equals(Shape s) {
        if (s instanceof CompoundShape) {
            for (int index = 0; index < shapes.size(); index++) {
                if (!shapes.get(index).equals(((CompoundShape) s).getShapes().get(index))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Adds a Shape to the CompoundShape
     *
     * @param s
     */
    public void add(Shape s) {
        // Computes the top-left position
        if (shapes.isEmpty()) {
            this.getPositionI().setX(s.getPositionI().getX());
            this.getPositionI().setY(s.getPositionI().getY());
        } else {
            if (s.getPositionI().getX() < this.getPositionI().getX()) {
                this.getPositionI().setX(s.getPositionI().getX());
            }
            if (s.getPositionI().getY() < this.getPositionI().getY()) {
                this.getPositionI().setY(s.getPositionI().getY());
            }
        }
        // Add all the Shapes of the CompoundShape to this one
        if (s instanceof CompoundShape) {
            for (Shape shape : ((CompoundShape) s).getShapes()) {
                shape.setSelected(false);
                shapes.add(shape);
            }
        } else {
            s.setSelected(false);
            shapes.add(s);
        }
    }

    public void remove(Shape s) {
        shapes.remove(s);
    }

    public void clear() {
        shapes.clear();
    }

    /**
     * Clones this CompoundShape and deep copies its shapes
     *
     * @return a copy of this CompoundShape
     */
    @Override
    public CompoundShape clone() {
        CompoundShape copy = (CompoundShape) super.clone();
        copy.shapes = new ArrayList<>();
        for (Shape s : this.shapes) {
            Shape sCopy = s.clone();
            sCopy.setId();
            copy.add(sCopy);
        }
        return copy;
    }

    /**
     * Checks if all the subshapes have the same color
     *
     * @return the result of the test
     */
    public boolean sameColor() {
        boolean sameColor = true;
        int i = 1;
        String color = "";
        color = shapes.get(0).getColor();
        while (i < shapes.size() && sameColor) {
            if (color != shapes.get(i).getColor())
                sameColor = false;
            i++;
        }
        return sameColor;
    }

    /**
     * Checks if all the subshapes have the same rotation
     *
     * @return the result of the test
     */
    public boolean sameRotation() {
        boolean sameRotation = true;
        int i = 1;
        float rotation = -1;
        rotation = shapes.get(0).getRotation();
        while (i < shapes.size() && sameRotation) {
            if (rotation != shapes.get(i).getRotation())
                sameRotation = false;
            i++;
        }
        return sameRotation;
    }

    /**
     * Checks if all the subshapes have the same width (Rectangles only)
     *
     * @return the result of the test
     */
    public boolean sameWidth() {
        boolean isRectangle = true;
        boolean sameWidth = true;
        int i = 1;
        float width = -1;
        if (shapes.get(0) instanceof Rectangle) width = shapes.get(0).getWidth();
        while (isRectangle && i < shapes.size() && sameWidth) {
            if (shapes.get(i) instanceof Rectangle) {
                if (width != shapes.get(i).getWidth()) sameWidth = false;
            } else isRectangle = false;
            i++;
        }
        return sameWidth;
    }

    /**
     * Checks if all the subshapes have the same height (Rectangles only)
     *
     * @return the result of the test
     */
    public boolean sameHeight() {
        boolean isRectangle = true;
        boolean sameHeight = true;
        int i = 1;
        float height = -1;
        if (shapes.get(0) instanceof Rectangle) height = shapes.get(0).getHeight();
        while (isRectangle && i < shapes.size() && sameHeight) {
            if (shapes.get(i) instanceof Rectangle) {
                if (height != shapes.get(i).getHeight()) sameHeight = false;
            } else isRectangle = false;
            i++;
        }
        return sameHeight;
    }

    /**
     * Checks if all the subshapes have the same border radius (Rectangles only)
     *
     * @return the result of the test
     */
    public boolean sameBorderRadius() {
        boolean isRectangle = true;
        boolean sameBorderRadius = true;
        int i = 1;
        float borderRadius = -1;
        if (shapes.get(0) instanceof Rectangle) borderRadius = ((Rectangle) shapes.get(0)).getBorderRadius();
        while (isRectangle && i < shapes.size() && sameBorderRadius) {
            if (shapes.get(i) instanceof Rectangle) {
                if (borderRadius != ((Rectangle) shapes.get(i)).getBorderRadius()) sameBorderRadius = false;
            } else isRectangle = false;
            i++;
        }
        return sameBorderRadius;
    }

    /**
     * Checks if all the subshapes have the same number of edges (Polygon only)
     *
     * @return the result of the test
     */
    public boolean sameEdges() {
        boolean isPolygon = true;
        boolean sameEdges = true;
        int i = 1;
        int edges = -1;
        if (shapes.get(0) instanceof Polygon) edges = ((Polygon) shapes.get(0)).getEdges();
        while (isPolygon && i < shapes.size() && sameEdges) {
            if (shapes.get(i) instanceof Polygon) {
                if (edges != ((Polygon) shapes.get(i)).getEdges()) sameEdges = false;
            } else isPolygon = false;
            i++;
        }
        return sameEdges;
    }

    /**
     * Checks if all the subshapes have the same length (Polygon only)
     *
     * @return the result of the test
     */
    public boolean sameLength() {
        boolean isPolygon = true;
        boolean sameLength = true;
        int i = 1;
        float length = -1;
        if (shapes.get(0) instanceof Polygon) length = ((Polygon) shapes.get(0)).getLength();
        while (isPolygon && i < shapes.size() && sameLength) {
            if (shapes.get(i) instanceof Polygon) {
                if (length != ((Polygon) shapes.get(i)).getLength()) sameLength = false;
            } else isPolygon = false;
            i++;
        }
        return sameLength;
    }

}
