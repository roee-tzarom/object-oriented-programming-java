package assignments.Ex4;

import assignments.Ex2.Sprite;

import biuoop.DrawSurface;
import java.awt.Color;


public class Green3Background implements Sprite {
    @Override
    public void drawOn(DrawSurface d) {
        // green background
        d.setColor(new Color(0, 130, 0));
        d.fillRectangle(0, 0, 800, 600);

        // building
        int bx = 60;
        int by = 450;
        int bw = 96;
        int bh = 180;

        // building body
        d.setColor(new Color(20, 20, 20));
        d.fillRectangle(bx, by, bw, bh);

        // outline (helps it pop)
        d.setColor(Color.BLACK);
        d.drawRectangle(bx, by, bw, bh);

        // windows
        d.setColor(Color.white);
        int winW = 10;
        int winH = 24;
        int gapX = 7;
        int gapY = 7;

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                int x = bx + 9 + col * (winW + gapX);
                int y = by + 10 + row * (winH + gapY);

                d.setColor(Color.white);
                d.fillRectangle(x, y, winW, winH);
            }
        }

        // pole + light
        int pX = 103;
        int pyTop = 200;
        int pyBottom = by;

        d.setColor(new Color(70, 70, 70));
        d.fillRectangle(pX, pyTop, 10, pyBottom - pyTop);

        // base of the pole
        int bX = 93;
        int byTop = 400;
        int byBottom = by;

        d.setColor(new Color(60, 60, 60));
        d.fillRectangle(bX, byTop, 30, byBottom - byTop);


        // lamp head
        d.setColor(new Color(255, 180, 100));
        d.fillCircle(pX + 5, pyTop, 12);

        d.setColor(new Color(255, 80, 80));
        d.fillCircle(pX + 5, pyTop, 8);

        d.setColor(new Color(230, 230, 230));
        d.fillCircle(pX + 5, pyTop, 3);
    }

    @Override
    public void timePassed() {}
}
