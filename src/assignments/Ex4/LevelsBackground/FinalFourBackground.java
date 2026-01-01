package assignments.Ex4.LevelsBackground;

import assignments.Ex2.Sprite;

import biuoop.DrawSurface;
import java.awt.Color;

public class FinalFourBackground implements Sprite {
    @Override
    public void drawOn(DrawSurface d) {
        // background (dark blue)
        d.setColor(new Color(20, 100, 250));
        d.fillRectangle(0, 0, 800, 600);

        // small white bar at top
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 20);

        // level name
        d.setColor(Color.BLACK);
        d.drawText(600, 16, "Level Name: " + "FinalFour", 14);

        // rain (draw first)
        d.setColor(new Color(170, 200, 255));

        // from left cloud
        for (int x = 115; x <= 210; x += 10) {
            d.drawLine(x, 415, x - 25, 600);
        }

        // from right cloud
        for (int x = 570; x <= 675; x += 10) {
            d.drawLine(x, 475, x - 25, 600);
        }

        // clouds

        // Left cloud build from 5 circles each one different color of gray
        d.setColor(new Color(160, 160, 160));
        d.fillCircle(130, 400, 25);
        d.setColor(new Color(140, 140, 140));
        d.fillCircle(155, 390, 30);
        d.setColor(new Color(120, 120, 120));
        d.fillCircle(185, 400, 25);
        d.setColor(new Color(100, 100, 100));
        d.fillCircle(145, 412, 22);
        d.setColor(new Color(80, 80, 80));
        d.fillCircle(195, 412, 22);

        // Right cloud build from 5 circles each one different color of gray (moved +50)
        d.setColor(new Color(160, 160, 160));
        d.fillCircle(590, 460, 25);
        d.setColor(new Color(140, 140, 140));
        d.fillCircle(615, 450, 30);
        d.setColor(new Color(120, 120, 120));
        d.fillCircle(645, 460, 25);
        d.setColor(new Color(100, 100, 100));
        d.fillCircle(605, 472, 22);
        d.setColor(new Color(80, 80, 80));
        d.fillCircle(655, 472, 22);
    }

    @Override
    public void timePassed() {
    }
}
