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

        // create window only once
        if (this.gui == null) {
            this.gui = new GUI("Game", width, height);
            this.sleeper = new Sleeper();
            this.keyboard = this.gui.getKeyboardSensor();
        }

        // reset state for new game
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

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

        // score bar on top
        ScoreIndicator scoreIndicator =
                new ScoreIndicator(this.score, 0, 0, width, 20);
        this.addSprite(scoreIndicator);
    }

    public void run() {
        int FPS = 60;
        int millisecondsPerFrame = 1000 / FPS;

        // outer loop: play again in the same window
        while (true) {

            boolean started = false;
            long gameStartTime = 0;

            // inner loop: single game
            while (true) {
                long startTime = System.currentTimeMillis(); // frame start time

                DrawSurface d = this.gui.getDrawSurface();

                // background
                d.setColor(Color.LIGHT_GRAY);
                d.fillRectangle(0, 0, 800, 600);

                // draw all game objects
                this.sprites.drawAllOn(d);

                // wait for first paddle move
                if (!started) {
                    d.setColor(Color.BLACK);
                    d.drawText(200, 320,
                            "Move the paddle (LEFT or RIGHT) to start", 22);

                    this.gui.show(d);

                    // start only when player moves the paddle
                    if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)
                            || this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
                        started = true;
                        gameStartTime = System.currentTimeMillis();
                    }

                    long usedTimeWait = System.currentTimeMillis() - startTime;
                    long msLeftWait = millisecondsPerFrame - usedTimeWait;
                    if (msLeftWait > 0) {
                        this.sleeper.sleepFor(msLeftWait);
                    }
                    continue; // do not move sprites yet
                }

                // game is running: show and update sprites
                this.gui.show(d);
                this.sprites.notifyAllTimePassed();

                long elapsedMillis = System.currentTimeMillis() - gameStartTime;
                double elapsedSeconds = elapsedMillis / 1000.0;

                // win: no more blocks
                if (this.countBlocks != null && this.countBlocks.getValue() == 0) {
                    if (this.score != null) {
                        this.score.increase(100); // win bonus
                    }

                    boolean playAgain = showEndScreen(
                            true, this.score.getValue(), elapsedSeconds);

                    if (playAgain) {
                        this.initialize(); // new game in same window
                        break;             // break inner loop
                    } else {
                        this.gui.close();
                        return;
                    }
                }

                // lose: no more balls
                if (this.countBalls != null && this.countBalls.getValue() == 0) {

                    boolean playAgain = showEndScreen(
                            false, this.score.getValue(), elapsedSeconds);

                    if (playAgain) {
                        this.initialize(); // new game in same window
                        break;             // break inner loop
                    } else {
                        this.gui.close();
                        return;
                    }
                }

                // keep constant FPS
                long usedTime = System.currentTimeMillis() - startTime;
                long millisecondLeftToSleep = millisecondsPerFrame - usedTime;
                if (millisecondLeftToSleep > 0) {
                    this.sleeper.sleepFor(millisecondLeftToSleep);
                }
            }
            // loop again: new game already initialized
        }
    }

    // extra end screen (added by ChatGPT, not part of the required assignment)
    private boolean showEndScreen(boolean win, int score, double timeSeconds) {
        while (true) {
            DrawSurface d = this.gui.getDrawSurface();

            int width = d.getWidth();
            int height = d.getHeight();

            // background stripes
            int stripeH = 40;
            for (int i = 0; i < height / stripeH + 2; i++) {
                if (win) {
                    d.setColor(i % 2 == 0
                            ? new Color(30, 144, 255)
                            : new Color(0, 0, 80));
                } else {
                    d.setColor(i % 2 == 0
                            ? new Color(90, 0, 0)
                            : new Color(20, 0, 0));
                }
                d.fillRectangle(0, i * stripeH, width, stripeH);
            }

            // center panel
            int panelW = 500;
            int panelH = 260;
            int panelX = (width - panelW) / 2;
            int panelY = (height - panelH) / 2;

            d.setColor(Color.DARK_GRAY);
            d.fillRectangle(panelX, panelY, panelW, panelH);
            d.setColor(Color.WHITE);
            d.drawRectangle(panelX, panelY, panelW, panelH);

            // title
            String title = win ? "YOU WIN!" : "GAME OVER";
            d.drawText(panelX + 80, panelY + 60, title, 48);

            // score + time
            d.drawText(panelX + 40, panelY + 110, "Score: " + score, 26);
            d.drawText(panelX + 40, panelY + 145,
                    String.format("Time: %.1f sec", timeSeconds), 26);

            // message
            String msg = win ? "Great job! :)" : "It's ok, try again :)";
            d.drawText(panelX + 40, panelY + 185, msg, 24);

            // instructions
            d.drawText(panelX + 40, panelY + 220,
                    "Press ENTER to play again, SPACE to quit", 20);

            this.gui.show(d);

            // ENTER -> play again
            if (this.keyboard.isPressed("enter")) {
                return true;
            }
            // SPACE -> quit (works גם כשהמקלדת על עברית)
            if (this.keyboard.isPressed("space")) {
                return false;
            }

            this.sleeper.sleepFor(30);
        }
    }

}
