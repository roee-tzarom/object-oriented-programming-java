package assignments.Ex1;

import biuoop.DrawSurface;
import java.awt.Color;

import assignments.Ex2.GameEnvironment;
import assignments.Ex2.CollisionInfo;
import assignments.Ex2.Collidable;
import assignments.Ex2.Sprite;
import assignments.Ex2.Game;


public class Ball implements Sprite {
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
        // no game environment → old frame logic (Ex1)
        if (this.gameEnvironment == null) {
            double nextX = this.center.getX() + this.velocity.getDX();
            double nextY = this.center.getY() + this.velocity.getDY();

            // bounce from left / right borders
            if (nextX + this.radius > right || nextX - this.radius < left) {
                this.velocity = new Velocity(-this.velocity.getDX(), this.velocity.getDY());
                nextX = Math.max(left + this.radius, Math.min(nextX, right - this.radius));
            }

            // bounce from top / bottom borders
            if (nextY + this.radius > bottom || nextY - this.radius < top) {
                this.velocity = new Velocity(this.velocity.getDX(), -this.velocity.getDY());
                nextY = Math.max(top + this.radius, Math.min(nextY, bottom - this.radius));
            }

            this.center = new Point(nextX, nextY);
            return;
        }

        // with game environment → collision logic (Ex2)
        Point start = this.center;
        Point end = this.velocity.applyToPoint(this.center);
        Line trajectory = new Line(start, end);

        CollisionInfo info = this.gameEnvironment.getClosestCollision(trajectory);

        if (info == null) {
            this.center = end;
            return;
        }

        Point collisionPoint = info.collisionPoint();
        Collidable obj = info.collisionObject();

        Velocity newV = obj.hit(collisionPoint, this.velocity);

        this.center = positionBeforeHit(collisionPoint, this.velocity, newV);
        this.velocity = fixTinyDrift(newV);
    }

    // place the ball a bit before the hit point
    private Point positionBeforeHit(Point collisionPoint,
                                    Velocity oldV,
                                    Velocity newV) {
        double oldDx = oldV.getDX();
        double oldDy = oldV.getDY();
        double newDx = newV.getDX();
        double newDy = newV.getDY();

        double newX = collisionPoint.getX();
        double newY = collisionPoint.getY();

        if (oldDx != 0 && Math.signum(oldDx) != Math.signum(newDx)) {
            newX = collisionPoint.getX() - Math.signum(oldDx) * this.radius;
        }

        if (oldDy != 0 && Math.signum(oldDy) != Math.signum(newDy)) {
            newY = collisionPoint.getY() - Math.signum(oldDy) * this.radius;
        }

        return new Point(newX, newY);
    }

    // remove very small dx values
    private Velocity fixTinyDrift(Velocity v) {
        double dx = v.getDX();
        double dy = v.getDY();
        if (Math.abs(dx) < 0.001) {
            dx = 0;
        }
        return new Velocity(dx, dy);
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

    //for Ex2:
    private GameEnvironment gameEnvironment;

    public void setGameEnvironment(GameEnvironment env) {
        this.gameEnvironment = env;
    }

    // Sprite: one step in time
    @Override
    public void timePassed() {
        this.moveOneStep();
    }

    // add this ball to the game
    public void addToGame(Game g) {
        g.addSprite(this);
    }

}