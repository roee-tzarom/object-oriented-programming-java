package assignments.Ex4;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

public class YouWinScreen implements Animation {
    private KeyboardSensor keyboard;
    private int score;

    private boolean stop;
    private boolean bonusChosen;

    // prevents the "key already pressed" issue
    private boolean isAlreadyPressed;

    public YouWinScreen(KeyboardSensor keyboard, int score) {
        this.keyboard = keyboard;
        this.score = score;

        this.stop = false;
        this.bonusChosen = false;

        this.isAlreadyPressed = true;
    }

    public boolean bonusChosen() {
        return this.bonusChosen;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // background
        d.setColor(new Color(30, 144, 255)); // כחול
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());

        d.setColor(new Color(170, 220, 255)); // תכלת נקודות
        for (int i = 0; i < 200; i++) {
            int x = (int) (Math.random() * d.getWidth());
            int y = (int) (Math.random() * d.getHeight());
            d.fillCircle(x, y, 2);
        }

        // text
        d.setColor(Color.WHITE);
        d.drawText(800 / 2 - 110, 270, "YOU WIN!", 48);
        d.drawText(800 / 2 - 90, 320, "Your score is " + score, 24);
        d.drawText(800 / 2 - 180, 370, "Press 0 for bonus round, or SPACE to exit", 20);

        boolean zeroDown = this.keyboard.isPressed("0");
        boolean spaceDown = this.keyboard.isPressed(KeyboardSensor.SPACE_KEY);

        // wait until keys are released once after entering the screen
        if (!zeroDown && !spaceDown) {
            this.isAlreadyPressed = false;
        }

        // handle input
        if (!this.isAlreadyPressed) {
            if (zeroDown) {
                this.bonusChosen = true;
                this.stop = true;
            } else if (spaceDown) {
                this.stop = true;
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
