package test;

import junit.framework.TestCase;
import model.*;

public class Test extends TestCase {
    Canvas canvas = Canvas.getInstance();
    FXImplementor implementor = FXImplementor.getInstance();

    public void testPosition() {
        // Checks that you can't get a momento that doesn't exist
        boolean thrown = false;
        PositionI position;
        try {
            position = new Position(0, -1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            position = new Position(-1, 0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            position = new CanvasPosition(-1, 0);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;
        try {
            position = new CanvasPosition(0, -1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    public void testComposite() {
        // Initialization
        ConcreteShapeObserver observer = new ConcreteShapeObserver();
        Rectangle rectangle = new Rectangle(new CanvasPosition(50, 50), 0, new Position(0,0), new Translation(0,0), "#4472c4", 30, 20, 0, implementor);
        Polygon polygon = new Polygon(new CanvasPosition(100,100), 0, new Position(0,0), new Translation(0,0), "#4472c4", 5, 100, implementor);
        CompoundShape compoundShape = new CompoundShape(implementor, new CanvasPosition(0, 0));
        rectangle.addObserver(observer);
        polygon.addObserver(observer);
        canvas.add(rectangle);
        canvas.add(polygon);
        assertEquals(2, canvas.getShapes().size());

        // Checks id
        assertEquals(0, rectangle.getId());
        assertEquals(1, polygon.getId());
        assertEquals(2, compoundShape.getId());

        compoundShape.add(rectangle);
        compoundShape.add(polygon);

        assertEquals(true, compoundShape.getShapes().contains(rectangle));
        assertEquals(true, compoundShape.getShapes().contains(polygon));

        // Checks CompoundShape's methods
        compoundShape.translate(new Translation(10, 10));
        assertEquals(60.0, rectangle.getPositionI().getX());
        assertEquals(60.0, rectangle.getPositionI().getY());
        assertEquals(110.0, polygon.getPositionI().getX());
        assertEquals(110.0, polygon.getPositionI().getY());

        compoundShape.setSelected(true);
        assertEquals(true, rectangle.isSelected());
        assertEquals(true, polygon.isSelected());

        // Checks size
        float heightDiff = (float)(polygon.getPositionI().getY() - (rectangle.getPositionI().getY() + rectangle.getHeight()));
        assertEquals(rectangle.getHeight() + polygon.getHeight() + heightDiff, compoundShape.getHeight());
        float widthDiff = (float)(polygon.getPositionI().getX() - (rectangle.getPositionI().getX() + rectangle.getWidth()));
        assertEquals(rectangle.getWidth() + polygon.getWidth() + widthDiff, compoundShape.getWidth());

        compoundShape.remove(rectangle);
        assertEquals(1, compoundShape.getShapes().size());
        assertEquals(polygon, compoundShape.getShapes().get(0));
    }

    public void testMemento() {
        // Initialization
        Caretaker caretaker = Caretaker.getInstance();
        ConcreteShapeObserver observer = new ConcreteShapeObserver();
        Rectangle rectangle = new Rectangle(new CanvasPosition(50, 50), 0, new Position(0,0), new Translation(0,0), "#4472c4", 30, 20, 0, implementor);
        Polygon polygon = new Polygon(new CanvasPosition(100,100), 0, new Position(0,0), new Translation(0,0), "#4472c4", 5, 100, implementor);
        rectangle.addObserver(observer);
        polygon.addObserver(observer);
        canvas.add(rectangle);
        canvas.add(polygon);
        caretaker.saveState();

        // Checks that you can't get a momento that doesn't exist
        boolean thrown = false;

        try {
            caretaker.get(-1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        thrown = false;

        try {
            caretaker.get(1);
        } catch (IllegalArgumentException e) {
            thrown = true;
        }
        assertTrue(thrown);

        // Checks that you can't have a negative index
        assertEquals(0, caretaker.getCurrent());
        caretaker.decrement();
        assertEquals(0, caretaker.getCurrent());

        // Checks if the saved shapes are the same as the ones in the canvas
        int i = 0;
        for(Shape s : canvas.getShapes()) {
            assertEquals(s.getId(), caretaker.get(0).getState().get(i).getId());
            i++;
        }
        assertEquals(canvas.getShapes().size(), caretaker.get(0).getState().size());

        Polygon polygon2 = new Polygon(new CanvasPosition(0,0), 0, new Position(0,0), new Translation(0,0), "#4472c4", 5, 100, implementor);
        canvas.add(polygon2);
        caretaker.saveState();

        // Checks if the current saved state contains the same things that the current canvas
        assertEquals(1, caretaker.getCurrent());
        i = 0;
        for(Shape s : canvas.getShapes()) {
            assertEquals(s.getId(), caretaker.get(1).getState().get(i).getId());
            i++;
        }
        assertEquals(canvas.getShapes().size(), caretaker.get(1).getState().size());

        // Checks if the old state is different than the current canvas
        assertFalse(canvas.getShapes().equals(caretaker.get(0).getState().size()));

    }

    public void testPrototype() {
        // Rectangle clone
        Rectangle rectangle = new Rectangle(new CanvasPosition(50, 50), 0, new Position(0,0), new Translation(0,0), "#4472c4", 30, 20, 0, implementor);
        Shape rectangle2 = rectangle.clone();

        assertEquals(rectangle.getClass(), rectangle2.getClass());
        assertEquals(rectangle.getId(), rectangle2.getId());
        assertEquals(rectangle.getWidth(), rectangle2.getWidth());
        assertEquals(rectangle.getHeight(), rectangle2.getHeight());
        assertEquals(rectangle.getBorderRadius(), ((Rectangle) rectangle2).getBorderRadius());
        assertEquals(rectangle.getColor(), rectangle2.getColor());
        assertEquals(rectangle.getImplementor(), rectangle2.getImplementor());
        assertEquals(rectangle.getPositionI(), rectangle2.getPositionI());
        assertEquals(rectangle.getRotation(), rectangle2.getRotation());
        assertEquals(rectangle.getRotationCenter(), rectangle2.getRotationCenter());
        assertEquals(rectangle.getTranslation(), rectangle2.getTranslation());

        // Polygon clone
        Polygon polygon = new Polygon(new CanvasPosition(100,100), 0, new Position(0,0), new Translation(0,0), "#4472c4", 5, 100, implementor);
        Shape polygon2 = polygon.clone();
        assertEquals(polygon.getClass(), polygon2.getClass());
        assertEquals(polygon.getId(), polygon2.getId());
        assertEquals(polygon.getLength(), ((Polygon) polygon2).getLength());
        assertEquals(polygon.getEdges(), ((Polygon) polygon2).getEdges());
        assertEquals(polygon.getVertices(), ((Polygon) polygon2).getVertices());

        // CompoundShape clone
        CompoundShape compoundShape = new CompoundShape(implementor, new ToolbarPosition());
        compoundShape.add(rectangle);
        compoundShape.add(polygon);
        CompoundShape compoundShape2 = compoundShape.clone();
        assertEquals(compoundShape.getClass(), compoundShape2.getClass());
        assertEquals(compoundShape.getShapes().size(), compoundShape2.getShapes().size());
        rectangle.setRotation((float)-10);
        assertEquals((float)-10, compoundShape.getShapes().get(0).getRotation());
        assertNotSame((float)-10, compoundShape2.getShapes().get(0).getRotation());


    }

}
