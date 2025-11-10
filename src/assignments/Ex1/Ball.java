package assignments.Ex1;

import biuoop.DrawSurface;
import java.awt.Color;

public class Ball {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;

    private static final int WIDTH = 200;
    private static final int HEIGHT = 200;


    // --- Constructor ---
    public Ball(double x, double y, int r, Color color) {
        this.center = new Point(x, y);  // יוצרים נקודה חדשה מהמיקום
        this.radius = r;
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

    // --- Draw the ball on the given DrawSurface ---
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.radius);
    }
    public void moveOneStep() {
        double nextX = this.center.getX() + this.velocity.getDX();
        double nextY = this.center.getY() + this.velocity.getDY();

        // בדיקה לפגיעה בקירות ימין ושמאל
        if (nextX + this.radius > WIDTH || nextX - this.radius < 0) {
            // הופכים את כיוון התנועה בציר X
            this.velocity = new Velocity(-this.velocity.getDX(), this.velocity.getDY());
        }

        // בדיקה לפגיעה בקירות עליון ותחתון
        if (nextY + this.radius > HEIGHT || nextY - this.radius < 0) {
            // הופכים את כיוון התנועה בציר Y
            this.velocity = new Velocity(this.velocity.getDX(), -this.velocity.getDY());
        }
        this.center = this.velocity.applyToPoint(this.center);
    }
}