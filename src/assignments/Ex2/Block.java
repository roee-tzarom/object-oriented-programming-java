package assignments.Ex2;

import assignments.Ex1.*;
import assignments.Ex3.HitListener;
import assignments.Ex3.HitNotifier;

import biuoop.DrawSurface;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

// a block that can be hit
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle rect;
    private Color color;
    private List<HitListener> hitListeners;


    // create a block with a given rectangle and color
    public Block(Rectangle rect, Color color) {
        this.rect = rect;
        this.color = color;
        this.hitListeners = new ArrayList<HitListener>();

    }

    // return the block rectangle
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    // change velocity when the ball hits this block
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double dx = currentVelocity.getDX();
        double dy = currentVelocity.getDY();

        double x1 = this.rect.getUpperLeft().getX();
        double y1 = this.rect.getUpperLeft().getY();
        double x2 = x1 + this.rect.getWidth();
        double y2 = y1 + this.rect.getHeight();

        boolean hitSides = false;
        boolean hitTopOrBottom = false;

        // check left / right sides
        if (collisionPoint.getX() == x1 || collisionPoint.getX() == x2) {
            hitSides = true;
        }

        // check top / bottom sides
        if (collisionPoint.getY() == y1 || collisionPoint.getY() == y2) {
            hitTopOrBottom = true;
        }

        if (hitSides) {
            dx = -dx;
        }

        if (hitTopOrBottom) {
            dy = -dy;
        }

        Velocity newV = new Velocity(dx, dy);

        // notify listeners that this block was hit
        this.notifyHit(hitter);

        return newV;
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

    // remove this block from the game
    public void removeFromGame(Game g) {
        g.removeCollidable(this);
        g.removeSprite(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }
    // notify all listeners that this block was hit
    private void notifyHit(Ball hitter) {
        // copy list so it won't change while we loop
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
}
