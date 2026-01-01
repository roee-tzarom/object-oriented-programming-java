package assignments.Ex4.LevelsBackground;

import assignments.Ex2.Sprite;

import biuoop.DrawSurface;
import java.awt.Color;

public class OriginalBackground implements Sprite {

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 600);

        // small white bar at top
        d.setColor(Color.WHITE);
        d.fillRectangle(0, 0, 800, 20);

        // level name
        d.setColor(Color.BLACK);
        d.drawText(600, 16, "Level Name (bonus): " + "Original", 14);
    }

    @Override
    public void timePassed() { }
}
