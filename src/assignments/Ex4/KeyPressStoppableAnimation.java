package assignments.Ex4;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor keyboard;
    private String key;
    private Animation animation;

    private boolean stop;
    private boolean isAlreadyPressed;

    // tells us if we actually stopped because the key was pressed
    private boolean stoppedByKey;

    public KeyPressStoppableAnimation(KeyboardSensor keyboard, String key, Animation animation) {
        this.keyboard = keyboard;
        this.key = key;
        this.animation = animation;

        this.stop = false;
        this.isAlreadyPressed = true; // bug-fix: ignore a key that was already down
        this.stoppedByKey = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);

        if (!this.keyboard.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }

        if (!this.isAlreadyPressed && this.keyboard.isPressed(this.key)) {
            this.stop = true;
            this.stoppedByKey = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    public boolean stoppedByKey() {
        return this.stoppedByKey;
    }
}
