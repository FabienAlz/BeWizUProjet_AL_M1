package model;

import java.awt.*;

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

    public double computeRadius() {
        double angle_b = 2 * Math.PI / edges;
        Position a = new Position(Math.cos(0), Math.sin(0));
        Position b = new Position(Math.cos(angle_b), Math.sin(angle_b));

        return (length / a.distance(b));
    }

    public double[] getPoints() {

            double[] points = new double[edges * 2];
            /*double angle_b = 2 * Math.PI / edges;
            Position a = new Position(Math.cos(0), Math.sin(0));
            Position b = new Position(Math.cos(angle_b), Math.sin(angle_b));

            double r = (length / a.distance(b));*/

            double r = computeRadius();

        for (int i = 0; i < edges*2; i+=2) {
            double angle = i/2 * (2 * Math.PI / edges);
            points[i] = r * Math.cos(angle) + getPositionI().getX() + r;
            points[i + 1] = r * Math.sin(angle) + getPositionI().getY() + r;

        }
        return points;
    }

    @Override
    public Polygon clone() {
        return (Polygon) super.clone();
    }
}
