package tr.core;

import static tr.core.PixelConstants.*;
import static tr.core.AnimationSet.LoadState.*;

import java.util.ArrayList;
import java.util.List;

public class Lara {
  private static final String MAIN_PATH = "lara/main/";
  private static final String ATTACK_PATH = "lara/attack/";  

  public static Lara INSTANCE;

  private MainSet mainSet = new MainSet(MAIN_PATH, LOADED);
  //MainSet attackingSet = new MainSet(ATTACK_PATH, LOADED);

  private Animation anim = mainSet.IDLE;
  private Animation tween = null;
  private Movement movement = Movements.NOOP;

  private int x = NUM_TILES_X / 2;
  private int y = NUM_TILES_Y / 2;
  private Direction facing = Direction.RIGHT;

  public void tick() {
    movement.tick();
    anim.tick();

    if (tween != null) {
      tween.tick();
      if (tween.done()) tween = null;
    }

    if (movement.done()) {
      x += maybeFlip(movement.dx);
      y += movement.dy;
      if (movement.flipX) facing = facing.opposite();

      ControlState controls = ControlState.INSTANCE;
      Control forwards = facing.control;
      Control backwards = facing.opposite().control;

      Animation prevAnim = anim;
      if (controls.isPressed(forwards)) {
        anim = mainSet.RUN;
        movement = Movements.RUN;
      } else if (controls.isPressed(backwards)) {
        anim = mainSet.TURN;
        movement = Movements.TURN;
      } else {
        anim = mainSet.IDLE;
        movement = Movements.NOOP;
      }
      movement.restart();
      if (anim != prevAnim) {
        anim.restart();
        if (isEither(mainSet.IDLE, mainSet.RUN, anim) && isEither(mainSet.IDLE, mainSet.RUN, prevAnim)) {
          tween = mainSet.HALF_RUN;
        }
      }
    }
  }

  public void draw(Surface surface) {
    Font.WHITE.singleLine(surface, x + ", " + y, 50, 50);

    int xPx = x * TILE_SIZE + HALF_TILE_SIZE + maybeFlip(movement.xPx());
    int yPx = (y + 1) * TILE_SIZE + movement.yPx();
    Image image = (tween != null) ? tween.currentFrame() : anim.currentFrame();
    drawUp(surface, image, xPx, yPx);
  }

  private void drawUp(Surface surface, Image image, int xPx, int yPx) {
    if (facing == Direction.LEFT) {
      surface.saveTx();
      surface.translate(2 * xPx, 0);
      surface.scale(-1, 1);
    }

    surface.draw(image, xPx - image.width() / 2, yPx - image.height());

    if (facing == Direction.LEFT) {
      surface.restoreTx();
    }
  }

  private int maybeFlip(int rightwards) {
    return facing == Direction.RIGHT ? rightwards : -rightwards;
  }

  private static boolean isEither(Animation first, Animation second, Animation subject) {
    return subject == first || subject == second;
  }

  public static final Loadable LOADER = new Loadable() {
    public void startLoading() {
      new MainSet(MAIN_PATH, START_LOADING);
    }

    @Override
    public void finishLoading() {
      new MainSet(MAIN_PATH, LOADED);
      Lara.INSTANCE = new Lara();
    }
  };

  private static class MainSet extends AnimationSet {
    MainSet(String path, LoadState state) {
      super(path, state);
    }

    Animation STAND =    load("stand", 4)    .slow(5).boomerang();
    Animation STRETCH =  load("stretch", 14) .slow(3);
    Animation TURN =     load("turn", 3)     .slow(2);
    Animation HALF_RUN = load("half_run", 1) .slow(2);
    Animation RUN =      load("run", 12)     .slow(2);

    Animation IDLE = sequence(STAND.repeat(5), STRETCH, STAND.repeat(10));
  }

  private static class Movements extends MovementSet {
    static Movement NOOP = Movement.NOOP;
    static Movement RUN = move().dx(1).length(6).build();
    static Movement TURN = move().flipX().length(6).build();
  }
}