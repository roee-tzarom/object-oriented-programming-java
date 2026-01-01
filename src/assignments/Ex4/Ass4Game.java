package assignments.Ex4;

import assignments.Ex4.Levels.*;

import biuoop.GUI;
import biuoop.KeyboardSensor;

import java.util.ArrayList;
import java.util.List;

public class Ass4Game {

    public static void main(String[] args) {

        GUI gui = new GUI("Block Breaker", 800, 600);
        KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner runner = new AnimationRunner(gui, 60);

        GameFlow gameFlow = new GameFlow(runner, keyboard);

        // create all levels by index
        LevelInformation[] levels = new LevelInformation[] {
                new Original(),   // 0 - bonus
                new DirectHit(),  // 1
                new WideEasy(),   // 2
                new Green3(),     // 3
                new FinalFour()   // 4
        };

        List<LevelInformation> levelsToRun = new ArrayList<>();

        // no arguments -> run levels 1..4
        if (args.length == 0) {
            for (int i = 1; i < levels.length; i++) {
                levelsToRun.add(levels[i]);
            }
        } else {
            // run levels according to arguments
            for (String s : args) {
                try {
                    int num = Integer.parseInt(s);

                    if (num >= 0 && num < levels.length) {
                        levelsToRun.add(levels[num]);
                    }
                } catch (NumberFormatException e) {
                    // ignore invalid arguments
                }
            }
        }
        gameFlow.runLevels(levelsToRun);
        gui.close();
    }
}
