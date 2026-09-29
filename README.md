# Java Block Breaker

A playable, multi-level block-breaker game written in Java. Geometry, motion, collision detection, event listeners and animation flow are organized into cooperating objects, making the game a concrete example of object-oriented design.

## What you can play

The game includes a paddle, moving balls, breakable blocks, scoring and multiple level layouts. It displays pause, victory and game-over states. The default game flow runs the standard levels; optional level numbers select a subset. An additional `Original` level is present in the source.

## How the pieces fit together

```text
GameFlow → GameLevel → AnimationRunner
               │
               ├─ SpriteCollection → drawable/updatable objects
               ├─ GameEnvironment → closest collision lookup
               └─ hit listeners → block removal, ball removal, score
```

- **Geometry and motion:** points, lines, rectangles and velocity supply collision calculations.
- **Game objects:** `Sprite` defines drawing and time-step behavior; `Collidable` defines collision geometry and response.
- **Events:** hit listeners keep score and removal logic separate from the blocks and balls that trigger them.
- **Level flow:** `GameFlow` coordinates level transitions and terminal screens.

The `biuoop-1.4.jar` included in the repository provides drawing and keyboard support.

## Build and play

Use a JDK and a desktop environment. Open the repository in a Java IDE, mark `src/` as a source root and add `biuoop-1.4.jar` to the project classpath. Run the `main` method in `Ass4Game`. To select particular standard levels, supply their numbers as program arguments, for example `1 3`.

## Where to explore

| File or component | Why it matters |
| --- | --- |
| `Ass4Game`, `GameFlow` | Entry point and level sequence |
| `GameLevel`, `AnimationRunner` | Game state, updates and rendering loop |
| `Ball`, `Paddle`, `Block` | Moving objects and collision responses |
| `HitListener` implementations | Score and object-removal events |
| `Levels/`, `LevelsBackground/` | Level configuration and drawing |

The game uses the included desktop library and runs locally. It is not packaged as an installer or browser game, and its geometry and event logic are intended to be read alongside the playable result.
