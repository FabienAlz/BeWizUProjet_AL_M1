package model;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class DragAndDropHandlers {
    EventHandler<MouseEvent> updateSelectionRectangle = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (Canvas.getInstance().getSelection()) {
                Position firstPos = Canvas.getInstance().getStartSelectPos();
                // top-left to bottom-right
                if (e.getX() > firstPos.getX() && e.getY() > firstPos.getY()) {
                    rectangleSelection.setX(firstPos.getX());
                    rectangleSelection.setY(firstPos.getY());
                    rectangleSelection.setWidth(e.getX() - firstPos.getX());
                    rectangleSelection.setHeight(e.getY() - firstPos.getY());

                }
                // bottom-right to top-left
                else if (e.getX() < firstPos.getX() && e.getY() < firstPos.getY()) {
                    rectangleSelection.setX(e.getX());
                    rectangleSelection.setY(e.getY());
                    rectangleSelection.setWidth(firstPos.getX() - e.getX());
                    rectangleSelection.setHeight(firstPos.getY() - e.getY());
                }
                // bottom-left to top-right
                else if (e.getX() > firstPos.getX() && e.getY() < firstPos.getY()) {
                    rectangleSelection.setX(firstPos.getX());
                    rectangleSelection.setY(e.getY());
                    rectangleSelection.setWidth(e.getX() - firstPos.getX());
                    rectangleSelection.setHeight(firstPos.getY() - e.getY());
                }
                // top-right to bottom-left
                else if (e.getX() < firstPos.getX() && e.getY() > firstPos.getY()) {
                    rectangleSelection.setX(e.getX());
                    rectangleSelection.setY(firstPos.getY());
                    rectangleSelection.setWidth(firstPos.getX() - e.getX());
                    rectangleSelection.setHeight(e.getY() - firstPos.getY());
                }
            }
        }
    };
}
