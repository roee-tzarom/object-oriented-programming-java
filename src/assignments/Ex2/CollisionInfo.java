package assignments.Ex2;

import assignments.Ex1.Point;

// information about a collision
public class CollisionInfo {

    private Point collisionPoint;
    private Collidable collisionObject;

    public CollisionInfo(Point p, Collidable c) {
        this.collisionPoint = p;
        this.collisionObject = c;
    }

    // the point at which the collision occurs.
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    // the collidable object involved in the collision.
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
