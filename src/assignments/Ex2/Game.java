package assignments.Ex2;

import assignments.Ex1.*;
import assignments.Ex3.Counter;
import assignments.Ex3.BlockRemover;
import assignments.Ex3.BallRemover;
import assignments.Ex3.ScoreTrackingListener;
import assignments.Ex3.ScoreIndicator;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import biuoop.KeyboardSensor;
import java.awt.Color;

public class Game {

    private KeyboardSensor keyboard;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;
    private Counter countBlocks;
    private Counter countBalls;
    private Counter score;


    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
    }

    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize() {
        int width = 800;
        int height = 600;
        int borderSize = 20;

        // create window
        this.gui = new GUI("Game", width, height);
        this.sleeper = new Sleeper();
        this.keyboard = this.gui.getKeyboardSensor();

        // score counter and score listener
        this.score = new Counter(0);
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(this.score);

        // balls in the middle
        Ball ball1 = new Ball(width / 2.0 - 30, height / 2.0, 5, Color.WHITE);
        Ball ball2 = new Ball(width / 2.0 + 30, height / 2.0 - 20, 5, Color.BLACK);

        ball1.setVelocity(new Velocity(3, 3));
        ball2.setVelocity(new Velocity(-4, 2));

        ball1.setGameEnvironment(this.environment);
        ball2.setGameEnvironment(this.environment);

        ball1.addToGame(this);
        ball2.addToGame(this);

        // balls counter and remover
        this.countBalls = new Counter(2);
        BallRemover ballRemover = new BallRemover(this, this.countBalls);

        // border blocks
        Block top = new Block(
                new Rectangle(new Point(0, 0), width, borderSize),
                Color.DARK_GRAY);
        top.addToGame(this);

        Block bottom = new Block(
                new Rectangle(new Point(0, height - borderSize), width, borderSize),
                Color.DARK_GRAY);
        bottom.addToGame(this);
        bottom.addHitListener(ballRemover); // death region block

        Block left = new Block(
                new Rectangle(new Point(0, 0), borderSize, height),
                Color.DARK_GRAY);
        left.addToGame(this);

        Block right = new Block(
                new Rectangle(new Point(width - borderSize, 0), borderSize, height),
                Color.DARK_GRAY);
        right.addToGame(this);

        // rows of blocks
        int rows = 4;
        int numBlocks = 10;
        double blockWidth = 70;
        double blockHeight = 20;
        double startY = 70;
        double rowGap = 30;

        // count only the "real" blocks (the colored rows)
        int totalBlocks = rows * numBlocks;
        this.countBlocks = new Counter(totalBlocks);

        // remover that will remove blocks and update the counter
        BlockRemover blockRemover = new BlockRemover(this, this.countBlocks);

        Color[] colors = {
                Color.YELLOW,
                Color.RED,
                Color.BLUE,
                Color.PINK,
                Color.LIGHT_GRAY,
                Color.ORANGE,
                Color.GREEN,
                Color.CYAN,
                Color.MAGENTA,
        };

        // available width between left and right borders
        double innerWidth = width - 2.0 * borderSize;

        // gap so that left gap = gaps between blocks = right gap
        double gap = (innerWidth - numBlocks * blockWidth) / (numBlocks + 1.0);

        // random start index in colors
        int startIndex = (int) (Math.random() * colors.length);

        for (int row = 0; row < rows; row++) {
            double y = startY + row * rowGap;

            // color for this row: move one step in the array each row
            Color rowColor = colors[(startIndex + row) % colors.length];

            for (int i = 0; i < numBlocks; i++) {
                double x = borderSize + gap + i * (blockWidth + gap);
                Block b = new Block(
                        new Rectangle(new Point(x, y), blockWidth, blockHeight),
                        rowColor);
                b.addToGame(this);

                // block removal listener
                b.addHitListener(blockRemover);

                // score listener
                b.addHitListener(scoreTracker);
            }
        }

        // create paddle on the bottom border (on the gray bar)
        int paddleWidth = 100;
        int paddleHeight = 15;
        int paddleSpeed = 8;

        Rectangle paddleRect = new Rectangle(
                new Point((width - paddleWidth) / 2.0,
                        height - borderSize - paddleHeight),
                paddleWidth, paddleHeight);

        Paddle paddle = new Paddle(
                paddleRect,
                Color.WHITE,
                this.keyboard,
                paddleSpeed,
                borderSize,
                width - borderSize);

        paddle.addToGame(this);

        // score bar on top, drawn last so it will be on top of the border
        ScoreIndicator scoreIndicator =
                new ScoreIndicator(this.score, 0, 0, width, 20);
        this.addSprite(scoreIndicator);
    }

    public void run() {
        int FPS = 60;
        int millisecondsPerFrame = 1000 / FPS;

        // game start time
        long gameStartTime = System.currentTimeMillis();

        while (true) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = this.gui.getDrawSurface();

            // gray background
            d.setColor(Color.LIGHT_GRAY);
            d.fillRectangle(0, 0, 800, 600);

            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();

            // win: no more blocks, still have balls
            if (this.countBlocks != null && this.countBlocks.getValue() == 0) {
                if (this.score != null) {
                    this.score.increase(100); // bonus for clearing all blocks
                }
                long elapsed = System.currentTimeMillis() - gameStartTime;
                showEndScreen(true, elapsed);
                this.gui.close();
                return;
            }

            // lose: no more balls
            if (this.countBalls != null && this.countBalls.getValue() == 0) {
                long elapsed = System.currentTimeMillis() - gameStartTime;
                showEndScreen(false, elapsed);
                this.gui.close();
                return;
            }

            long usedTime = System.currentTimeMillis() - startTime;
            long millisecondLeftToSleep = millisecondsPerFrame - usedTime;

            if (millisecondLeftToSleep > 0) {
                this.sleeper.sleepFor(millisecondLeftToSleep);
            }
        }
    }

    // show final screen with win/lose, score and time (Created by chatGPT just for decoration)
    private void showEndScreen(boolean win, long elapsedMillis) {
        double seconds = elapsedMillis / 1000.0;

        DrawSurface d = this.gui.getDrawSurface();

        if (win) {
            // ---- WIN SCREEN ----

            // background stripes (blue + gold)
            Color[] bg = {
                    new Color(3, 4, 94),     // deep blue
                    new Color(0, 119, 182),  // medium blue
                    new Color(0, 180, 216),  // light blue
                    new Color(250, 204, 21)  // gold
            };

            int stripeWidth = 100;
            for (int i = 0; i < 8; i++) {
                d.setColor(bg[i % bg.length]);
                d.fillRectangle(i * stripeWidth, 0, stripeWidth, 600);
            }

            // center panel
            d.setColor(new Color(10, 10, 30));
            d.fillRectangle(120, 170, 560, 260);

            d.setColor(new Color(250, 204, 21)); // gold frame
            d.drawRectangle(120, 170, 560, 260);

            // small "sparkles"
            java.util.Random rand = new java.util.Random();
            for (int i = 0; i < 40; i++) {
                int x = 130 + rand.nextInt(540);
                int y = 180 + rand.nextInt(240);
                d.setColor(new Color(255, 255, 255));
                d.fillCircle(x, y, 2);
            }

            int scoreValue = (this.score != null) ? this.score.getValue() : 0;

            // title with simple shadow
            String title = "YOU WIN!";
            d.setColor(Color.BLACK);
            d.drawText(203, 230, title, 40);
            d.setColor(new Color(250, 250, 250));
            d.drawText(200, 227, title, 40);

            String scoreText = "Score: " + scoreValue;
            String timeText = "Time: " + String.format("%.1f", seconds) + " sec";

            d.setColor(new Color(250, 250, 250));
            d.drawText(220, 280, scoreText, 28);
            d.drawText(220, 320, timeText, 28);
            d.drawText(220, 370, "Great job! :)", 24);

        } else {
            // ---- LOSE SCREEN ----

            // dark red / black gradient-like stripes
            Color[] bg = {
                    new Color(50, 0, 0),
                    new Color(90, 0, 0),
                    new Color(20, 0, 0),
                    new Color(0, 0, 0)
            };

            int stripeHeight = 60;
            for (int i = 0; i < 10; i++) {
                d.setColor(bg[i % bg.length]);
                d.fillRectangle(0, i * stripeHeight, 800, stripeHeight);
            }

            // center panel
            d.setColor(new Color(15, 0, 0));
            d.fillRectangle(120, 170, 560, 260);

            d.setColor(new Color(200, 50, 50)); // red frame
            d.drawRectangle(120, 170, 560, 260);

            // "broken" circles to give feeling of shards
            java.util.Random rand = new java.util.Random();
            for (int i = 0; i < 35; i++) {
                int x = 130 + rand.nextInt(540);
                int y = 180 + rand.nextInt(240);
                d.setColor(new Color(120, 0, 0));
                d.fillCircle(x, y, 3);
            }

            int scoreValue = (this.score != null) ? this.score.getValue() : 0;

            // title with simple shadow
            String title = "GAME OVER";
            d.setColor(Color.BLACK);
            d.drawText(203, 230, title, 40);
            d.setColor(new Color(255, 230, 230));
            d.drawText(200, 227, title, 40);

            String scoreText = "Score: " + scoreValue;
            String timeText = "Time: " + String.format("%.1f", seconds) + " sec";

            d.setColor(new Color(255, 230, 230));
            d.drawText(220, 280, scoreText, 28);
            d.drawText(220, 320, timeText, 28);
            d.drawText(220, 370, "It's ok, try again :)", 24);
        }

        this.gui.show(d);
        this.sleeper.sleepFor(3000);
    }
}