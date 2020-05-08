package model;

public class Polygon extends AbstractShape {
    private int edges;
    private float length;

    public Polygon(PositionI positionI, float rotation, PositionI rotationCenter, PositionI translation, String color, int edges, float length, Implementor implementor) {
        super(positionI, rotation, rotationCenter, translation, color, implementor);
        this.edges = edges;
        this.length = length;
    }

    public int getEdges() {
        return edges;
    }

    public float getLength() {
        return length;
    }

    @Override
    public Polygon clone() {
        return (Polygon) super.clone();
    }
}
