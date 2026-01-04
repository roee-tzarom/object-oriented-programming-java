package assignments.Ex2;

import assignments.Ex4.Animation;
import assignments.Ex4.AnimationRunner;
import assignments.Ex4.KeyPressStoppableAnimation;
import assignments.Ex4.YouWinScreen;

import biuoop.GUI;
import biuoop.KeyboardSensor;

public class Ass2Game {

    public static void main(String[] args) {
        GUI gui = new GUI("Win Screen Test", 800, 600);

        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60);

        int score = 710;

        Animation win = new YouWinScreen(score);

        // show screen until SPACE is pressed
        runner.run(new KeyPressStoppableAnimation(
                keyboard,
                KeyboardSensor.SPACE_KEY,
                win
        ));

        gui.close();
    }
}
