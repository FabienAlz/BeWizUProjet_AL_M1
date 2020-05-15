package model;


public abstract class AbstractShape extends ObservableSuperClass implements Shape {
    private static long generatedId = 0;
    private long id;
    private Implementor implementor;
    private PositionI position;
    private PositionI translation;
    private boolean isSelected;

    public AbstractShape(PositionI position, PositionI translation, Implementor implementor) {
        this.implementor = implementor;
        this.position = position;
        this.translation = translation;
        this.isSelected = false;
        this.id = generatedId;
        generatedId ++;
    }

    public PositionI getPositionI() {
        return position;
    }

    public void setPosition(PositionI position) { this.position = position; }

    public PositionI getTranslation() {
        return translation;
    }

    public void setTranslation(PositionI translation) {
        this.translation = translation;
    }


    public Implementor getImplementor() {
        return this.implementor;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getId() { return this.id; }

    public void setId() { this.id = ++generatedId; }

    abstract public float getWidth();

    abstract  public float getHeight();

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
