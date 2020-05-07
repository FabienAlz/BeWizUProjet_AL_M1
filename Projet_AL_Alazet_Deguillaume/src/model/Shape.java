package model;

public interface Shape extends Cloneable {


    public Position getPosition();

    public float getRotation();

    public Position getRotationCenter();

    public Position getTranslation();

    public String getColor();

    public Implementor getImplementor();

    /**
     * Observable methods
     */
    public void addObserver(ShapeObserver obs);
    public void removeObserver(ShapeObserver obs);
    public void notifyObserver();

}
