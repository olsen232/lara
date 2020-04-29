package tr.core;

import playn.core.Key;

public enum Control {
  UP,
  DOWN,
  LEFT,
  RIGHT,
  JUMP;

  public static final Control[] ALL_CONTROLS = Control.values();
  public static final int NUM_CONTROLS = ALL_CONTROLS.length;

  public final int code = this.ordinal();
  public final int bitCode = 1 << this.ordinal();

  public Direction direction;

  public static Control forKey(Key key) {
    switch (key) {
      case UP: 
      case W: 
        return UP;
      case DOWN:
      case S:
        return DOWN;
      case LEFT:
      case A:
        return LEFT;
      case RIGHT:
      case D:
        return RIGHT;
      case SPACE:
        return JUMP;
      default:
        return null;
    }
  }
}
