package model;

public interface Shape extends Cloneable {

    /**
     * Observable methods
     */
    public void addObserver(ShapeObserver obs);
    public void removeObserver(ShapeObserver obs);
    public void notifyObserver();

}
