package model;

public class Polygon extends AbstractShape {
    private int edges;
    private float length;

    public Polygon(Position position, float rotation, Position rotationCenter, Position translation, String color, int edges, float length, Implementor implementor) {
        super(position, rotation, rotationCenter, translation, color, implementor);
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
