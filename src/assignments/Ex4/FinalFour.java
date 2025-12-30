package assignments.Ex4;

import assignments.Ex1.Point;
import assignments.Ex1.Velocity;
import assignments.Ex2.Block;
import assignments.Ex2.Rectangle;
import assignments.Ex2.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class FinalFour implements LevelInformation {

    private static final int WIDTH = 800;
    private static final int BORDER = 20;

    @Override
    public int numberOfBalls() {
        return 3;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<>();
        list.add(new Velocity(-3, -5));
        list.add(new Velocity(0, -6));
        list.add(new Velocity(3, -5));
        return list;
    }

    @Override
    public List<Point> initialBallPositions() {
        List<Point> list = new ArrayList<>();
        list.add(new Point(385, 520));
        list.add(new Point(400, 520));
        list.add(new Point(415, 520));
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 9;
    }

    @Override
    public int paddleWidth() {
        return 110;
    }

    @Override
    public String levelName() {
        return "FinalFour";
    }

    @Override
    public Sprite getBackground() {
        return new FinalFourBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int blockW = 50;
        int blockH = 25;

        int rows = 7;

        int gapX = 7;
        int gapY = 5;

        int borderSize = 20;

        // maximum blocks per row that still fit between the borders
        int availableWidth = 800 - 2 * borderSize;
        int blocksPerRow = (availableWidth + gapX) / (blockW + gapX);

        // center the row
        int rowWidth = blocksPerRow * blockW + (blocksPerRow - 1) * gapX;
        int startX = borderSize + (availableWidth - rowWidth) / 2;

        int startY = 110;

        Color[] rowColors = Block.getRandomColors(rows);

        for (int row = 0; row < rows; row++) {
            int y = startY + row * (blockH + gapY);
            Color c = rowColors[row];

            for (int i = 0; i < blocksPerRow; i++) {
                int x = startX + i * (blockW + gapX);
                blocks.add(new Block(
                        new Rectangle(new Point(x, y), blockW, blockH),
                        c
                ));
            }
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }
}
