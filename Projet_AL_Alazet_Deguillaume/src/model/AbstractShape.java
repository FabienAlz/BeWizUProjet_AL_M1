package model;

public abstract class AbstractShape extends ObservableSuperClass implements Shape {

    private Position position;
    private float rotation;
    private Position rotationCenter;
    private Position translation;
    private String color;
    private Implementor implementor;

    public AbstractShape(Position position, float rotation, Position rotationCenter, Position translation, String color, Implementor implementor) {
        this.position = position;
        this.rotation = rotation;
        this.rotationCenter = rotationCenter;
        this.translation = translation;
        this.color = color;
        this.implementor = implementor;
    }

    public Position getPosition() {
        return position;
    }

    public float getRotation() {
        return rotation;
    }

    public Position getRotationCenter() {
        return rotationCenter;
    }

    public Position getTranslation() {
        return translation;
    }

    public String getColor() {
        return color;
    }

    @Override
    public AbstractShape clone() {
        try {
            return (AbstractShape) super.clone();
        } catch (Exception e) {
        }
        return this;
    }
}
