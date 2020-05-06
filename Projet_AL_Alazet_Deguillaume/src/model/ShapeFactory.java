package model;

public final class ShapeFactory {
    public Shape createProduct(Position position, float rotation, Position rotationCenter, Position translation, String color, float width, float height, float borderRadius) {
        return new Rectangle(position, rotation, rotationCenter, translation, color, width, height, borderRadius);
    }

    public Shape createProduct(Position position, float rotation, Position rotationCenter, Position translation, String color, int edges, float length) {
        return new Polygon(position, rotation, rotationCenter, translation, color, edges, length);
    }
}
