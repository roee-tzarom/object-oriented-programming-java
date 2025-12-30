package assignments.Ex4;

import assignments.Ex1.Point;
import assignments.Ex1.Velocity;
import assignments.Ex2.Block;
import assignments.Ex2.Rectangle;
import assignments.Ex2.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class DirectHit implements LevelInformation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public int numberOfBalls() {
        return 1;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        // one ball, straight up
        List<Velocity> list = new ArrayList<>();
        list.add(new Velocity(0, -5));
        return list;
    }

    @Override
    public List<Point> initialBallPositions() {
        // start below the target, in the middle
        List<Point> list = new ArrayList<>();
        list.add(new Point(WIDTH / 2.0, 520));
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 6;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Direct Hit";
    }

    @Override
    public Sprite getBackground() {
        return new DirectHitBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        // single target block in the center
        double blockW = 30;
        double blockH = 30;

        double x = WIDTH / 2.0 - blockW / 2.0;
        double y = 150;

        blocks.add(new Block(
                new Rectangle(new Point(x, y), blockW, blockH),
                Color.RED
        ));

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 1;
    }
}
