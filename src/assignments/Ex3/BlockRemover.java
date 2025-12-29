package assignments.Ex3;

import assignments.Ex1.Ball;
import assignments.Ex2.Block;
import assignments.Ex2.GameLevel;

public class BlockRemover implements HitListener {

    private GameLevel gameLevel;
    private Counter countBlocks;

    public BlockRemover(GameLevel gameLevel, Counter countBlocks) {
        this.gameLevel = gameLevel;
        this.countBlocks = countBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // remove block from the game
        beingHit.removeFromGame(this.gameLevel);

        // update counter
        this.countBlocks.decrease(1);

        // no need to listen to this block anymore
        beingHit.removeHitListener(this);
    }
}
