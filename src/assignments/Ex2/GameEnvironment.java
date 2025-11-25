package assignments.Ex2;

import assignments.Ex1.*;

import java.util.ArrayList;
import java.util.List;

// holds all collidable objects in the game
public class GameEnvironment {

    private List<Collidable> collidables;

    // create empty environment
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    // add the given collidable to the environment.
    public void addCollidable(Collidable c) {
        this.collidables.add(c);
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.
    public CollisionInfo getClosestCollision(Line trajectory) {

        Point closestPoint = null;
        Collidable closestObject = null;
        double minDist = Double.POSITIVE_INFINITY;

        for (Collidable c : this.collidables) {
            Rectangle rect = c.getCollisionRectangle();
            Point p = trajectory.closestIntersectionToStartOfLine(rect);

            if (p != null) {
                double d = trajectory.start().distance(p);
                if (d < minDist) {
                    minDist = d;
                    closestPoint = p;
                    closestObject = c;
                }
            }
        }

        if (closestPoint == null) {
            return null;
        }

        return new CollisionInfo(closestPoint, closestObject);
    }


}
