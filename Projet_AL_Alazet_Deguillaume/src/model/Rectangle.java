package model;

import java.io.Serializable;

public class Rectangle extends SingleShape implements Serializable {
    private float width;
    private float height;
    private float borderRadius;

    /**
     *  Creates a Rectangle
     * @param positionI the position of the top-left corner of the rectangle
     * @param rotation
     * @param rotationCenter
     * @param translation
     * @param color
     * @param width
     * @param height
     * @param borderRadius
     * @param implementor
     */
    public Rectangle(PositionI positionI, float rotation, PositionI rotationCenter, PositionI translation, String color, float width, float height, float borderRadius, Implementor implementor) {
        super(positionI, rotation, rotationCenter, translation, color, implementor);
        this.width = width;
        this.height = height;
        this.borderRadius = borderRadius;
    }

    /******************************
     *          GETTERS           *
     ******************************/
    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    /******************************
     *          SETTERS           *
     ******************************/
    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
    }


    /**
     * Compares a given Shape with this Rectangle
     * @param s the Shape to compare to this Rectangle
     * @return true if the given Rectangle has the same id false otherwise
     */
    @Override
    public boolean equals(Shape s) {
        if(s instanceof Rectangle && s.getId() == this.getId()) {
            return true;
        }
        return false;
    }

    /**
     * Clones the Rectangle
     * @return the copy of the Rectangle
     */
    @Override
    public Rectangle clone() {
        return (Rectangle) super.clone();
    }
}
