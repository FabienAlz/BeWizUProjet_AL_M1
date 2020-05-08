package model;

import java.util.HashSet;
import java.util.Set;

public abstract class ObservableSuperClass implements Shape {
    private Set<ShapeObserver> observers = new HashSet<>();

    @Override
    public void addObserver(ShapeObserver obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(ShapeObserver obs) {
        observers.remove(obs);
    }

    @Override
    public void removeAllObservers() {
        observers.clear();
    }

    @Override
    public void notifyObserver() {
        Set<ShapeObserver> observersCp = new HashSet<>();
        observersCp.addAll(observers);
        for(ShapeObserver so : observersCp) {
            so.update(this);
        }
    }
}
