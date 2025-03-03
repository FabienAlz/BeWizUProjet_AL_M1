package model;

import java.io.Serializable;
import java.util.Vector;

public class Polygon extends SingleShape implements Serializable {
    private int edges;
    private float length;
    private Vector<Double> vertices;

    /**
     * Creates a new Polygon
     *
     * @param positionI
     * @param rotation
     * @param translation
     * @param color
     * @param edges
     * @param length
     * @param implementor
     */
    public Polygon(PositionI positionI, float rotation, Translation translation, String color, int edges, float length, Implementor implementor) {
        super(positionI, rotation, translation, color, implementor);
        this.edges = edges;
        this.length = length;
        this.vertices = new Vector<>();
        computeVertices();
        computeRotationCenter();
    }

    /******************************
     *          GETTERS           *
     ******************************/
    public int getEdges() {
        return edges;
    }

    public float getLength() {
        return length;
    }

    @Override
    public Position getTopLeft() {
        double minX = vertices.get(0);
        double minY = vertices.get(1);
        for(int i = 0; i < vertices.size(); i += 2) {
            if(minX > vertices.get(i)) minX = vertices.get(i);
            if(minY > vertices.get(i+1)) minY = vertices.get(i+1);
        }
        return new Position(minX, minY);
    }

    @Override
    public Vector<Double> getVertices() {
        return vertices;
    }

    @Override
    public float getWidth() {
        double maxX = vertices.get(0);
        double minX = vertices.get(0);
        for (int index = 0; index < edges * 2; index += 2) {
            if (vertices.get(index) > maxX) maxX = vertices.get(index);
            else if (vertices.get(index) < minX) minX = vertices.get(index);
        }
        return (float) (maxX - minX);
    }

    @Override
    public float getHeight() {
        double maxY = vertices.get(1);
        double minY = vertices.get(1);
        for (int index = 1; index < edges * 2; index += 2) {
            if (vertices.get(index) > maxY) maxY = vertices.get(index);
            if (vertices.get(index) < minY) minY = vertices.get(index);
        }
        return (float) (maxY - minY);
    }

    /******************************
     *          SETTERS           *
     ******************************/
    public void setEdges(int edges) {
        this.edges = edges;
    }

    public void setLength(float length) {
        this.length = length;
    }


    /**
     * Computes the radius of the circle on witch all the vertices of the Polygon are
     *
     * @return the radius of this circle
     */
    public double computeRadius() {
        double angle_b = 2 * Math.PI / edges;
        Position a = new Position(Math.cos(0), Math.sin(0));
        Position b = new Position(Math.cos(angle_b), Math.sin(angle_b));

        return (length / a.distance(b));
    }

    /**
     * Computes the vertices of a Polygon with the number of its edges and their length
     */
    @Override
    public void computeVertices() {
        double r = computeRadius();
        vertices.clear();
        for (int i = 0; i < edges * 2; i += 2) {
            double angle = i / 2 * (2 * Math.PI / edges);
            vertices.add(r * Math.cos(angle) + getPositionI().getX() + r);
            vertices.add(r * Math.sin(angle) + getPositionI().getY() + r);
        }
    }

    /**
     * Computes the rotation center of a Polygon
     */
    @Override
    public void computeRotationCenter() {
        double posX = 0;
        double posY = 0;
        for(int i = 0; i < edges*2; i++) {
            if(i%2 == 0) {
                posX += vertices.get(i);
            }
            else {
                posY += vertices.get(i);
            }
        }
        setRotationCenter(new Position(posX/edges, posY/edges));
    }

    /**
     * Checks if the Shape is inside a Rectangle
     * @param startingPoint
     * @param arrival
     * @return
     */
    @Override
    public boolean isInside(Position startingPoint, Position arrival) {
        for(int i = 0; i < vertices.size(); i+=2) {
            if (!(vertices.get(i) > startingPoint.getX() &&
                    vertices.get(i) < arrival.getX() &&
                    vertices.get(i+1) > startingPoint.getY() &&
                    vertices.get(i+1) < arrival.getY())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Compares a given Shape with this Polygon
     *
     * @param s the Shape to compare to this Polygon
     * @return true if they have the same id, false otherwise
     */
    @Override
    public boolean equals(Shape s) {
        if (s instanceof Polygon && s.getId() == this.getId()) {
            return true;
        }
        return false;
    }

    /**
     * Clones a Polygon and deep copies its array of vertices
     *
     * @return a copy of this Polygon
     */
    @Override
    public Polygon clone() {
        Polygon copy = (Polygon) super.clone();
        copy.vertices = new Vector<>();
        Vector<Double> copyVector = (Vector<Double>) vertices.clone();
        copy.vertices.addAll(copyVector);
        return copy;
    }
}
