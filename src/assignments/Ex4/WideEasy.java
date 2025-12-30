package assignments.Ex4;

import assignments.Ex1.Point;
import assignments.Ex1.Velocity;
import assignments.Ex2.Block;
import assignments.Ex2.Rectangle;
import assignments.Ex2.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class WideEasy implements LevelInformation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BORDER_SIZE = 20;

    @Override
    public int numberOfBalls() {
        return 8;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        // 6 balls (3 go right and the other 3 go left)
        // same speed, different angles (all go upward)
        int[] angles = {-60, -45, -30, -15, 15, 30, 45, 60};
        double speed = 5.0;

        List<Velocity> list = new ArrayList<>();
        for (int a : angles) {
            list.add(fromAngleAndSpeed(a, speed));
        }
        return list;
    }

    private Velocity fromAngleAndSpeed(double angleDeg, double speed) {
        double rad = Math.toRadians(angleDeg);
        double dx = speed * Math.sin(rad);
        double dy = -speed * Math.cos(rad); // negative => upward
        return new Velocity(dx, dy);
    }

    @Override
    public List<Point> initialBallPositions() {
        List<Point> list = new ArrayList<>();
        list.add(new Point(245, 425));
        list.add(new Point(275, 390));
        list.add(new Point(310, 365));
        list.add(new Point(355, 350));
        list.add(new Point(445, 350));
        list.add(new Point(490, 365));
        list.add(new Point(525, 390));
        list.add(new Point(555, 425));
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 4;
    }

    @Override
    public int paddleWidth() {
        return 600;
    }

    @Override
    public String levelName() {
        return "Wide Easy";
    }

    @Override
    public Sprite getBackground() {
        return new WideEasyBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int numBlocks = 10;
        int blockWidth = 70;
        int blockHeight = 20;

        int y = 250;

        // equal gaps (same style you had earlier)
        double innerWidth = WIDTH - 2.0 * BORDER_SIZE;
        double gap = (innerWidth - numBlocks * blockWidth) / (numBlocks + 1.0);

        Color[] colors = Block.getRandomColors(numBlocks);

        for (int i = 0; i < numBlocks; i++) {
            double x = BORDER_SIZE + gap + i * (blockWidth + gap);

            blocks.add(new Block(
                    new Rectangle(new Point(x, y), blockWidth, blockHeight),
                    colors[i]
            ));
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 10;
    }
}
