package assignments.Ex1;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;

public class BouncingBallAnimation {

    static private void drawAnimation(Point start, double angle, double speed) {
        GUI gui = new GUI("title", 200, 200);
        Sleeper sleeper = new Sleeper();

        Ball ball = new Ball(start.getX(), start.getY(), 30, Color.BLACK);
        Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
        ball.setVelocity(v);

        while (true) {
            ball.moveOneStep();
            DrawSurface d = gui.getDrawSurface();
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
