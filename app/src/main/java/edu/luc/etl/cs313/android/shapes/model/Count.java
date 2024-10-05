package edu.luc.etl.cs313.android.shapes.model;

/**
 * A visitor to compute the number of basic shapes in a (possibly complex)
 * shape.
 */
public class Count implements Visitor<Integer> {

    // TODO entirely your job

    private int count = 0;

    @Override
    public Integer onPolygon(final Polygon p) {
        for (Point point : p.getPoints()) {
            count++;
        }
        return count;
    }

    @Override
    public Integer onCircle(final Circle c) {
        count++;
        return count;
    }

    @Override
    public Integer onGroup(final Group g) {
        for (Shape shape : g.getShapes()) {
            shape.accept(this);
        }
        return count;
    }

    @Override
    public Integer onRectangle(final Rectangle q) {
        count++;
        return count;
    }

    @Override
    public Integer onOutline(final Outline o) {
        return o.getShape().accept(this);
    }

    @Override
    public Integer onFill(final Fill c) {
        return c.getShape().accept(this);
    }

    @Override
    public Integer onLocation(final Location l) {
        return l.getShape().accept(this);
    }

    @Override
    public Integer onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this);
    }
}
