package assignments.Ex1;

import biuoop.GUI;
import biuoop.DrawSurface;

import java.util.Random;
import java.awt.Color;

public class AbstractArtDrawing {

    // עוזרים קטנים לקריאות: עיגול מ-double ל-int בשביל ה-drawLine שעובד עם int ולא double
    private static int X(Point p) { return (int) Math.round(p.getX()); }
    private static int Y(Point p) { return (int) Math.round(p.getY()); }

    public void drawLines() {
        Random rand = new Random();
        GUI gui = new GUI("Abstract Art GUI", 400, 300);
        DrawSurface d = gui.getDrawSurface();

        //יוצר 10 קטעים רנדומליים ושומר אותם במערך
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
            d.drawLine(X(lines[i].start()), Y(lines[i].start()),
                    X(lines[i].end()),   Y(lines[i].end()));
        }

        // צובע את האמצע של כל קטע בכחול
        for (int i = 0; i < 10; ++i) {
            d.setColor(Color.BLUE);
            Point m = lines[i].middle();
            d.fillCircle(X(m), Y(m), 3);
        }

        // צובע את כל נקודות החיתוך של כל הקטעים אחד עם השני באדום
        for (int i = 0; i < 10; ++i) {
            for (int j = i + 1; j < 10; ++j) {
                Point inter = lines[i].intersectionWith(lines[j]);
                if (inter != null) {
                    d.setColor(Color.RED);
                    d.fillCircle(X(inter), Y(inter), 3);
                }
            }
        }

        gui.show(d);
    }

    public static void main(String[] args) {
        AbstractArtDrawing art = new AbstractArtDrawing();
        art.drawLines();
    }
}
