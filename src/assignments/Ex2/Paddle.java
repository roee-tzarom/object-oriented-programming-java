package assignments.Ex2;

import assignments.Ex1.*;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

public class Paddle implements Sprite, Collidable {

    private Rectangle rect;
    private Color color;
    private KeyboardSensor keyboard;
    private int speed;
    private int leftLimit;
    private int rightLimit;

    public Paddle(Rectangle rect, Color color,
                  KeyboardSensor keyboard, int speed,
                  int leftLimit, int rightLimit) {
        this.rect = rect;
        this.color = color;
        this.keyboard = keyboard;
        this.speed = speed;
        this.leftLimit = leftLimit;
        this.rightLimit = rightLimit;
    }

    public void moveLeft() {
        double x = this.rect.getUpperLeft().getX();
        double y = this.rect.getUpperLeft().getY();
        double newX = x - this.speed;

        if (newX < this.leftLimit) {
            newX = this.leftLimit;
        }

        this.rect = new Rectangle(
                new Point(newX, y),
                this.rect.getWidth(),
                this.rect.getHeight());
    }

    public void moveRight() {
        double x = this.rect.getUpperLeft().getX();
        double y = this.rect.getUpperLeft().getY();
        double newX = x + this.speed;

        double maxX = this.rightLimit - this.rect.getWidth();
        if (newX > maxX) {
            newX = maxX;
        }

        this.rect = new Rectangle(
                new Point(newX, y),
                this.rect.getWidth(),
                this.rect.getHeight());
    }

    @Override
    public void timePassed() {
        if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            this.moveLeft();
        }
        if (this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            this.moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        int x = (int) this.rect.getUpperLeft().getX();
        int y = (int) this.rect.getUpperLeft().getY();
        int w = (int) this.rect.getWidth();
        int h = (int) this.rect.getHeight();

        d.setColor(this.color);
        d.fillRectangle(x, y, w, h);
        d.setColor(Color.BLACK);
        d.drawRectangle(x, y, w, h);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDX();
        double dy = currentVelocity.getDY();

        double x1 = this.rect.getUpperLeft().getX();
        double y1 = this.rect.getUpperLeft().getY();
        double width = this.rect.getWidth();

        // hit on the top side: use 5 regions
        if (collisionPoint.getY() == y1) {
            double regionWidth = width / 5.0;
            double hitX = collisionPoint.getX();
            int region = (int) ((hitX - x1) / regionWidth); // 0..4

            if (region < 0) {
                region = 0;
            } else if (region > 4) {
                region = 4;
            }

            double speed = Math.sqrt(dx * dx + dy * dy);
            double angle;

            if (region == 0) {
                angle = 300; // hard left
            } else if (region == 1) {
                angle = 330; // slight left
            } else if (region == 2) {
                angle = 0;   // straight up
            } else if (region == 3) {
                angle = 30;  // slight right
            } else {
                angle = 60;  // hard right
            }

            return Velocity.fromAngleAndSpeed(angle, speed);
        }

        // hit from left or right side: reverse dx
        double xLeft = x1;
        double xRight = x1 + width;
        if (collisionPoint.getX() == xLeft || collisionPoint.getX() == xRight) {
            dx = -dx;
        }

        return new Velocity(dx, dy);
    }

    // Add this paddle to the game.
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }


}
