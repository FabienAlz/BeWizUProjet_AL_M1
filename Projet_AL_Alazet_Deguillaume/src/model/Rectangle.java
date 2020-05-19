package model;

import java.io.Serializable;
import java.util.Vector;

public class Rectangle extends SingleShape implements Serializable {
    private static final long serialVersionUID = 2467016630396039571L;

    private float width;
    private float height;
    private float borderRadius;
    private Vector<Double> vertices;


    /**
     * Creates a Rectangle
     *
     * @param position the position of the top-left corner of the rectangle
     * @param rotation
     * @param translation
     * @param color
     * @param width
     * @param height
     * @param borderRadius
     * @param implementor
     */
    public Rectangle(PositionI position, float rotation, Translation translation, String color, float width, float height, float borderRadius, Implementor implementor) {
        super(position, rotation, translation, color, implementor);
        this.width = width;
        this.height = height;
        this.borderRadius = borderRadius;
        vertices = new Vector<>();
        computeRotationCenter();
        computeVertices();
    }

    /******************************
     *          GETTERS           *
     ******************************/
    @Override
    public float getWidth() {
        return width;
    }

    @Override
    public float getHeight(){
        return height;
    }

    @Override
    public Vector<Double> getVertices() {
        return vertices;
    }

    public float getAppearingWidth() {
        double maxX = vertices.get(0);
        double minX = vertices.get(0);
        for(int index = 0; index < 8; index += 2){
            if(vertices.get(index) > maxX) maxX = vertices.get(index);
            else if (vertices.get(index) < minX) minX = vertices.get(index);
        }
        return (float)(maxX-minX);
    }

    public float getAppearingHeight(){
        double maxY = vertices.get(1);
        double minY = vertices.get(1);
        for(int index = 1; index < 8; index += 2){
            if(vertices.get(index) > maxY) maxY = vertices.get(index);
            if (vertices.get(index) < minY) minY = vertices.get(index);
        }
        return (float)(maxY-minY);
    }

    @Override
    public Position getTopLeft() {
        double minX = vertices.get(0);
        double minY = vertices.get(1);
        for(int i = 0; i < 8; i += 2) {
            if(minX > vertices.get(i)) minX = vertices.get(i);
            if(minY > vertices.get(i+1)) minY = vertices.get(i+1);
        }
        return new Position(minX, minY);
    }

    public float getBorderRadius() {
        return borderRadius;
    }

    /******************************
     *          SETTERS           *
     ******************************/
    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setBorderRadius(float borderRadius) {
        this.borderRadius = borderRadius;
    }

    @Override
    public void setPosition(PositionI position) {
        super.setPosition(position);
        computeRotationCenter();
        computeVertices();
    }

    /**
     * Computes the rotation center of a Rectangle
     */
    @Override
    public void computeRotationCenter() {
        setRotationCenter(new Position(getPositionI().getX() + width/2, getPositionI().getY() + height/2));
    }

    /**
     * Computes the vertices of a Rectangle
     */
    @Override
    public void computeVertices() {
        vertices.clear();
        // Initialize the vertices
        Position firstVertex = new Position(getPositionI().getX(), getPositionI().getY());
        Position secondVertex = new Position(getPositionI().getX() + width, getPositionI().getY());
        Position thirdVertex = new Position(getPositionI().getX(), getPositionI().getY() + height);
        Position fourthVertex = new Position(getPositionI().getX() + width, getPositionI().getY() + height);
        vertices.add(rotate(firstVertex, getRotationCenter(), getRotation()).getX());
        vertices.add(rotate(firstVertex, getRotationCenter(), getRotation()).getY());

        vertices.add(rotate(secondVertex, getRotationCenter(), getRotation()).getX());
        vertices.add(rotate(secondVertex, getRotationCenter(), getRotation()).getY());

        vertices.add(rotate(thirdVertex, getRotationCenter(), getRotation()).getX());
        vertices.add(rotate(thirdVertex, getRotationCenter(), getRotation()).getY());

        vertices.add(rotate(fourthVertex, getRotationCenter(), getRotation()).getX());
        vertices.add(rotate(fourthVertex, getRotationCenter(), getRotation()).getY());
    }

    /**
     * Checks if the Shape is inside a Rectangle
     * @param startingPoint
     * @param arrival
     * @return
     */
    @Override
    public boolean isInside(Position startingPoint, Position arrival) {
        for(int i = 0; i < 8; i+=2) {
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
     * Compares a given Shape with this Rectangle
     *
     * @param s the Shape to compare to this Rectangle
     * @return true if the given Rectangle has the same id false otherwise
     */
    @Override
    public boolean equals(Shape s) {
        if (s instanceof Rectangle && s.getId() == this.getId()) {
            return true;
        }
        return false;
    }

    /**
     * Clones the Rectangle
     *
     * @return the copy of the Rectangle
     */
    @Override
    public Rectangle clone() {
        Rectangle copy = (Rectangle) super.clone();
        copy.vertices = new Vector<>();
        Vector<Double> copyVector = (Vector<Double>)vertices.clone();
        copy.vertices.addAll(copyVector);
        return copy;
    }
}
