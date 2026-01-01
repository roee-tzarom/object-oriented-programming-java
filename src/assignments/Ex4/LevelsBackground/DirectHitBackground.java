package assignments.Ex4.LevelsBackground;

import assignments.Ex2.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

public class DirectHitBackground implements Sprite {

    @Override
    public void drawOn(DrawSurface d) {
        // black background
        d.setColor(Color.BLACK);
        d.fillRectangle(0, 0, 800, 600);

        // small white bar at top
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 20);

        // level name
        d.setColor(Color.BLACK);
        d.drawText(600, 16, "Level Name: " + "Direct Hit", 14);

        // target center (match your DirectHit block area roughly)
        int cx = 400;
        int cy = 130;

        // concentric circles
        d.setColor(Color.BLUE);
        d.drawCircle(cx, cy, 105);
        d.drawCircle(cx, cy, 75);
        d.drawCircle(cx, cy, 45);

        // cross lines
        d.drawLine(cx - 125, cy, cx + 125, cy);
        d.drawLine(cx, cy - 125, cx, cy + 125);
    }

    @Override
    public void timePassed() {
        // static background
    }
}
