package assignments.Ex4;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

public class KeyPressStoppableAnimation implements Animation{

    private KeyboardSensor keyboard;
    private String key;
    private Animation animation;

    private boolean stop;
    private boolean isAlreadyPressed;

    public KeyPressStoppableAnimation(KeyboardSensor keyboard, String key, Animation animation) {
        this.keyboard = keyboard;
        this.key = key;
        this.animation = animation;

        this.stop = false;
        this.isAlreadyPressed = true; // bug-fix: ignore a key that was already down
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d);

        if (!this.keyboard.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }

        if (!this.isAlreadyPressed && this.keyboard.isPressed(this.key)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
