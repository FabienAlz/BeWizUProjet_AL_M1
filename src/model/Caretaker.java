package model;

import java.util.ArrayList;
import java.util.List;

// This class saves the currents shapes and permits to do the undo/redo
public final class Caretaker {
    private int current;
    private static Caretaker instance;
    private List<Memento> mementoList = new ArrayList<Memento>();


    /**
     * Singleton constructor
     */
    private Caretaker() {
        this.current = -1;
    }

    /**
     * Singleton pattern
     * @return
     */
    public static Caretaker getInstance() {
        if(instance == null) {
            instance = new Caretaker();
        }
        return instance;
    }

    /**
     * Saves a memento and updates the current memento
     * @param state
     */
    public void add(Memento state){
        current ++;
        mementoList.add(state);
    }

    /**
     * Permits to get a Memento given its index in the list
     * @param index
     * @return the corresponding Memento
     */
    public Memento get(int index){
        if(index > current || index < 0) throw new IllegalArgumentException("The given index is out of bond");
        return mementoList.get(index);
    }

    /**
     * Returns the position in the list of the current Memento
     * @return
     */
    public int getCurrent() {
        return current;
    }

    /**
     * Decrements current
     */
    public void decrement() {
        if(current > 0) {
            current--;
        }
    }

    /**
     * Increments current
     */
    public void increment() {
        if(current < mementoList.size() - 1) {
            current++;
        }
    }

    /**
     * Removes all the Memento saved after the current one
     */
    public void remove() {
        List<Memento> toRemove = new ArrayList<>();
        for(int i = current + 1; i < mementoList.size(); i++) {
            toRemove.add(mementoList.get(i));
        }
        for(int i = 0; i < toRemove.size(); i++) {
            mementoList.remove(toRemove.get(i));
        }

    }

}