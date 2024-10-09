package edu.luc.etl.cs313.android.shapes.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import edu.luc.etl.cs313.android.shapes.model.*;

/**
 * A Visitor for drawing a shape to an Android canvas.
 */
public class Draw implements Visitor<Void> {

    // TODO entirely your job (except onCircle)

    private final Canvas canvas;
    private final Paint paint;

    public Draw(final Canvas canvas, final Paint paint) {
        this.canvas = canvas; // FIXED
        this.paint = paint; // FIXED
        paint.setStyle(Style.STROKE);
    }

    @Override
    public Void onCircle(final Circle c) { //DONE
        canvas.drawCircle(0, 0, c.getRadius(), paint);
        return null;
    }

    @Override
    public Void onStrokeColor(final StrokeColor c) { //DONE
        int original_c = paint.getColor();

        paint.setColor(c.getColor());
        c.getShape().accept(this);

        paint.setColor(original_c);

        return null;
    }

    @Override
    public Void onFill(final Fill f) { //DONE
        Style original = paint.getStyle();

        paint.setStyle(Style.FILL_AND_STROKE);
        f.getShape().accept(this);
        paint.setStyle(original);

        return null;
    }

    @Override
    public Void onGroup(final Group g) { //DONE
        for (Shape s:g.getShapes()){
            s.accept(this);
        }
        return null;
    }

    @Override
    public Void onLocation(final Location l) { //DONE
        canvas.translate(l.getX(), l.getY());
        l.getShape().accept(this);
        canvas.translate(-l.getX(), -l.getY());
        return null;
    }

    @Override
    public Void onRectangle(final Rectangle r) { //DONE
        canvas.drawRect(0,0, r.getWidth(), r.getHeight(), paint);

        return null;
    }

    @Override
    public Void onOutline(Outline o) { //DONE
        Style original = paint.getStyle();

        paint.setStyle(Style.STROKE);
        o.getShape().accept(this);
        paint.setStyle(original);
        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) { //DONE

        final int total_pts = s.getPoints().size();

        final float[] points = new float[4 * total_pts];
        int j = 0;

        for (int i = 0; i < total_pts; i++) {
            Point point_1 = s.getPoints().get(i);
            Point point_2 = s.getPoints().get((i + 1) % total_pts);

            points[j++] = point_1.getX();
            points[j++] = point_1.getY();
            points[j++] = point_2.getX();
            points[j++] = point_2.getY();
        }

        canvas.drawLines(points, paint);
        return null;
    }
}
