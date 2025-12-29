package assignments.Ex4;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    public PauseScreen(KeyboardSensor keyboard) {
        this.keyboard = keyboard;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        String text = "paused -- press space to continue";
        int fontSize = 32;

        int x = d.getWidth() / 2 - 220; // קירוב למרכז
        int y = d.getHeight() / 2;

        d.drawText(x, y, text, fontSize);

        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }


    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
