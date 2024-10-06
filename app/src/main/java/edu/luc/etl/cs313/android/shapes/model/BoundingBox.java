package edu.luc.etl.cs313.android.shapes.model;

/**
 * A shape visitor for calculating the bounding box, that is, the smallest
 * rectangle containing the shape. The resulting bounding box is returned as a
 * rectangle at a specific location.
 */
public class BoundingBox implements Visitor<Location> {

    // DONE entirely your job (except onCircle)

    @Override
    public Location onCircle(final Circle c) {
        final int radius = c.getRadius();
        return new Location(-radius, -radius, new Rectangle(2 * radius, 2 * radius));
    }

    @Override
    public Location onFill(final Fill f) {
        return f.getShape().accept(this);
    }

    @Override
    public Location onGroup(final Group g) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Shape shape : g.getShapes()) {
            Location loc = shape.accept(this);

            Shape boundingBoxShape = loc.getShape();

            Rectangle boundingBox;
            if (boundingBoxShape instanceof Rectangle) {
                boundingBox = (Rectangle) boundingBoxShape;
            } else {
                continue;
            }

            int shapeMinX = loc.getX();
            int shapeMinY = loc.getY();
            int shapeMaxX = shapeMinX + boundingBox.getWidth();
            int shapeMaxY = shapeMinY + boundingBox.getHeight();

            minX = Math.min(minX, shapeMinX);
            minY = Math.min(minY, shapeMinY);
            maxX = Math.max(maxX, shapeMaxX);
            maxY = Math.max(maxY, shapeMaxY);
        }

        return new Location(minX, minY, new Rectangle(maxX - minX, maxY - minY));
    }

    @Override
    public Location onLocation(final Location l) {

        Location innerBoundingBox = l.getShape().accept(this);

        int x = l.getX() + innerBoundingBox.getX();
        int y = l.getY() + innerBoundingBox.getY();

        return new Location(x, y, innerBoundingBox.getShape());
    }

    @Override
    public Location onRectangle(final Rectangle r) {
        return new Location(0, 0, new Rectangle(r.getWidth(), r.getHeight()));
    }

    @Override
    public Location onStrokeColor(final StrokeColor c) {
        return c.getShape().accept(this);
    }

    @Override
    public Location onOutline(final Outline o) {
        return o.getShape().accept(this);
    }

    @Override
    public Location onPolygon(final Polygon s) {

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Point point : s.getPoints()) {
            int x = point.getX();
            int y = point.getY();
            minX = Math.min(minX, x);
            minY = Math.min(minY, y);
            maxX = Math.max(maxX, x);
            maxY = Math.max(maxY, y);
        }

        int width = maxX - minX;
        int height = maxY - minY;

        return new Location(minX, minY, new Rectangle(width, height));
    }
}
