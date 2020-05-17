package model;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import view.Mediator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ColorPicker extends javafx.scene.control.ColorPicker implements Component {
    private Mediator mediator;

    public ColorPicker(Color color) {
        super();
        setValue(color);
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
