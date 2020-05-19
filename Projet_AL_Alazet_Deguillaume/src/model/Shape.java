package model;

public interface Shape extends Cloneable {


    PositionI getPositionI();

    void setPosition(PositionI position);

    float getRotation();

    PositionI getTopLeft();

    void setRotation(float rotation);

    void setColor(String color);

    PositionI getRotationCenter();

    Translation getTranslation();

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

    boolean isInside(Position startingPoint, Position arrival);

    /**
     * Observable methods
     */
    void addObserver(ShapeObserver obs);
    void removeObserver(ShapeObserver obs);
    void removeAllObservers();
    void notifyObserver();

}
