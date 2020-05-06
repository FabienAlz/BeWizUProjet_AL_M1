package model;

public final class ShapeFactory {
    public Shape createProduct(Position position, float rotation, Position rotationCenter, Position translation, String color, float width, float height, float borderRadius, Implementor implementor) {
        return new Rectangle(position, rotation, rotationCenter, translation, color, width, height, borderRadius, implementor);
    }

    public Shape createProduct(Position position, float rotation, Position rotationCenter, Position translation, String color, int edges, float length, Implementor implementor) {
        return new Polygon(position, rotation, rotationCenter, translation, color, edges, length, implementor);
    }
}
