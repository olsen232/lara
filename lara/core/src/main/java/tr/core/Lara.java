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

  private int x = NUM_TILES_X / 2;
  private int y = NUM_TILES_Y / 2;

  public void tick() {
    Animation nextAnim = ControlState.INSTANCE.isPressed(Control.RIGHT) ? mainSet.GO_RUN : mainSet.IDLE;
    if (nextAnim != anim) {
      anim = nextAnim;
      anim.restart();
    } else {
      anim.tick();
    }
  }

  public void draw(Surface surface) {
    Image image = anim.currentFrame();
    drawUp(surface, image, x * TILE_SIZE + HALF_TILE_SIZE, (y + 1) * TILE_SIZE);
  }

  private void drawUp(Surface surface, Image image, int xPx, int yPx) {
    surface.draw(image, xPx - image.width() / 2, yPx - image.height());
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

    Animation STAND =    load("stand", 4)    .slow(5).boomerang().build();
    Animation STRETCH =  load("stretch", 14) .slow(3).build();
    Animation TURN =     load("turn", 2)     .slow(2).build();
    Animation HALF_RUN = load("half_run", 1) .slow(2).build();
    Animation RUN =      load("run", 12)     .slow(2).build();

    Animation IDLE = sequence(repeat(STAND, 5), STRETCH, repeat(STAND, 10));
    Animation GO_RUN = leadIn(HALF_RUN, RUN);    
  }
}