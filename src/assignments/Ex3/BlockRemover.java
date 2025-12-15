package assignments.Ex3;

import assignments.Ex1.Ball;
import assignments.Ex2.Block;
import assignments.Ex2.Game;

public class BlockRemover implements HitListener {

    private Game game;
    private Counter countBlocks;

    public BlockRemover(Game game, Counter countBlocks) {
        this.game = game;
        this.countBlocks = countBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // remove block from the game
        beingHit.removeFromGame(this.game);

        // update counter
        this.countBlocks.decrease(1);

        // no need to listen to this block anymore
        beingHit.removeHitListener(this);
    }
}
