package model;

public class Rectangle extends AbstractShape {
    private float width;
    private float height;
    private float borderRadius;

    public Rectangle(Position position, float rotation, Position rotationCenter, float translation, String color, float width, float height, float borderRadius) {
        super(position, rotation, rotationCenter, translation, color);
        this.width = width;
        this.height = height;
        this.borderRadius = borderRadius;
    }
}
