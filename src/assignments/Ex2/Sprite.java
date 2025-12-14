package assignments.Ex2;

import biuoop.DrawSurface;

// objects that can be drawn and that time moves them
public interface Sprite {

    // draw the sprite to the screen
    void drawOn(DrawSurface d);

    // notify the sprite that time has passed
    void timePassed();
}
