package assignments.Ex1;

import biuoop.DrawSurface;
import java.awt.Color;

public class Ball {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;

    private int left = 0;
    private int right = 700;
    private int top = 0;
    private int bottom = 700;


    // --- Constructor ---
    public Ball(double x, double y, int size, Color color) {
        this.center = new Point(x, y);
        this.radius = size;
        this.color = color;
    }

    // --- Accessors ---
    public int getX() {
        return (int) this.center.getX();
    }

    public int getY() {
        return (int) this.center.getY();
    }

    public int getSize() {
        return this.radius;
    }

    public Color getColor() {
        return this.color;
    }

    public Velocity getVelocity(){
        return this.velocity;
    }

    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    //פונקציה שמשנה את הגבולות (כשהכדור נוצר במסגרת אחרת)
    public void setBounds(int left, int right, int top, int bottom) {
        this.left = left;
        this.right = right;
        this.top = top;
        this.bottom = bottom;
    }

    // --- Draw the ball on the given DrawSurface ---
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }
    public void moveOneStep() {
        double nextX = this.center.getX() + this.velocity.getDX();
        double nextY = this.center.getY() + this.velocity.getDY();

        if (nextX + this.radius > right || nextX - this.radius < left) {
            this.velocity = new Velocity(-this.velocity.getDX(), this.velocity.getDY());
            nextX = Math.max(left + this.radius, Math.min(nextX, right - this.radius));
        }

        if (nextY + this.radius > bottom || nextY - this.radius < top) {
            this.velocity = new Velocity(this.velocity.getDX(), -this.velocity.getDY());
            nextY = Math.max(top + this.radius, Math.min(nextY, bottom - this.radius));
        }

        this.center = new Point(nextX, nextY);
    }

    // פלטת צבעים גלובלית לכל הכדורים
    private static final Color[] PALETTE = new Color[] {
            Color.YELLOW,
            Color.RED,
            Color.BLUE,
            Color.PINK,
            Color.LIGHT_GRAY,
            Color.ORANGE,
            Color.GREEN,
            Color.CYAN,
            Color.MAGENTA,
            new Color(128, 0, 128),     // Purple
            new Color(255, 105, 180),   // Hot Pink
            new Color(255, 165, 0),     // Vivid Orange
            new Color(0, 128, 128),     // Teal
            new Color(0, 191, 255),     // Deep Sky Blue
            new Color(46, 139, 87),     // Sea Green
            new Color(218, 112, 214),   // Orchid
            new Color(255, 215, 0),     // Gold
            new Color(205, 92, 92),     // Indian Red
            new Color(70, 130, 180),    // Steel Blue
            new Color(154, 205, 50)     // Yellow Green
    };

    // בוחר צבע שונה לכל 20 כדורים
    public static Color getColorByIndex(int index) {
        return PALETTE[index % PALETTE.length];
    }
}