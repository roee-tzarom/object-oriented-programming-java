package assignments.Ex3;

import biuoop.DrawSurface;
import assignments.Ex2.Sprite;
import java.awt.Color;

// draw the current score on the top
public class ScoreIndicator implements Sprite {

    private Counter score;
    private int x;
    private int y;
    private int width;
    private int height;

    public ScoreIndicator(Counter score, int x, int y, int width, int height) {
        this.score = score;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void drawOn(DrawSurface d) {
        // score text
        String text = "Score: " + this.score.getValue();
        d.drawText(this.x + 350, this.y + this.height - 5, text, 16);
    }

    @Override
    public void timePassed() {
        // nothing to do
    }
}
