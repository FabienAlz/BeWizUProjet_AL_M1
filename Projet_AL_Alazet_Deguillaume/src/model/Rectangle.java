package model;

public class Rectangle extends AbstractShape {
    private float width;
    private float height;
    private float borderRadius;

    public Rectangle(Position position, float rotation, Position rotationCenter, Position translation, String color, float width, float height, float borderRadius, Implementor implementor) {
        super(position, rotation, rotationCenter, translation, color, implementor);
        this.width = width;
        this.height = height;
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
    public Rectangle clone() {
        return (Rectangle) super.clone();
    }
}
