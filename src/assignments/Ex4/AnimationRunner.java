package assignments.Ex4;

import biuoop.GUI;
import biuoop.DrawSurface;
import biuoop.Sleeper;

public class AnimationRunner {

    private GUI gui;
    private int FPS;
    private Sleeper sleeper;

    public AnimationRunner(GUI gui, int FPS) {
        this.gui = gui;
        this.FPS = FPS;
        this.sleeper = new Sleeper();
    }

    public void run(Animation animation) {
        int millisecondsPerFrame = 1000/this.FPS;

        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis();
            DrawSurface d = this.gui.getDrawSurface();

            animation.doOneFrame(d);

            this.gui.show(d);

            long usedTime = System.currentTimeMillis() - startTime;
            long millisecondsLeftToSleep = millisecondsPerFrame - usedTime;
            if (millisecondsLeftToSleep > 0) {
                this.sleeper.sleepFor(millisecondsLeftToSleep);
            }
        }
    }

    public GUI getGui() {
        return this.gui;
    }
}
