package assignments.Ex2;
import assignments.Ex1.*;

import java.util.ArrayList;
import java.util.List;


public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;

    // Create a new rectangle with location and width/height.
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
    }

    // --- Constructor ---
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    // Return a (possibly empty) List of intersection points
    // with the specified line.
    public List<Point> intersectionPoints(Line line) {
        List<Point> result  = new ArrayList<>();

        // 4 values for rectangle corners
        double x1 = this.upperLeft.getX();
        double y1 = this.upperLeft.getY();
        double x2 = x1 + this.width;
        double y2 = y1 + this.height;

        // create 4 lines for the rectangle edges
        Line top = new Line(new Point(x1, y1), new Point(x2, y1));
        Line bottom = new Line(new Point(x1, y2), new Point(x2, y2));
        Line left = new Line(new Point(x1, y1), new Point(x1, y2));
        Line right = new Line(new Point(x2, y1), new Point(x2, y2));

        // check intersection with each edge
        Point p;

        p = line.intersectionWith(top);
        if (p != null) {
            result.add(p);
        }

        p = line.intersectionWith(bottom);
        if (p != null) {
            result.add(p);
        }

        p = line.intersectionWith(left);
        if (p != null) {
            result.add(p);
        }

        p = line.intersectionWith(right);
        if (p != null) {
            result.add(p);
        }

        return result;

    }



}
