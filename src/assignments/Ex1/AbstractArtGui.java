package assignments.Ex1;

import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;

public class AbstractArtGui {

    public void drawLines() {
        Random rand = new Random();
        GUI gui = new GUI("Abstract Art GUI", 400, 300);
        DrawSurface d = gui.getDrawSurface();

        Line[] lines = new Line[10];
        for (int i = 0; i < 10; ++i) {
            int x1 = rand.nextInt(400) + 1;
            int y1 = rand.nextInt(300) + 1;
            int x2 = rand.nextInt(400) + 1;
            int y2 = rand.nextInt(300) + 1;
            lines[i] = new Line(x1, y1, x2, y2);
        }

        // צובע את כל הקטעים בשחור
        for (int i = 0; i < 10; ++i) {
            d.setColor(Color.BLACK);
            d.drawLine((int) lines[i].start().getX(), (int) lines[i].start().getY(),
                    (int) lines[i].end().getX(),   (int) lines[i].end().getY());
        }

        // צובע את האמצע של כל קטע בכחול
        for (int i = 0; i < 10; ++i) {
            d.setColor(Color.BLUE);
            Point m = lines[i].middle();
            d.fillCircle((int) m.getX(), (int) m.getY(), 3);
        }

        // צובע את כל נקודות החיתוך של כל הקטעים עם קטעים אחרים באדום
        for (int i = 0; i < 10; ++i) {
            for (int j = i + 1; j < 10; ++j) {
                Point inter = lines[i].intersectionWith(lines[j]);
                if (inter != null) {
                    d.setColor(Color.RED);
                    d.fillCircle((int) inter.getX(), (int) inter.getY(), 3);
                }
            }
        }

        gui.show(d);
    }

    public static void main(String[] args) {
        AbstractArtGui art = new AbstractArtGui();
        art.drawLines();
    }
}
