package assignments.Ex1;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;

public class MultipleBouncingBallsAnimation {

    public static void main(String[] args) {
        // חלון גדול לעבוד איתו
        final int WIDTH = 700, HEIGHT = 700;
        GUI gui = new GUI("Multiple Bouncing Balls", WIDTH, HEIGHT);
        DrawSurface d;
        Sleeper sleeper = new Sleeper();
        Random rand = new Random();

        // אם אין ארגומנטים, לא ניצור כדורים (פשוט חלון ריק)
        Ball[] balls = new Ball[args.length];

        for (int i = 0; i < args.length; i++) {
            int r = Integer.parseInt(args[i]);

            // מיקום התחלתי רנדומלי כך שכל הכדור בתוך המסך
            int x = r + rand.nextInt(Math.max(1, WIDTH  - 2 * r));
            int y = r + rand.nextInt(Math.max(1, HEIGHT - 2 * r));

            Color color = Ball.getColorByIndex(i);

            Ball b = new Ball(x, y, r, color);

            b.setBounds(0, WIDTH, 0, HEIGHT);

            // מהירות: קטן = מהיר, רדיוסים >= 50 כולם איטיים באותה מידה
            double speed = Math.max(1.0, 50.0 / r);

            // זווית אקראית
            double angle = rand.nextInt(360);
            Velocity v = Velocity.fromAngleAndSpeed(angle, speed);
            b.setVelocity(v);

            balls[i] = b;
        }

        // לולאת האנימציה
        while (true) {
            d = gui.getDrawSurface();

            // רקע לבן
            d.setColor(Color.WHITE);
            d.fillRectangle(0, 0, WIDTH, HEIGHT);

            // עדכון וציור כל הכדורים
            for (Ball b : balls) {
                if (b != null) {
                    b.moveOneStep();
                    b.drawOn(d);
                }
            }

            gui.show(d);
            sleeper.sleepFor(50); // ~20FPS
        }
    }
}
