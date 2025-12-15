package assignments.Ex3;

import assignments.Ex1.Ball;
import assignments.Ex2.Block;

// add points when blocks are hit
public class ScoreTrackingListener implements HitListener {

    private Counter currentScore;

    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // hit block = +5 points
        this.currentScore.increase(5);
    }
}
