package assignments.Ex2;

import assignments.Ex3.Counter;
import assignments.Ex4.*;
import assignments.Ex4.Levels.*;

import biuoop.GUI;
import biuoop.KeyboardSensor;

public class Ass2Game {

    public static void main(String[] args) {
        GUI gui = new GUI("Win Screen Test", 800, 600);

        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60);

        // you can set any score you want for testing
        Counter score = new Counter(710);

        // run only the win screen
        YouWinScreen win = new YouWinScreen(keyboard, score.getValue());
        runner.run(win);

        gui.close();
    }
}
