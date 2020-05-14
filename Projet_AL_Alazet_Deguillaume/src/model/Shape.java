package model;

public interface Shape extends Cloneable {


    PositionI getPositionI();

    void setPosition(PositionI positionI);

    float getRotation();

    PositionI getRotationCenter();

    PositionI getTranslation();

    String getColor();

    Implementor getImplementor();

    boolean isSelected();

    void setSelected(boolean b);

    Shape clone();

    boolean equals(Shape s);

    void setId();

    long getId();

    float getWidth();

    float getHeight();


    /**
     * Observable methods
     */
    void addObserver(ShapeObserver obs);
    void removeObserver(ShapeObserver obs);
    void removeAllObservers();
    void notifyObserver();

}
