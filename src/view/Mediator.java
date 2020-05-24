package view;

import model.Component;

public interface Mediator {
    void registerComponent(Component component);

    void createGUI();
}
