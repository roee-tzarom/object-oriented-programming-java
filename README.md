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
