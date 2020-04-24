package tr.core;

public final class PixelConstants {
  private PixelConstants() {}
  
  public static int FRAME_RATE = 30;
  public static int FRAME_MS = 1000 / FRAME_RATE;

  public static final int ZOOM = 2;

  public static final int SCREEN_WIDTH = 640;
  public static final int SCREEN_HEIGHT = 480;

  public static final int FONT_SIZE = 8;
  public static final int TEXT_LINE_HEIGHT = 10;

  public static final int TILE_SIZE = 32;
  public static final int HALF_TILE_SIZE = 16;
  public static final int NUM_TILES_X = 20;
  public static final int NUM_TILES_Y = 15;
}
