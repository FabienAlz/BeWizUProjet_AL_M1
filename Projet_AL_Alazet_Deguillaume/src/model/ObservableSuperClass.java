package model;

import java.util.HashSet;
import java.util.Set;

public class ObservableSuperClass implements Shape {
    private Set<ShapeObserver> observers = new HashSet<>();
    private Implementor implementor;

    public ObservableSuperClass(Implementor implementor) {
        this.implementor = implementor;
    }

    public Implementor getImplementor() {
        return implementor;
    }

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
        for(ShapeObserver so : observersCp) {
            so.update();
        }
    }
}
