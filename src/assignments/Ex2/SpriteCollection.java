package assignments.Ex2;

import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

// holds all sprites in the game
public class SpriteCollection {

    private List<Sprite> sprites;

    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();
    }

    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    // call timePassed() on all sprites.
    public void notifyAllTimePassed() {
        // create copy to avoid problems
        List<Sprite> copy = new ArrayList<Sprite>(this.sprites);
        for (Sprite s : copy) {
            s.timePassed();
        }
    }

    // call drawOn(d) on all sprites.
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            s.drawOn(d);
        }
    }
}
