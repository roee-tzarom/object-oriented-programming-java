package assignments.Ex4;

import biuoop.DrawSurface;
import java.awt.Color;

public class YouWinScreen implements Animation {
    private int score;

    public YouWinScreen(int score) {
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // background (your original win style)
        d.setColor(new Color(30, 144, 255));
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        d.setColor(new Color(170, 220, 255));
        for (int i = 0; i < 200; i++) {
            int x = (int) (Math.random() * d.getWidth());
            int y = (int) (Math.random() * d.getHeight());
            d.fillCircle(x, y, 2);
        }

        // text
        d.setColor(Color.WHITE);

        // ONLY fix: move "YOU WIN!" to match your other lines
        // (Your score... and Press 0... were perfect)
        d.drawText(290, 270, "YOU WIN!", 48);
        d.drawText(310, 320, "Your score is " + score, 24);
        d.drawText(220, 370, "Press 0 for bonus round, or SPACE to exit", 20);
    }

    @Override
    public boolean shouldStop() {
        return false; // wrapper stops it
    }
}
