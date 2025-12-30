package assignments.Ex2;

import assignments.Ex3.Counter;
import assignments.Ex4.*;

import assignments.Ex4.Levels.*;
import biuoop.GUI;
import biuoop.KeyboardSensor;

public class Ass2Game {

    public static void main(String[] args) {
        GUI gui = new GUI("Arkanoid - Level 0", 800, 600);
        //GUI gui = new GUI("Arkanoid - Level 1", 800, 600);
        //GUI gui = new GUI("Arkanoid - Level 2", 800, 600);
        //GUI gui = new GUI("Arkanoid - Level 3", 800, 600);
        //GUI gui = new GUI("Arkanoid - Level 4", 800, 600);


        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60);

        LevelInformation level_0 = new Original();
        LevelInformation level_1 = new DirectHit();
        LevelInformation level_2 = new WideEasy();
        LevelInformation level_3 = new Green3();
        LevelInformation level_4 = new FinalFour();


        Counter score = new Counter(0);

        GameLevel game = new GameLevel(level_3, runner, keyboard, score);
        game.initialize();
        game.run();

        gui.close();
    }
}
