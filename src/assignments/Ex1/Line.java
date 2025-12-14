package assignments.Ex1;

public class Line {
    private Point start;
    private Point end;

    // constructors
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    // accessors
    public Point start() {
        return this.start;
    }

    public Point end() {
        return this.end;
    }

    // length of the line
    public double length() {
        return this.start.distance(this.end);
    }

    // middle point of the line
    public Point middle() {
        double midX = (this.start.getX() + this.end.getX()) / 2.0;
        double midY = (this.start.getY() + this.end.getY()) / 2.0;
        return new Point(midX, midY);
    }

    // check if two lines intersect
    public boolean isIntersecting(Line other) {
        return this.intersectionWith(other) != null;
    }

    // compute intersection point between two line segments
    public Point intersectionWith(Line other) {
        // line 1
        double x1 = this.start.getX();
        double y1 = this.start.getY();
        double x2 = this.end.getX();
        double y2 = this.end.getY();

        // line 2
        double x3 = other.start.getX();
        double y3 = other.start.getY();
        double x4 = other.end.getX();
        double y4 = other.end.getY();

        double den = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);

        if (den == 0) {
            return null; // parallel or coincident
        }

        // intersection point of the infinite lines
        double t = ((x1 - x3) * (y3 - y4)
                - (y1 - y3) * (x3 - x4)) / den;
        double u = -((x1 - x2) * (y1 - y3)
                - (y1 - y2) * (x1 - x3)) / den;

        // check if intersection is within the line segments
        if (t < 0 || t > 1 || u < 0 || u > 1) {
            return null;
        }

        double px = x1 + t * (x2 - x1);
        double py = y1 + t * (y2 - y1);

        return new Point(px, py);
    }

    // check if the two lines are equal
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end))
                || (this.start.equals(other.end) && this.end.equals(other.start));
    }

    // return closest intersection point with this rectangle to the start point
    public Point closestIntersectionToStartOfLine(assignments.Ex2.Rectangle rect) {
        // get all intersection points with this line
        java.util.List<Point> points = rect.intersectionPoints(this);

        // no intersection at all
        if (points.isEmpty()) {
            return null;
        }

        // find the closest point to start()
        Point closest = points.get(0);
        double minDist = this.start().distance(closest);

        for (int i = 1; i < points.size(); i++) {
            Point p = points.get(i);
            double d = this.start().distance(p);
            if (d < minDist) {
                minDist = d;
                closest = p;
            }
        }

        return closest;
    }
}
