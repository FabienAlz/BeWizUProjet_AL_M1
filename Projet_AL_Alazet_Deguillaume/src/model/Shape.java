package model;

public interface Shape {


    /**
     * Observable methods
     */

    public void addObserver(ShapeObserver obs);
    public void removeObserver(ShapeObserver obs);
    public void notifyObserver();

}
