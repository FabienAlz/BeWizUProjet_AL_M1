package model;

import java.util.HashSet;
import java.util.Set;

public class ObservableSuperClass implements Shape {
    Set<ShapeObserver> observers = new HashSet<>();

    @Override
    public void addObserver(ShapeObserver obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(ShapeObserver obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObserver() {
        Set<ShapeObserver> observersCp = new HashSet<>();
        observersCp.addAll(observers);
        for(ShapeObserver uo : observersCp) {
            uo.update(this);
        }
    }
}
