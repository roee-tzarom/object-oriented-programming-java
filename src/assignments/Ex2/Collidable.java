package assignments.Ex2;

import assignments.Ex1.*;
import assignments.Ex3.HitListener;
import assignments.Ex3.HitNotifier;

public interface Collidable {
    // collision shape
    Rectangle getCollisionRectangle();

    // new hit: we also get the ball that hits
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
