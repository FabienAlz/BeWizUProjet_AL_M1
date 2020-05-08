package model;

public final class ShapeFactory {
    public Shape createProduct(PositionI positionI, float rotation, PositionI rotationCenter, PositionI translation, String color, float width, float height, float borderRadius, Implementor implementor) {
        return new Rectangle(positionI, rotation, rotationCenter, translation, color, width, height, borderRadius, implementor);
    }

    public Shape createProduct(PositionI positionI, float rotation, PositionI rotationCenter, PositionI translation, String color, int edges, float length, Implementor implementor) {
        return new Polygon(positionI, rotation, rotationCenter, translation, color, edges, length, implementor);
    }
}
