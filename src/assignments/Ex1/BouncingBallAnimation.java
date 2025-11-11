package assignments.Ex1;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

public class BouncingBallAnimation {

    static private void drawAnimation(Point start, double angle, double speed) {

        final int WIDTH = 200, HEIGHT = 200;
        GUI gui = new GUI("title", WIDTH, HEIGHT);

        Sleeper sleeper = new Sleeper();

        Ball ball = new Ball(start.getX(), start.getY(), 30, Color.BLACK);

        ball.setBounds(0, WIDTH, 0, HEIGHT);

        ball.setVelocity(Velocity.fromAngleAndSpeed(angle, speed));

        while (true) {

            ball.moveOneStep();

            DrawSurface d = gui.getDrawSurface();

            d.setColor(Color.WHITE);
            d.fillRectangle(0, 0, WIDTH, HEIGHT);

            ball.drawOn(d);
            gui.show(d);
            sleeper.sleepFor(50);
        }
    }

    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        double angle = Double.parseDouble(args[2]);
        double speed = Double.parseDouble(args[3]);
        drawAnimation(new Point(x, y), angle, speed);
    }
}
