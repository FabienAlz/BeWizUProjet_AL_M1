package model;

import java.io.Serializable;

public abstract class AbstractShape extends ObservableSuperClass implements Shape, Serializable {
    private static long generatedId = -1;
    private long id;
    private Implementor implementor;
    private PositionI position;
    private Translation translation;
    private boolean isSelected;

    /**
     * Initializes the fields of an AbstractShape
     *
     * @param position
     * @param translation
     * @param implementor
     */
    public AbstractShape(PositionI position, Translation translation, Implementor implementor) {
        this.implementor = implementor;
        this.position = position;
        this.translation = translation;
        this.isSelected = false;
        this.id = ++generatedId;
    }

    public AbstractShape() {
    }

    /******************************
     *           GETTERS          *
     ******************************/

    abstract public float getWidth();

    abstract public float getHeight();

    public PositionI getPositionI() {
        return position;
    }

    public Translation getTranslation() {
        return translation;
    }

    public Implementor getImplementor() {
        return this.implementor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public long getId() {
        return this.id;
    }

    /******************************
     *           SETTERS          *
     ******************************/
    public void setPosition(PositionI position) {
        this.position = position;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setId() {
        this.id = ++generatedId;
    }

    /**
     * Checks if the Shape is inside a Rectangle
     *
     * @param startingPoint
     * @param arrival
     * @return
     */
    public abstract boolean isInside(Position startingPoint, Position arrival);

    /**
     * Permits to do a rotation
     * @return
     */
    public Position rotate(PositionI origin, PositionI pivot, double rotation) {
        double radRotation = rotation%180 * Math.PI / 180;
        double arrivalX = (Math.cos(radRotation) * (origin.getX() - pivot.getX()) -
                Math.sin(radRotation) * (origin.getY() - pivot.getY()) +
                pivot.getX());
        double arrivalY = (Math.sin(radRotation) * (origin.getX() - pivot.getX()) +
                Math.cos(radRotation) * (origin.getY() - pivot.getY()) +
                pivot.getY());
        return new Position(arrivalX, arrivalY);
    }

    @Override
    public AbstractShape clone() {
        try {
            return (AbstractShape) super.clone();
        } catch (Exception e) {
        }
        return this;
    }

}
