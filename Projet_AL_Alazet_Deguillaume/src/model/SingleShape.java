package model;

public abstract class SingleShape extends AbstractShape {
    private float rotation;
    private PositionI rotationCenter;
    private String color;

    /**
     * Initializes a SingleShape's fields
     * @param position
     * @param rotation
     * @param rotationCenter
     * @param translation
     * @param color
     * @param implementor
     */
    public SingleShape(PositionI position, float rotation, PositionI rotationCenter, PositionI translation, String color, Implementor implementor) {
        super(position, translation, implementor);
        this.rotation = rotation;
        this.rotationCenter = rotationCenter;
        this.color = color;
    }

    public SingleShape(){}

    /******************************
     *          GETTERS           *
     ******************************/
    public float getRotation() {
        return rotation;
    }

    public PositionI getRotationCenter() {
        return rotationCenter;
    }

    public String getColor() {
        return color;
    }

    /******************************
     *          SETTERS           *
     ******************************/
    public void setColor(String color) {
        this.color = color;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    @Override
    public SingleShape clone() {
            return (SingleShape) super.clone();
    }


}
