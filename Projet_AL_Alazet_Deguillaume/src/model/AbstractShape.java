package model;

public abstract class AbstractShape extends ObservableSuperClass implements Shape {
    private static long generatedId = 0;
    private long id;
    private Implementor implementor;
    private PositionI position;
    private PositionI translation;
    private boolean isSelected;

    /**
     * Initializes the fields of an AbstractShape
     * @param position
     * @param translation
     * @param implementor
     */
    public AbstractShape(PositionI position, PositionI translation, Implementor implementor) {
        this.implementor = implementor;
        this.position = position;
        this.translation = translation;
        this.isSelected = false;
        this.id = ++generatedId;
    }

    public AbstractShape(){}

    /******************************
     *           GETTERS          *
     ******************************/

    abstract public float getWidth();

    abstract  public float getHeight();

    public PositionI getPositionI() {
        return position;
    }

    public PositionI getTranslation() {
        return translation;
    }

    public Implementor getImplementor() {
        return this.implementor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public long getId() { return this.id; }

    /******************************
     *           SETTERS          *
     ******************************/
    public void setPosition(PositionI position) { this.position = position; }

    public void setTranslation(PositionI translation) {
        this.translation = translation;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setId() {
        this.id = ++generatedId; }

    /**
     * Checks if the Shape is inside a Rectangle
     * @param startingPoint
     * @param arrival
     * @return
     */
    public boolean isInside(Position startingPoint, Position arrival) {
        boolean res = false;

        if(position.getX() > startingPoint.getX() &&
                position.getX() + getWidth() < arrival.getX() &&
                position.getY() > startingPoint.getY() &&
                position.getY() + getHeight() < arrival.getY()){
            res = true;
        }

        return res;
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
