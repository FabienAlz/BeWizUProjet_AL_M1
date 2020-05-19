package model;

import java.io.Serializable;
import java.util.Vector;

public abstract class SingleShape extends AbstractShape implements Serializable {
    private static final long serialVersionUID = -6514750722141852843L;

    private float rotation;
    private PositionI rotationCenter;
    private String color;

    /**
     * Initializes a SingleShape's fields
     *
     * @param position
     * @param rotation
     * @param translation
     * @param color
     * @param implementor
     */
    public SingleShape(PositionI position, float rotation, Translation translation, String color, Implementor implementor) {
        super(position, translation, implementor);
        this.rotation = rotation;
        this.color = color;
    }

    public SingleShape() {
    }

    /******************************
     *          GETTERS           *
     ******************************/
    public float getRotation() {
        return rotation;
    }

    public PositionI getRotationCenter() {
        return rotationCenter;
    }

    public String getColor() {
        return color;
    }

    /******************************
     *          SETTERS           *
     ******************************/
    public void setColor(String color) {
        this.color = color;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
        computeVertices();
    }

    public void setRotationCenter(PositionI rotationCenter) {
        this.rotationCenter = rotationCenter;
    }

    public abstract void computeVertices();

    public abstract void computeRotationCenter();

    public abstract Vector<Double> getVertices();

    @Override
    public SingleShape clone() {
        return (SingleShape) super.clone();
    }


}
