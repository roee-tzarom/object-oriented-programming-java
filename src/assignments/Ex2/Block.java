package assignments.Ex2;

import assignments.Ex1.*;

import biuoop.DrawSurface;
import java.awt.Color;

// a block that can be hit
public class Block implements Collidable, Sprite {

    private Rectangle rect;
    private Color color;


    // create a block with a given rectangle and color
    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.color = color;
    }

    // return the block rectangle
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    // change velocity when the ball hits this block
    @Override
    public Velocity hit(Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDX();
        double dy = currentVelocity.getDY();

        double x1 = this.rect.getUpperLeft().getX();
        double y1 = this.rect.getUpperLeft().getY();
        double x2 = x1 + this.rect.getWidth();
        double y2 = y1 + this.rect.getHeight();

        boolean hitSides = false;
        boolean hitTopOrBottom = false;

        double eps = 0.0001;

        // check left / right sides
        if (Math.abs(collisionPoint.getX() - x1) < eps
                || Math.abs(collisionPoint.getX() - x2) < eps) {
            hitSides = true;
        }

        // check top / bottom sides
        if (Math.abs(collisionPoint.getY() - y1) < eps
                || Math.abs(collisionPoint.getY() - y2) < eps) {
            hitTopOrBottom = true;
        }

        if (hitSides) {
            dx = -dx;
        }
        if (hitTopOrBottom) {
            dy = -dy;
        }

        return new Velocity(dx, dy);
    }

    // draw the block
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

    // Sprite: block does nothing when time passed
    @Override
    public void timePassed() {
        // block does not move
    }

    // add this block to the game
    public void addToGame(Game g) {
        g.addCollidable(this);
        g.addSprite(this);
    }

}