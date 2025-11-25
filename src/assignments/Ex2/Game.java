package assignments.Ex2;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

import assignments.Ex1.Ball;
import assignments.Ex1.Point;
import assignments.Ex1.Velocity;

import java.awt.Color;
import biuoop.KeyboardSensor;

public class Game {

    private KeyboardSensor keyboard;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private Sleeper sleeper;

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


    // Initialize a new game: create the Blocks and Ball (and Paddle)
    // and add them to the game.
    public void initialize() {

        // create window
        this.gui = new GUI("Game", 800, 600);
        this.sleeper = new Sleeper();
        this.keyboard = this.gui.getKeyboardSensor();

        // create ball in the middle
        Ball ball = new Ball(400, 300, 5, Color.WHITE);
        ball.setVelocity(new Velocity(3,3 ));
        ball.setGameEnvironment(this.environment);
        ball.addToGame(this);

        int width = 800;
        int height = 600;
        int borderSize = 20;

        // all border block: top, bottom, left, right
        Block top = new Block(new Rectangle(new Point(0, -borderSize), width, borderSize), Color.BLACK);
        top.addToGame(this);

        Block bottom = new Block(new Rectangle(new Point(0, height), width, borderSize), Color.BLACK);
        bottom.addToGame(this);

        Block left = new Block(new Rectangle(new Point(-borderSize, 0), borderSize, height), Color.BLACK);
        left.addToGame(this);

        Block right = new Block(new Rectangle(new Point(width, 0), borderSize, height), Color.BLACK);
        right.addToGame(this);

        // rows of blocks with random row colors
        int rows = 4;
        int numBlocks = 10;
        double blockWidth = 70;
        double blockHeight = 20;
        double startY = 90;
        double rowGap = 30;

        // color pool
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

        // gap so that left gap = gaps between blocks = right gap
        double gap = (width - numBlocks * blockWidth) / (numBlocks + 1.0);

        // random start index in colors
        int startIndex = (int) (Math.random() * colors.length);

        for (int row = 0; row < rows; row++) {
            double y = startY + row * rowGap;

            // color for this row: move one step in the array each row
            Color rowColor = colors[(startIndex + row) % colors.length];

            for (int i = 0; i < numBlocks; i++) {
                double x = gap + i * (blockWidth + gap);
                Block b = new Block(
                        new Rectangle(new Point(x, y), blockWidth, blockHeight),
                        rowColor);
                b.addToGame(this);
            }
        }

        // create paddle near the bottom
        int paddleWidth = 100;
        int paddleHeight = 15;
        int paddleSpeed = 8;

        Rectangle paddleRect = new Rectangle(
                new Point((width - paddleWidth) / 2.0, height - 40),
                paddleWidth, paddleHeight);

        Paddle paddle = new Paddle(
                paddleRect,
                Color.WHITE,
                this.keyboard,
                paddleSpeed,
                0,
                width);

        paddle.addToGame(this);

    }

    public void run(){
        int FPS = 60;
        int millisecondsPreFrame = 1000 / FPS;
        while (true){
            long startTime = System.currentTimeMillis();

            DrawSurface d = this.gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            this.gui.show(d);
            this.sprites.notifyAllTimePassed();

            long usedTime = System.currentTimeMillis() - startTime;
            long millisecondLeftToSleep = millisecondsPreFrame - usedTime;

            if (millisecondLeftToSleep > 0) {
                this.sleeper.sleepFor(millisecondLeftToSleep);
            }
        }

    }
}
