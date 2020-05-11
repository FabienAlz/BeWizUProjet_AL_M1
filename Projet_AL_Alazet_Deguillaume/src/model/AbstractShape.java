package model;

public abstract class AbstractShape extends ObservableSuperClass implements Shape {

    private PositionI positionI;
    private float rotation;
    private PositionI rotationCenter;
    private PositionI translation;
    private String color;
    private Implementor implementor;
    private float ratio;
    private boolean isSelected;

    public AbstractShape(PositionI positionI, float rotation, PositionI rotationCenter, PositionI translation, String color, Implementor implementor) {
        this.positionI = positionI;
        this.rotation = rotation;
        this.rotationCenter = rotationCenter;
        this.translation = translation;
        this.color = color;
        this.implementor = implementor;
        this.ratio = 1;
        this.isSelected = false;
    }

    public PositionI getPositionI() {
        return positionI;
    }

    public void setPosition(PositionI positionI) { this.positionI = positionI; }

    public float getRotation() {
        return rotation;
    }

    public PositionI getRotationCenter() {
        return rotationCenter;
    }

    public PositionI getTranslation() {
        return translation;
    }

    public String getColor() {
        return color;
    }

    public Implementor getImplementor() {
        return this.implementor;
    }

    public float getRatio() { return this.ratio; }

    public void setRatio(float ratio) { this.ratio = ratio; }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
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
