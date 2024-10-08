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
    public Void onStrokeColor(final StrokeColor c) {
        int originalColor = paint.getColor();  // Save the original color
        paint.setColor(c.getColor());          // Set the stroke color
        c.getShape().accept(this);             // Draw the shape with the new color
        paint.setColor(originalColor);

       // paint.setColor(c.getColor());

//        if(paint.getStyle()!=Style.FILL){
//            paint.setStyle(Style.STROKE);
//        }
//        else {
//            paint.setStyle(Style.FILL_AND_STROKE);
//        }
        //c.getShape().accept(this);
        return null;
    }

    @Override
    public Void onFill(final Fill f) {
        if(paint.getStyle()!=Style.STROKE){
            paint.setStyle(Style.FILL);
        }
        else {
            paint.setStyle(Style.FILL_AND_STROKE);
        }
        f.getShape().accept(this);
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
        o.getShape().accept(this);
        return null;
    }

    @Override
    public Void onPolygon(final Polygon s) {

        final float[] pts = new float[s.getPoints().size() * 4];
        int i=0;

        for(Point point:s.getPoints()){
            pts[i]= point.getX();
            pts[i+1]= point.getY();
            pts[i+2]=point.getX();
            pts[i+3]=point.getY();
            i=i+4;
        }
//        for (int i = 0; i < points.size(); i++) {
//            Point p1 = points.get(i);
//            Point p2 = points.get((i + 1) % points.size()); // Wrap around to the first point for the last line
//
//            // Set the coordinates for each line (x1, y1, x2, y2)
//            pts[i * 4]     = p1.getX();  // x1
//            pts[i * 4 + 1] = p1.getY();  // y1
//            pts[i * 4 + 2] = p2.getX();  // x2
//            pts[i * 4 + 3] = p2.getY();  // y2
//        }


        canvas.drawLines(pts, paint);
        return null;
    }
}
