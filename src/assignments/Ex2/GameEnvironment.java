package assignments.Ex2;

import assignments.Ex1.Line;
import assignments.Ex1.Point;

import java.util.ArrayList;
import java.util.List;

// holds all collidable objects in the game
public class GameEnvironment {

    private List<Collidable> collidables;

    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null.
    // Else, return the information about the closest collision.
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable closestCollidable = null;
        double minDist = Double.POSITIVE_INFINITY;

        for (Collidable c : this.collidables) {
            Rectangle r = c.getCollisionRectangle();
            Point p = trajectory.closestIntersectionToStartOfLine(r);

            if (p != null) {
                double d = trajectory.start().distance(p);
                if (d < minDist) {
                    minDist = d;
                    closestPoint = p;
                    closestCollidable = c;
                }
            }
        }

        if (closestPoint == null) {
            return null;
        }

        return new CollisionInfo(closestPoint, closestCollidable);
    }

    public void removeCollidable(Collidable c) {
        this.collidables.remove(c);
    }

}
