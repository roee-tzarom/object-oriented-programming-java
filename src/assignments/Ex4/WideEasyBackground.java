package assignments.Ex4;

import assignments.Ex2.Sprite;
import biuoop.DrawSurface;

import java.awt.Color;

public class WideEasyBackground implements Sprite {
    @Override
    public void drawOn(DrawSurface d) {
        // background: white
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 600);

        // sun position
        int sx = 130;
        int sy = 130;

        // rays: draw FIRST so the sun covers them (no rays inside sun)
        d.setColor(new Color(255, 220, 100)); // warm ray color
        int raysEndY = 250;                   // roughly where the blocks row is
        for (int x = 0; x <= 800; x += 8) {   // small spacing => many rays
            d.drawLine(sx, sy, x, raysEndY);
        }

        // sun: 3 circles
        d.setColor(new Color(255, 240, 170));
        d.fillCircle(sx, sy, 70);

        d.setColor(new Color(255, 215, 0));
        d.fillCircle(sx, sy, 60);

        d.setColor(new Color(255, 225, 120));
        d.fillCircle(sx, sy, 50);
    }

    @Override
    public void timePassed() {
        // static background
    }
}
