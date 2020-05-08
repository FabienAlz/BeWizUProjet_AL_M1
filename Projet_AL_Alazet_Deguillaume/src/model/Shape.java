package model;

public interface Shape extends Cloneable {


    public PositionI getPositionI();

    public void setPosition(PositionI positionI);

    public float getRotation();

    public PositionI getRotationCenter();

    public PositionI getTranslation();

    public String getColor();

    public Implementor getImplementor();

    /**
     * Observable methods
     */
    public void addObserver(ShapeObserver obs);
    public void removeObserver(ShapeObserver obs);
    public void removeAllObservers();
    public void notifyObserver();

}
