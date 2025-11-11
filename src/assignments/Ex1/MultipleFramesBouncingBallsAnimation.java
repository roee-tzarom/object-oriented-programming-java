package assignments.Ex1;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import java.util.Random;

public class MultipleFramesBouncingBallsAnimation {
    public static void main(String[] args) {
        final int WIDTH = 700, HEIGHT = 700;
        GUI gui = new GUI("Multiple Frames Bouncing Balls", WIDTH, HEIGHT);
        Sleeper sleeper = new Sleeper();
        DrawSurface d;
        Random rand = new Random();

        final int L1 = 50,  T1 = 50,  R1 = 500, B1 = 500;   // אפור
        final int L2 = 450, T2 = 450, R2 = 600, B2 = 600;   // צהוב

        int n = args.length;
        Ball[] balls = new Ball[n];

        for (int i = 0; i < n; i++) {
            int r = Integer.parseInt(args[i]);

            int left, right, top, bottom;
            boolean firstFrame = i < (n / 2);  // חצי ראשון באפור, חצי שני בצהוב
            if (firstFrame) {
                left = L1; right = R1; top = T1; bottom = B1;
            } else {
                left = L2; right = R2; top = T2; bottom = B2;
            }

            // מיקום התחלתי אקראי בתוך המסגרת שלו
            int x = left + r + rand.nextInt(Math.max(1, (right - left) - 2 * r));
            int y = top  + r + rand.nextInt(Math.max(1, (bottom - top) - 2 * r));

            // צבע לפי אינדקס כדי שלא יחזרו צבעים
            Color color = Ball.getColorByIndex(i);

            Ball b = new Ball(x, y, r, color);
            b.setBounds(left, right, top, bottom);

            // מהירות: קטן = מהיר
            double speed = Math.max(1.0, 50.0 / r);
            double angle = rand.nextInt(360);
            b.setVelocity(Velocity.fromAngleAndSpeed(angle, speed));

            balls[i] = b;
        }

        // לולאת האנימציה
        while (true) {
            d = gui.getDrawSurface();

            // רקע לבן
            d.setColor(Color.WHITE);
            d.fillRectangle(0, 0, WIDTH, HEIGHT);

            // רקע האפור
            d.setColor(Color.GRAY);
            d.fillRectangle(L1, T1, R1 - L1, B1 - T1);

            // רקע הצהוב
            d.setColor(Color.YELLOW);
            d.fillRectangle(L2, T2, R2 - L2, B2 - T2);

            // ציור ועדכון כל הכדורים
            for (Ball b : balls) {
                b.moveOneStep();
                b.drawOn(d);
            }

            gui.show(d);
            sleeper.sleepFor(50);
        }
    }
}
