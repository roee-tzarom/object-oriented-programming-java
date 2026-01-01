package assignments.Ex4.Levels;

import assignments.Ex1.Velocity;
import assignments.Ex2.Block;
import assignments.Ex2.Rectangle;
import assignments.Ex1.Point;
import assignments.Ex2.Sprite;
import assignments.Ex4.LevelInformation;

import assignments.Ex4.LevelsBackground.OriginalBackground;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Original implements LevelInformation {

    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> list = new ArrayList<>();
        list.add(new Velocity(0, -4));
        list.add(new Velocity(0, -4));
        return list;
    }

    @Override
    public List<Point> initialBallPositions() {
        List<Point> list = new ArrayList<>();
        list.add(new Point (380, 420));
        list.add(new Point (420, 520));
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 12;
    }

    @Override
    public int paddleWidth() {
        return 100;
    }

    @Override
    public String levelName() {
        return "Original";
    }

    @Override
    public Sprite getBackground() {
        return new OriginalBackground();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int rows = 4;
        int blocksPerRow = 10;

        int blockWidth = 70;
        int blockHeight = 20;

        int borderSize = 20;
        int startY = 70;
        int rowGap = 30;

        double innerWidth = 800 - 2.0 * borderSize;
        double gap = (innerWidth - blocksPerRow * blockWidth) / (blocksPerRow + 1);

        Color[] rowColors = Block.getRandomColors(rows);

        for (int row = 0; row < rows; row++) {
            double y = startY + row * rowGap;
            Color rowColor = rowColors[row];

            for (int i = 0; i < blocksPerRow; i++) {
                double x = borderSize + gap + i * (blockWidth + gap);
                blocks.add(
                        new Block(
                                new Rectangle(new Point(x, y), blockWidth, blockHeight),
                                rowColor
                        )
                );
            }
        }

        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return 4 * 10;
    }
}