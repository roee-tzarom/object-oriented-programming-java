package assignments.Ex1;

public class Velocity {
    private final double dx;
    private final double dy;

    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public double getDX() { return dx; }
    public double getDY() { return dy; }

    // מזיזה נקודה לפי המהירות
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    // 0° = מעלה, 90° = ימינה, 180° = מטה, 270° = שמאלה
    public static Velocity fromAngleAndSpeed(double angleDegrees, double speed) {
        double rad = Math.toRadians(angleDegrees - 90.0);
        double dx = speed * Math.cos(rad);
        double dy = speed * Math.sin(rad);
        return new Velocity(dx, dy);
    }
}
