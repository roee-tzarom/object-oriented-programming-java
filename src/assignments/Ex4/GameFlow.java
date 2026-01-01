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

        // end of game logic
        if (won) {
            // show win screen (SPACE to exit, 0 for bonus)
            YouWinScreen winScreen = new YouWinScreen(this.keyboard, this.score.getValue());
            this.runner.run(winScreen);

            // bonus round if player chose 0
            if (winScreen.bonusChosen()) {
                LevelInformation bonusInfo = new Original(); // bonus level 0
                GameLevel bonusLevel = new GameLevel(bonusInfo, this.runner, this.keyboard, this.score);
                bonusLevel.initialize();
                bonusLevel.run();

                // after bonus: if player died in bonus -> game over, else win
                if (bonusLevel.remainingBalls() == 0) {
                    GameOverScreen over = new GameOverScreen(this.score.getValue());
                    this.runner.run(new KeyPressStoppableAnimation(
                            this.keyboard,
                            KeyboardSensor.SPACE_KEY,
                            over
                    ));
                } else {
                    // final win screen (SPACE to exit)
                    YouWinScreen finalWin = new YouWinScreen(this.keyboard, this.score.getValue());
                    this.runner.run(finalWin);
                }
            }

        } else {
            // lost during regular levels: game over until SPACE
            GameOverScreen over = new GameOverScreen(this.score.getValue());
            this.runner.run(new KeyPressStoppableAnimation(
                    this.keyboard,
                    KeyboardSensor.SPACE_KEY,
                    over
            ));
        }
    }
}
