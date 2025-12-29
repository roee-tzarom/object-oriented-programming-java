package assignments.Ex2;

import assignments.Ex3.Counter;
import assignments.Ex4.AnimationRunner;
import assignments.Ex4.LevelInformation;
import assignments.Ex4.DirectHit;
import assignments.Ex4.WideEasy;

import biuoop.GUI;
import biuoop.KeyboardSensor;

public class Ass2Game {

    public static void main(String[] args) {
        // GUI gui = new GUI("Arkanoid - Level 1", 800, 600);
        GUI gui = new GUI("Arkanoid - Level 2", 800, 600);

        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60);

        LevelInformation level_1 = new DirectHit();
        LevelInformation level_2 = new WideEasy();

        Counter score = new Counter(0);

        GameLevel game = new GameLevel(level_2, runner, keyboard, score);
        game.initialize();
        game.run();

        gui.close();
    }
}
