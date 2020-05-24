package model;

public interface Shape extends Cloneable {

    /******************************
     *          GETTERS           *
     ******************************/
    PositionI getPositionI();

    float getRotation();

    PositionI getTopLeft();

    PositionI getRotationCenter();

    Translation getTranslation();

    String getColor();

    Implementor getImplementor();

    boolean isSelected();

    long getId();

    float getWidth();

    float getHeight();


    /******************************
     *          SETTERS           *
     ******************************/

    void setPosition(PositionI position);

    void setRotation(float rotation);

    void setColor(String color);

    void setSelected(boolean b);

    void setImplementor(Implementor implementor);

    void setId();



    boolean isInside(Position startingPoint, Position arrival);

    boolean equals(Shape s);


    /**
     * Prototype method
     */
    Shape clone();


    /**
     * Observable methods
     */
    void addObserver(ShapeObserver obs);

    void removeObserver(ShapeObserver obs);

    void removeAllObservers();

    void notifyObserver();

}
