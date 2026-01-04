package assignments.Ex4;

import assignments.Ex2.GameLevel;
import assignments.Ex3.Counter;
import assignments.Ex4.Levels.Original;

import biuoop.KeyboardSensor;
import java.util.List;

public class GameFlow {

    private AnimationRunner runner;
    private KeyboardSensor keyboard;
    private Counter score;

    public GameFlow(AnimationRunner runner, KeyboardSensor keyboard) {
        this.runner = runner;
        this.keyboard = keyboard;
        this.score = new Counter(0); // shared score for all levels
    }

    public void runLevels(List<LevelInformation> levels) {
        boolean won = true;

        // run the requested levels (usually 1..4)
        for (LevelInformation info : levels) {
            GameLevel level = new GameLevel(info, this.runner, this.keyboard, this.score);
            level.initialize();
            level.run();

            // if no balls left -> game over
            if (level.remainingBalls() == 0) {
                won = false;
                break;
            }
        }

        // LOST
        if (!won) {
            Animation over = new GameOverScreen(this.score.getValue());
            this.runner.run(new KeyPressStoppableAnimation(
                    this.keyboard,
                    KeyboardSensor.SPACE_KEY,
                    over
            ));
            return;
        }

        // WON: show win screen and allow 0 for bonus
        Animation win = new YouWinScreen(this.score.getValue());

        // 1) First wait for '0' (bonus choice)
        KeyPressStoppableAnimation waitFor0 =
                new KeyPressStoppableAnimation(this.keyboard, "0", win);
        this.runner.run(waitFor0);

        // If '0' was pressed -> run bonus level
        if (waitFor0.stoppedByKey()) {
            LevelInformation bonusInfo = new Original();
            GameLevel bonusLevel = new GameLevel(bonusInfo, this.runner, this.keyboard, this.score);
            bonusLevel.initialize();
            bonusLevel.run();

            // if died in bonus -> game over
            if (bonusLevel.remainingBalls() == 0) {
                Animation over = new GameOverScreen(this.score.getValue());
                this.runner.run(new KeyPressStoppableAnimation(
                        this.keyboard,
                        KeyboardSensor.SPACE_KEY,
                        over
                ));
                return;
            }

            // after bonus -> show win until SPACE
            Animation winAfterBonus = new YouWinScreen(this.score.getValue());
            this.runner.run(new KeyPressStoppableAnimation(
                    this.keyboard,
                    KeyboardSensor.SPACE_KEY,
                    winAfterBonus
            ));
            return;
        }

        // 2) If not bonus, just wait for SPACE to exit
        this.runner.run(new KeyPressStoppableAnimation(
                this.keyboard,
                KeyboardSensor.SPACE_KEY,
                win
        ));
    }
}
