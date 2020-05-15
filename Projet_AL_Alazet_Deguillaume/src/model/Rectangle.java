package model;

public class Rectangle extends SingleShape {
    private float width;
    private float height;
    private float borderRadius;

    public Rectangle(PositionI positionI, float rotation, PositionI rotationCenter, PositionI translation, String color, float width, float height, float borderRadius, Implementor implementor) {
        super(positionI, rotation, rotationCenter, translation, color, implementor);
        this.width = width;
        this.height = height;
        this.borderRadius = borderRadius;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getBorderRadius() {
        return borderRadius;
    }


    @Override
    public boolean equals(Shape s) {
        if(s instanceof Rectangle && ((Rectangle) s).getId() == this.getId()) {
            return true;
        }
        return false;
    }

    @Override
    public Rectangle clone() {
        return (Rectangle) super.clone();
    }
}
