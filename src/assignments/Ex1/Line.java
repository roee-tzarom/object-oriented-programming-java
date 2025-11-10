package assignments.Ex1;

public class Line {
    private static final double EPS = 1e-7;

    private Point start;
    private Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2));
    }

    // Return the length of the line
    public double length() {
        return start.distance(end);
    }

    // Returns the middle point of the line
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2.0,(this.start.getY() + this.end.getY()) / 2.0);
    }


    // Returns the start point of the line
    public Point start() {
        return start;
    }

    // Returns the end point of the line
    public Point end() {
        return end;
    }
    // Returns true if the lines intersect, false otherwise
    // נעשה משני הקטעים שמוואות ישר ונמצא את נקודת החתוך בנייהם (אם יש)
    // ואז נבדוק אם הנקודת חיתוך הזאת נמצאת גם בין הקטע הראשון וגם בין הקטע השני
    public boolean isIntersecting(Line other) {
        double[] eq1 = lineEquation(this);
        double[] eq2 = lineEquation(other);

        double A1 = eq1[0], B1 = eq1[1], C1 = eq1[2];
        double A2 = eq2[0], B2 = eq2[1], C2 = eq2[2];

        double det = A1 * B2 - A2 * B1;
        if (det == 0){
            boolean isEndOnLine = isPointOnLine(this, other.end);
            boolean isStartOnLine = isPointOnLine(this, other.start);
            if (isEndOnLine || isStartOnLine) {
                // לפחות אחת מהנקודות נמצאת על הקטע השני
                return true;
            }
            return false;
        }

        // אחרת אם הדטרמיננטה שונה מ-0 יש נקודת חיתוך אחת שהיא
        double xi = (C1 * B2 - C2 * B1) / det;
        double yi = (A1 * C2 - A2 * C1) / det;

        Point intersection = new Point(xi, yi);

        return isPointOnLine(this, intersection) && isPointOnLine(other, intersection);
    }

    // מקבל קטע וממיר אותו למשוואת ישר C = By + Ax
    private double[] lineEquation(Line line) {
        double x1 = line.start.getX();
        double y1 = line.start.getY();
        double x2 = line.end.getX();
        double y2 = line.end.getY();

        double A = y2 - y1;
        double B = x1 - x2;
        double C = A * x1 + B * y1;


        return new double[]{A, B, C};
    }

    // מקבל קטע ונקודה ובודק אם הנקודה נמצאת בקטע
    private boolean isPointOnLine(Line line, Point p) {
        double x1 = line.start.getX();
        double y1 = line.start.getY();
        double x2 = line.end.getX();
        double y2 = line.end.getY();

        double x = p.getX();
        double y = p.getY();

        double cross = (x - x1) * (y2 - y1) - (y - y1) * (x2 - x1);

        if (Math.abs(cross) > EPS) return false;

        return (x >= Math.min(x1, x2) - EPS && x <= Math.max(x1, x2) + EPS) && (y >= Math.min(y1, y2) - EPS && y <= Math.max(y1, y2) + EPS);
    }

    // Returns the intersection point if the lines intersect,
    // and null otherwise.
    public Point intersectionWith(Line other) {
        double[] eq1 = lineEquation(this);
        double[] eq2 = lineEquation(other);

        double A1 = eq1[0], B1 = eq1[1], C1 = eq1[2];
        double A2 = eq2[0], B2 = eq2[1], C2 = eq2[2];

        if (!isIntersecting(other)) return null;

        if(isPointOnLine(this, other.end)) return other.end;

        if (isPointOnLine(this, other.start)) return other.start;

        double det = (A1 * B2) - (A2 * B1);

        double xi = (C1 * B2 - C2 * B1) / det;
        double yi = (A1 * C2 - A2 * C1) / det;

        return new Point(xi, yi);
    }

    // equals -- return true is the lines are equal, false otherwise
    public boolean equals(Line other) {
        return (this.start.equals(other.start) && this.end.equals(other.end)) ||
                (this.start.equals(other.end) && this.end.equals(other.start));
    }
}
