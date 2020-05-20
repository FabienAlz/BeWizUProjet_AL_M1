package model.mediatorFX;

import javafx.scene.paint.Color;
import model.Component;
import view.Mediator;

public class ColorPicker extends javafx.scene.control.ColorPicker implements Component {
    private Mediator mediator;

    public ColorPicker(Color color) {
        super();
        setValue(color);
    }

    public ColorPicker() {
        super();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "ColorPicker";
    }

}
