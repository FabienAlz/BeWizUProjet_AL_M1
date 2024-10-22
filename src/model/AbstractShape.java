package model;

import java.io.Serializable;
import java.util.Objects;

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

    public void setImplementor(Implementor implementor) {
        this.implementor = implementor;
    }

    public void setId() {
        this.id = ++generatedId;
    }

    /**
     * Checks if the Shape is inside a Rectangle
     *
     * @param startingPoint
     * @param arrival
     * @return if the Shape is inside a Rectangle
     */
    public abstract boolean isInside(Position startingPoint, Position arrival);

    @Override
    public AbstractShape clone() {
        try {
            return (AbstractShape) super.clone();
        } catch (Exception e) {
        }
        return this;
    }

    /**
     * Compares this with o with their id
     *
     * @param o the object to compare this to
     * @return if this is equal to o
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractShape)) return false;
        AbstractShape that = (AbstractShape) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
