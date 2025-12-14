package assignments.Ex3;

import assignments.Ex1.Ball;
import assignments.Ex2.Block;

// any object that wants to know when a block is hit
public interface HitListener {
    // This method is called whenever the beingHit object is hit.
    // hitter is the Ball that hit the block.
    void hitEvent(Block beingHit, Ball hitter);
}
