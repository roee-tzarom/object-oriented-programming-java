package assignments.Ex3;

import assignments.Ex1.Ball;
import assignments.Ex2.Block;
import assignments.Ex2.Game;

public class BallRemover implements HitListener {

    private Game game;
    public Counter countBalls;

    public BallRemover(Game game, Counter countBalls) {

        this.game = game;
        this.countBalls = countBalls;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {

        // remove ball from the game
        hitter.removeFromGame(this.game);

        //update counter
        this.countBalls.decrease(1);
    }
}
