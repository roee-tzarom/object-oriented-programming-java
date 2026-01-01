package assignments.Ex4;

import biuoop.DrawSurface;

import java.awt.Color;

public class GameOverScreen implements Animation {
    private int finalScore;

    public GameOverScreen(int finalScore) {
        this.finalScore = finalScore;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        int width = d.getWidth();
        int height = d.getHeight();

        // background stripes (lose style)
        int stripeH = 40;
        for (int i = 0; i < height / stripeH + 2; i++) {
            if (i % 2 == 0) {
                d.setColor(new Color(90, 0, 0));
            } else {
                d.setColor(new Color(20, 0, 0));
            }
            d.fillRectangle(0, i * stripeH, width, stripeH);
        }

        // center panel
        int panelW = 520;
        int panelH = 260;
        int panelX = (width - panelW) / 2;
        int panelY = (height - panelH) / 2;

        d.setColor(Color.DARK_GRAY);
        d.fillRectangle(panelX, panelY, panelW, panelH);
        d.setColor(Color.WHITE);
        d.drawRectangle(panelX, panelY, panelW, panelH);

        // title + score
        d.drawText(panelX + 90, panelY + 70, "GAME OVER", 48);
        d.drawText(panelX + 60, panelY + 140, "Your score is: " + this.finalScore, 28);

        // instruction
        d.drawText(panelX + 60, panelY + 210, "Press SPACE to exit", 24);
    }

    @Override
    public boolean shouldStop() {
        return false; // wrapper stops it
    }
}