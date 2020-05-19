package model;

import view.Mediator;

public interface Component {
    void setMediator(Mediator mediator);

    String getName();
}
