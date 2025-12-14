package assignments.Ex2;

import assignments.Ex1.Point;
import assignments.Ex1.Velocity;

// things that the ball can hit
public interface Collidable {

    // Return the "collision shape" of the object.
    Rectangle getCollisionRectangle();

    // Notify the object that we collided with it at collisionPoint
    // with a given velocity.
    // Return the new velocity after the hit.
    Velocity hit(Point collisionPoint, Velocity currentVelocity);
}
