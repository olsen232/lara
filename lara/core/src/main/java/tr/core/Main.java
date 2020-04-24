package tr.core;

import static tr.core.PixelConstants.*;

import playn.core.Canvas;
import playn.core.Clock;
import playn.core.Key;
import playn.core.Keyboard;
import playn.core.Mouse;
import playn.scene.SceneGame;
import react.Slot;

public class Main extends SceneGame {
  private Surface surface = new Surface(this.viewSurf);

  private Loader loader;

  static int FRAME_MS = 1000 / 30;

  private int frameCounter = 0;
  private int frameBuffer = 0;

  private Lara lara = new Lara();

  public Main(Platform platform) {
    super(platform.raw, FRAME_MS);

    plat.input().keyboardEnabled = true;
    plat.input().keyboardEvents.connect(keySlot);

    plat.input().mouseEnabled = true;
    plat.input().mouseEvents.connect(mouseSlot);

    loader = new Loader(Font.LOADER, Lara.LOADER);
  }
  
  @Override
  public void update(Clock clock) {
    frameBuffer = 0;
    
    frameCounter++;
    loader.tick();
  }
  
  @Override
  public void paintScene() {
    if (frameBuffer >= 2) return;
    frameBuffer++;
  
    surface.saveTx();
    surface.begin();
    
    surface.clear(0.0f, 0.0f, 0.0f, 1.0f);
    surface.scale(ZOOM, ZOOM);
    
    try {

      if (!loader.done()) {
        loader.draw(surface);
        return;
      }
      
      lara.draw(surface, frameCounter);
      
    } finally {
      surface.end();
      surface.restoreTx();
    }
  }
  

  
  private Slot<Keyboard.Event> keySlot = new Slot<Keyboard.Event>() {
    public void onEmit(Keyboard.Event e) {
      if (e instanceof Keyboard.TypedEvent) {
      
      } else if (e instanceof Keyboard.KeyEvent) {

      }
    }
  };
  
  private Slot<Mouse.Event> mouseSlot = new Slot<Mouse.Event>() {
    public void onEmit(Mouse.Event e) {

    }
  };
}