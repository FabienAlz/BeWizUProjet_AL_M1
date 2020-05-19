package model.mediatorFX;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Button extends javafx.scene.control.Button implements Component {

    public Button(String s, String imageSrc) {
        super(s);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imageSrc);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image img = new Image(fis);
        ImageView imageView = new ImageView(img);
        imageView.setPreserveRatio(true);
        setGraphic(imageView);
        setPrefSize(32, 32);
    }
}
