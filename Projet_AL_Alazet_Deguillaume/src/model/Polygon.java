package model;

import java.util.Vector;

public class Polygon extends SingleShape {
    private int edges;
    private float length;
    private Vector<Double> vertices;

    public Polygon(PositionI positionI, float rotation, PositionI rotationCenter, PositionI translation, String color, int edges, float length, Implementor implementor) {
        super(positionI, rotation, rotationCenter, translation, color, implementor);
        this.edges = edges;
        this.length = length;
        this.vertices = new Vector<>();
    }

    public int getEdges() {
        return edges;
    }

    public void setEdges(int edges) {
        this.edges = edges;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }


    public double computeRadius() {
        double angle_b = 2 * Math.PI / edges;
        Position a = new Position(Math.cos(0), Math.sin(0));
        Position b = new Position(Math.cos(angle_b), Math.sin(angle_b));

        return (length / a.distance(b));
    }

    public void computeVertices() {
        double r = computeRadius();
        vertices.clear();
        for (int i = 0; i < edges*2; i+=2) {
            double angle = i/2 * (2 * Math.PI / edges);
            vertices.add(r * Math.cos(angle) + getPositionI().getX() + r);
            vertices.add(r * Math.sin(angle) + getPositionI().getY() + r);
        }
    }

    public Vector<Double> getVertices() {
        return vertices;
    }

    @Override
    public boolean equals(Shape s) {
        if(s instanceof Polygon && s.getId() == this.getId()) {
            return true;
        }
        return false;
    }

    @Override
    public Polygon clone() {
        return (Polygon) super.clone();
    }

    @Override
    public float getWidth() {
        double maxX = vertices.get(0);
        double minX = vertices.get(0);

        for(int index = 0; index < edges; index += 2){
            if(vertices.get(index) > maxX) maxX = vertices.get(index);
            else if (vertices.get(index) < minX) minX = vertices.get(index);
        }
        return (float)(maxX-minX);
    }

    @Override
    public float getHeight(){
        double maxY = vertices.get(1);
        double minY = vertices.get(1);

        for(int index = 1; index < edges; index += 2){
            if(vertices.get(index) > maxY) maxY = vertices.get(index);
            else if (vertices.get(index) < minY) minY = vertices.get(index);
        }
        return (float)(maxY-minY);
    }
}
