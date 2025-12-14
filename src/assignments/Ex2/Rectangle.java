package assignments.Ex2;

import assignments.Ex1.Point;
import assignments.Ex1.Line;

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

    // Return a (possibly empty) List of intersection points with the line.
    public List<Point> intersectionPoints(Line line) {
        List<Point> points = new ArrayList<Point>();

        // 4 limits corners of the rectangle
        double x1 = this.upperLeft.getX();
        double y1 = this.upperLeft.getY();
        double x2 = x1 + this.width;
        double y2 = y1 + this.height;

        Point topLeft = new Point(x1, y1);
        Point topRight = new Point(x2, y1);
        Point bottomLeft = new Point(x1, y2);
        Point bottomRight = new Point(x2, y2);

        // 4 sides lines
        Line topEdge = new Line(topLeft, topRight);
        Line bottomEdge = new Line(bottomLeft, bottomRight);
        Line leftEdge = new Line(topLeft, bottomLeft);
        Line rightEdge = new Line(topRight, bottomRight);

        // for each edge, check if it intersects with the line
        Point p;

        p = line.intersectionWith(topEdge);
        if (p != null) {
            points.add(p);
        }

        p = line.intersectionWith(bottomEdge);
        if (p != null) {
            points.add(p);
        }

        p = line.intersectionWith(leftEdge);
        if (p != null) {
            points.add(p);
        }

        p = line.intersectionWith(rightEdge);
        if (p != null) {
            points.add(p);
        }

        return points;
    }

    // Return the width and height of the rectangle
    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    // Returns the upper-left point of the rectangle.
    public Point getUpperLeft() {
        return this.upperLeft;
    }
}
