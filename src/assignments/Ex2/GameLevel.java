package assignments.Ex2;

import assignments.Ex1.*;
import assignments.Ex3.Counter;
import assignments.Ex3.BlockRemover;
import assignments.Ex3.BallRemover;
import assignments.Ex3.ScoreTrackingListener;
import assignments.Ex3.ScoreIndicator;
import assignments.Ex4.Animation;
import assignments.Ex4.AnimationRunner;
import assignments.Ex4.PauseScreen;
import assignments.Ex4.LevelInformation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;
import java.util.List;

public class GameLevel implements Animation {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final int BORDER_SIZE = 20;
    private static final int BALL_RADIUS = 5;
    private static final int PADDLE_HEIGHT = 15;

    private KeyboardSensor keyboard;
    private SpriteCollection sprites;
    private GameEnvironment environment;

    private Counter countBlocks;
    private Counter countBalls;
    private Counter score;

    private AnimationRunner runner;
    private boolean running;
    private boolean started;
    private long gameStartTime;

    private LevelInformation levelInfo;

    // keep a reference (useful for positioning, not mandatory but clean)
    private Paddle paddle;

    public GameLevel(LevelInformation levelInfo, AnimationRunner runner, KeyboardSensor keyboard, Counter score) {
        this.levelInfo = levelInfo;
        this.runner = runner;
        this.keyboard = keyboard;
        this.score = score;

        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.running = false;
        this.started = false;
        this.gameStartTime = -1;
    }

    public void addCollidable(Collidable c) { this.environment.addCollidable(c); }
    public void addSprite(Sprite s) { this.sprites.addSprite(s); }
    public void removeCollidable(Collidable c) { this.environment.removeCollidable(c); }
    public void removeSprite(Sprite s) { this.sprites.removeSprite(s); }

    public void initialize() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();

        // background comes from the level description
        this.addSprite(this.levelInfo.getBackground());

        // score is NOT recreated here (it comes from GameFlow)
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(this.score);

        // borders
        this.countBalls = new Counter(this.levelInfo.numberOfBalls());
        BallRemover ballRemover = new BallRemover(this, this.countBalls);

        Block top = new Block(new Rectangle(new Point(0, 0), WIDTH, BORDER_SIZE), Color.DARK_GRAY);
        top.addToGame(this);

        Block bottom = new Block(new Rectangle(new Point(0, HEIGHT - BORDER_SIZE), WIDTH, BORDER_SIZE), Color.DARK_GRAY);
        bottom.addToGame(this);
        bottom.addHitListener(ballRemover);

        Block left = new Block(new Rectangle(new Point(0, 0), BORDER_SIZE, HEIGHT), Color.DARK_GRAY);
        left.addToGame(this);

        Block right = new Block(new Rectangle(new Point(WIDTH - BORDER_SIZE, 0), BORDER_SIZE, HEIGHT), Color.DARK_GRAY);
        right.addToGame(this);

        // blocks from level
        this.countBlocks = new Counter(this.levelInfo.numberOfBlocksToRemove());
        BlockRemover blockRemover = new BlockRemover(this, this.countBlocks);

        for (Block b : this.levelInfo.blocks()) {
            b.addToGame(this);
            b.addHitListener(blockRemover);
            b.addHitListener(scoreTracker);
        }

        // paddle from level
        int paddleWidth = this.levelInfo.paddleWidth();
        int paddleSpeed = this.levelInfo.paddleSpeed();

        Rectangle paddleRect = new Rectangle(
                new Point((WIDTH - paddleWidth) / 2.0, HEIGHT - BORDER_SIZE - PADDLE_HEIGHT),
                paddleWidth, PADDLE_HEIGHT
        );

        this.paddle = new Paddle(
                paddleRect,
                Color.WHITE,
                this.keyboard,
                paddleSpeed,
                BORDER_SIZE,
                WIDTH - BORDER_SIZE
        );
        this.paddle.addToGame(this);

        // balls: now come from both velocities AND positions
        List<Velocity> velocities = this.levelInfo.initialBallVelocities();
        List<Point> positions = this.levelInfo.initialBallPositions();

        for (int i = 0; i < this.levelInfo.numberOfBalls(); i++) {
            Point p = positions.get(i);
            Velocity v = velocities.get(i);

            Ball ball = new Ball(p.getX(), p.getY(), BALL_RADIUS, Color.WHITE);
            ball.setVelocity(v);
            ball.setGameEnvironment(this.environment);
            ball.addToGame(this);
        }

        // score indicator (uses the shared score)
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score, 0, 0, WIDTH, 20);
        this.addSprite(scoreIndicator);
    }

    public void run() {
        this.running = true;
        this.started = false;
        this.gameStartTime = -1;
        this.runner.run(this);
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // IMPORTANT: do NOT paint a solid background here.
        // The level background sprite draws itself.
        this.sprites.drawAllOn(d);

        // pause on 'p' or 'פ'
        if (this.keyboard.isPressed("p") || this.keyboard.isPressed("פ")) {
            this.runner.run(new PauseScreen(this.keyboard));
        }

        if (!this.started) {
            String msg = "Move the paddle (LEFT or RIGHT) to start";
            int fontSize = 22;

            int x = d.getWidth() / 2 - 200; // same as you had (ok approximation)
            int y = d.getHeight() / 2;

            d.setColor(Color.GRAY);
            d.drawText(x, y, msg, fontSize);

            if (this.keyboard.isPressed(KeyboardSensor.LEFT_KEY)
                    || this.keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
                this.started = true;
            }
            return;
        }

        // game running
        this.sprites.notifyAllTimePassed();

        if (this.countBalls.getValue() == 0 || this.countBlocks.getValue() == 0) {
            this.running = false;
        }
    }
}
