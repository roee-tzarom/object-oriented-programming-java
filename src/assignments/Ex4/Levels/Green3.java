package assignments.Ex4.Levels;

import assignments.Ex1.Point;
import assignments.Ex1.Velocity;
import assignments.Ex2.Block;
import assignments.Ex2.Rectangle;
import assignments.Ex2.Sprite;
import assignments.Ex4.LevelsBackground.Green3Background;
import assignments.Ex4.LevelInformation;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Green3 implements LevelInformation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    @Override
    public int numberOfBalls() {
        return 2;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        // 2 balls (one go right and the other left)
        List<Velocity> list = new ArrayList<>();
        list.add(new Velocity(-3, -4));
        list.add(new Velocity(3, -4));
        return list;
    }

    @Override
    public List<Point> initialBallPositions() {
        List<Point> list = new ArrayList<>();
        list.add(new Point (390, 520));
        list.add(new Point (410, 520));
        return list;
    }

    @Override
    public int paddleSpeed() {
        return 10;
    }

    @Override
    public int paddleWidth() {
        return 120;
    }

    @Override
    public String levelName() {
        return "Green 3";
    }

    @Override
    public Sprite getBackground() {
        return new Green3Background();
    }

    @Override
    public List<Block> blocks() {
        List<Block> blocks = new ArrayList<>();

        int blockW = 50;
        int blockH = 25;

        int rows = 5;
        int topRowCount = 10;

        int gapX = 7;
        int gapY = 5;

        int borderSize = 20;
        int rightEdge = 800 - borderSize - gapX;

        int startY = 110;

        Color[] rowColors = Block.getRandomColors(rows);

        for (int row = 0; row < rows; row++) {
            int count = topRowCount - row; // one block less for every row
            int y = startY +row * (blockH + gapY);

            int rowWidth = count * blockW + (count - 1) * gapX;

            int x0 = rightEdge - rowWidth;

            Color c = rowColors[row];

            for (int i = 0; i < count; i++) {
                int x = x0 + i * (blockW + gapX);
                blocks.add(new Block(new Rectangle(new Point(x,y),blockW,blockH), c));
            }
        }
        return blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return blocks().size();
    }
}
