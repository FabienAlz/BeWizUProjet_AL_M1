package model;

import java.util.ArrayList;
import java.util.List;

public class CompoundShape extends AbstractShape {
    private List<Shape> shapes;

    /**
     * Creates a CompoundShape
     * @param implementor
     * @param position
     */
    public CompoundShape(Implementor implementor, PositionI position) {
        super(position, new Position(0, 0), implementor);
        this.shapes = new ArrayList<>();
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
        double minX = getTopLeft().getX();
        double maxX = 0;
        for(Shape s : shapes) {
            if(maxX < s.getPositionI().getX() + s.getWidth()) maxX = s.getPositionI().getX() + s.getWidth();
        }
        return (float)(maxX-minX);
    }

    public float getHeight() {
        double minY = shapes.get(0).getPositionI().getY();
        double maxY = shapes.get(0).getPositionI().getY() + shapes.get(0).getHeight() ;
        for(Shape s : shapes) {
            if(minY > s.getPositionI().getY()) minY = s.getPositionI().getY();
            if(maxY < s.getPositionI().getY() + s.getHeight()) maxY = s.getPositionI().getY() + s.getHeight();
        }
        return (float)(maxY-minY);
    }

    public Position getTopLeft() {
        double minX = shapes.get(0).getPositionI().getX();
        double minY = shapes.get(0).getPositionI().getY();
        for(Shape s : shapes) {
            if(minX > s.getPositionI().getX()) minX = s.getPositionI().getX();
            if(minY > s.getPositionI().getY()) minY = s.getPositionI().getY();
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
        for(Shape s : shapes) {
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
     * Translate all the shapes of the CompoundShape to their location + the translation
     * @param translation
     */
    public void translate(Position translation) {
        CanvasPosition compoundPos = new CanvasPosition(getTopLeft().getX() + translation.getX(),
                                                        getTopLeft().getY() + translation.getY());
        this.setTranslation(translation);
        this.setPosition(compoundPos);
        for(Shape shape : shapes) {
            double posX = shape.getPositionI().getX() + translation.getX();
            double posY = shape.getPositionI().getY() + translation.getY();
            shape.setPosition(new CanvasPosition(posX, posY));
        }
    }

    /**
     * Compares a Shape with this CompoundShape using the Shapes they're composed of
     * @param s the Shape to compare
     * @return
     */
    @Override
    public boolean equals(Shape s) {
        if(s instanceof CompoundShape) {
            for(int index = 0; index < shapes.size(); index++) {
                if(!shapes.get(index).equals(((CompoundShape) s).getShapes().get(index))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Add a Shape to the CompoundShape
     * @param s
     */
    public void add(Shape s) {
        // Computes the top-left position
        if(shapes.isEmpty()) {
            this.getPositionI().setX(s.getPositionI().getX());
            this.getPositionI().setY(s.getPositionI().getY());
        }
        else {
            if(s.getPositionI().getX() < this.getPositionI().getX()) {
                this.getPositionI().setX(s.getPositionI().getX());
            }
            if(s.getPositionI().getY() < this.getPositionI().getY()) {
                this.getPositionI().setY(s.getPositionI().getY());
            }
        }
        // Add all the Shapes of the CompoundShape to this one
        if(s instanceof CompoundShape) {
            for (Shape shape : ((CompoundShape) s).getShapes()) {
                shape.setSelected(false);
                shapes.add(shape);
            }
        }
        else {
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
     * @return a copy of this CompoundShape
     */
    @Override
    public CompoundShape clone() {
        CompoundShape copy = (CompoundShape)super.clone();
        copy.shapes = new ArrayList<>();
        for(Shape s : this.shapes) {
            Shape sCopy = s.clone();
            sCopy.setId();
            copy.add(sCopy);
        }
        return copy;
    }

}
