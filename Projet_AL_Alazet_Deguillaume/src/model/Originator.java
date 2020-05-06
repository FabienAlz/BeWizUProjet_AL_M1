package model;

public class Originator {
    private Shape state;

    public void setState(Shape state){
        this.state = state;
    }

    public Shape getState(){
        return state;
    }

    public Memento saveStateToMemento(){
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento){
        state = memento.getState();
    }

}
