package assignments.Ex3;

import assignments.Ex1.Ball;
import assignments.Ex2.Block;
import assignments.Ex2.Game;

public class BlockRemover implements HitListener {

    private Game game;
    private Counter remainingBlocks;

    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // remove block from the game
        beingHit.removeFromGame(this.game);

        // update counter
        this.remainingBlocks.decrease(1);

        // no need to listen to this block anymore
        beingHit.removeHitListener(this);
    }
}
