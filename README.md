# Java Block Breaker and OOP Exercises

A Java object-oriented programming project that develops from geometry and bouncing-ball animations into a multi-level block-breaker game. The playable entry point is `assignments.Ex4.Ass4Game`.

## Game architecture

- **Geometry and movement:** points, line intersections, rectangles, ball velocity and collision calculations.
- **Game objects:** `Sprite` and `Collidable` interfaces; balls, blocks and paddle implementations.
- **Event handling:** hit listeners remove balls/blocks and update score.
- **Animation flow:** a runner and level controller coordinate pause, win and game-over screens.
- **Levels:** four standard levels plus an optional `Original` bonus level. Command-line level numbers select a subset.

The code is organized by successive course assignments under `src/assignments/Ex1` through `Ex4`. `biuoop-1.4.jar` is the included external drawing/keyboard library.

## Build and play

Requires a JDK and a desktop environment. From the repository root on Linux/macOS:

```bash
mkdir -p out
javac -cp biuoop-1.4.jar -d out $(find src -name '*.java')
java -cp "out:biuoop-1.4.jar" assignments.Ex4.Ass4Game
```

On Windows, use your IDE to compile `src/` with `biuoop-1.4.jar` on the classpath, or replace the runtime classpath separator `:` with `;`. By default the game runs levels 1–4. For selected levels, pass numbers such as `1 3` after the main class.

This is a coursework game built on the supplied BIU OOP library. The repository also contains exploratory animation entry points; the Ex4 game is the main demonstration.


## From geometry to a playable game

The code follows a sequence of OOP assignments. `Ex1` builds the geometry and motion vocabulary: points, lines, velocity and bouncing balls. `Ex2` adds rectangles, collision lookup, a sprite collection and a game environment. `Ex3` introduces event listeners for removing blocks or balls and updating score. `Ex4` controls animation, levels, transitions and terminal win/loss screens. The main game wires those layers together through `Ass4Game` and `GameFlow`.

The two central contracts are `Sprite`, which can draw and update, and `Collidable`, which can report its collision shape and react to a hit. A moving ball searches the environment for the closest collision, then the collided object updates the velocity. Hit listeners decouple block removal and score updates from the block itself. That structure makes the game easier to extend with another level or visual background.

## Explore and customize

Run the default four-level sequence first, then pass level numbers such as `1 3` to choose a subset. The level definitions live in `src/assignments/Ex4/Levels/`; their corresponding background classes live in `LevelsBackground/`. `Original` is an additional level implementation, separate from the default sequence. The included BIU OOP JAR provides graphics and keyboard support, so the program needs a desktop display. Earlier assignment entry points are useful for understanding the progression, while Ex4 is the complete game path.

## Engineering scope

This is a course game with hand-written collision and event logic. It is not packaged as a standalone installer and does not include a unified automated test runner. The repository demonstrates OOP composition, interfaces, listeners and state transitions through a visible application.
