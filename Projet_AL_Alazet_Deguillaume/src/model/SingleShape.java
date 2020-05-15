package model;

public abstract class SingleShape extends AbstractShape {
    private float rotation;
    private PositionI rotationCenter;
    private String color;

    public SingleShape(PositionI position, float rotation, PositionI rotationCenter, PositionI translation, String color, Implementor implementor) {
        super(position, translation, implementor);
        this.rotation = rotation;
        this.rotationCenter = rotationCenter;
        this.color = color;
    }

    public float getRotation() {
        return rotation;
    }

    public PositionI getRotationCenter() {
        return rotationCenter;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }


    @Override
    public SingleShape clone() {
            return (SingleShape) super.clone();
    }


}
