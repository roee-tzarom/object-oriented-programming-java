package assignments.Ex4;

import assignments.Ex2.Sprite;

import biuoop.DrawSurface;
import java.awt.Color;

public class OriginalBackground implements Sprite {

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 600);
    }

    @Override
    public void timePassed() { }
}
