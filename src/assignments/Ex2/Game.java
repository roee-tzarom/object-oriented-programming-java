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
    public void initialize() {
        int width = 800;
        int height = 600;
        int borderSize = 20;

        // window, sleeper, keyboard
        this.gui = new GUI("Game", width, height);
        this.sleeper = new Sleeper();
        this.keyboard = this.gui.getKeyboardSensor();

        // ball in the middle (black)
        Ball ball = new Ball(width / 2.0, height / 2.0, 5, Color.BLACK);
        ball.setVelocity(new Velocity(3, 3));
        ball.setGameEnvironment(this.environment);
        ball.addToGame(this);

        // border blocks
        Block top = new Block(
                new Rectangle(new Point(0, 0), width, borderSize),
                Color.DARK_GRAY);
        top.addToGame(this);

        Block bottom = new Block(
                new Rectangle(new Point(0, height - borderSize), width, borderSize),
                Color.DARK_GRAY);
        bottom.addToGame(this);

        Block left = new Block(
                new Rectangle(new Point(0, 0), borderSize, height),
                Color.DARK_GRAY);
        left.addToGame(this);

        Block right = new Block(
                new Rectangle(new Point(width - borderSize, 0), borderSize, height),
                Color.DARK_GRAY);
        right.addToGame(this);

        // rows of blocks (inside the borders)
        int rows = 4;
        int numBlocks = 10;
        double blockWidth = 70;
        double blockHeight = 20;
        double startY = 70;     // a bit under the top border
        double rowGap = 30;

        Color[] colors = {
                Color.YELLOW,
                Color.RED,
                Color.BLUE,
                Color.PINK,
                Color.LIGHT_GRAY,
                Color.ORANGE,
                Color.GREEN,
                Color.CYAN,
                Color.MAGENTA
        };

        // available width between left and right borders
        double innerWidth = width - 2.0 * borderSize;

        // left gap = gaps between blocks = right gap
        double gap = (innerWidth - numBlocks * blockWidth) / (numBlocks + 1.0);

        int startIndex = (int) (Math.random() * colors.length);

        for (int row = 0; row < rows; row++) {
            double y = startY + row * rowGap;
            Color rowColor = colors[(startIndex + row) % colors.length];

            for (int i = 0; i < numBlocks; i++) {
                double x = borderSize + gap + i * (blockWidth + gap);
                Block b = new Block(
                        new Rectangle(new Point(x, y), blockWidth, blockHeight),
                        rowColor);
                b.addToGame(this);
            }
        }

        // paddle on the bottom bar
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
    }

    public void run() {
        int FPS = 60;
        int millisecondsPreFrame = 1000 / FPS;

        while (true) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = this.gui.getDrawSurface();

            // gray background
            d.setColor(Color.LIGHT_GRAY);
            d.fillRectangle(0, 0, 800, 600);

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
