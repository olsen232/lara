package tr.core;

import static tr.core.PixelConstants.*;
import static tr.core.LaraAnim.*;

import java.util.ArrayList;
import java.util.List;

public class Lara {

  private static AnimationSet<LaraAnim> MAIN = new AnimationSet<LaraAnim>("lara/main", LaraAnim.values());
    
  public static final Loadable LOADER = new Loadable() {
    public void startLoading() {
      MAIN.startLoading();
    }

    @Override
    public void finishLoading() {
      MAIN.finishLoading();
    }
  };

  int x = NUM_TILES_X / 2;
  int y = NUM_TILES_Y / 2;

  public void draw(Surface surface, int t) {
    Animation anim = MAIN.get(STRETCH);
    Image image = anim.tFrame(t);
    drawUp(surface, image, x * TILE_SIZE + HALF_TILE_SIZE, (y + 1) * TILE_SIZE);
  }

  private void drawUp(Surface surface, Image image, int xPx, int yPx) {
    surface.draw(image, xPx - image.width() / 2, yPx - image.height());
  }
}