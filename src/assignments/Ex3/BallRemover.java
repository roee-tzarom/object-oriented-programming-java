package assignments.Ex3;

import assignments.Ex1.Ball;
import assignments.Ex2.Block;
import assignments.Ex2.GameLevel;

public class BallRemover implements HitListener {

    private GameLevel gameLevel;
    public Counter countBalls;

    public BallRemover(GameLevel gameLevel, Counter countBalls) {

        this.gameLevel = gameLevel;
        this.countBalls = countBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {

        // remove ball from the game
        hitter.removeFromGame(this.gameLevel);

        //update counter
        this.countBalls.decrease(1);
    }
}
