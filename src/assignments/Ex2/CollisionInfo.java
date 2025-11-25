package assignments.Ex2;

import assignments.Ex1.*;

public class CollisionInfo {
    private Point point;
    private Collidable object;

    // create collision info
    public CollisionInfo(Point point, Collidable object) {
        this.point = point;
        this.object = object;
    }

    // the point at which the collision occurs.
    public Point collisionPoint() {
        return this.point;
    }

    // the collidable object involved in the collision.
    public Collidable collisionObject() {
        return this.object;
    }
}
