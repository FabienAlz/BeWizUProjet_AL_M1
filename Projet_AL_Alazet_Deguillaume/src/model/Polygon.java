package model;

public class Polygon extends AbstractShape {
    private int edges;
    private float length;

    public Polygon(Position position, float rotation, Position rotationCenter, Position translation, String color, int edges, float length) {
        super(position, rotation, rotationCenter, translation, color);
        this.edges = edges;
        this.length = length;
    }

    @Override
    public Polygon clone() {
        return (Polygon) super.clone();
    }
}
