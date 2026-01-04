package assignments.Ex4;

import biuoop.DrawSurface;

public class PauseScreen implements Animation {

    @Override
    public void doOneFrame(DrawSurface d) {
        String text = "paused -- press space to continue";
        int fontSize = 32;

        int x = d.getWidth() / 2 - 220; // same as you had
        int y = d.getHeight() / 2;

        d.drawText(x, y, text, fontSize);
    }

    @Override
    public boolean shouldStop() {
        return false; // wrapper stops it
    }
}
