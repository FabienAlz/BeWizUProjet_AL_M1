package model;

public final class ShapeFactory {
    public Shape createProduct(PositionI position, float rotation, PositionI rotationCenter, Translation translation, String color, float width, float height, float borderRadius, Implementor implementor) {
        return new Rectangle(position, rotation, rotationCenter, translation, color, width, height, borderRadius, implementor);
    }

    public Shape createProduct(PositionI position, float rotation, PositionI rotationCenter, Translation translation, String color, int edges, float length, Implementor implementor) {
        return new Polygon(position, rotation, rotationCenter, translation, color, edges, length, implementor);
    }
}
