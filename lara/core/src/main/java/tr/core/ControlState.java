package tr.core;

public enum ControlState {
  INSTANCE;
  //public final float mouseScale;

  private int controlBits = 0;
  private Control freshControl = null;

  public void onKeyChange(playn.core.Key k, boolean pressed) {
    Control c = Control.forKey(k);
    if (c != null) onControlChange(c, pressed);
  }

  public void onControlChange(Control c, boolean pressed) {
    int lastControlBits = controlBits;
    if (pressed) {
      controlBits |= c.bitCode;
      if (lastControlBits != controlBits) freshControl = c;
    } else {
      controlBits &= ~c.bitCode;
    }
  }

  public Control onlyPressedControl() {
    for (Control c : Control.ALL_CONTROLS) {
      if (c.bitCode == controlBits) return c;
    }
    return null;
  }

  public boolean isPressed(Control c) {
    return (controlBits & c.bitCode) != 0;
  }

  public boolean isFresh(Control c) {
    if (freshControl == c) {
      freshControl = null;
      return true;
    }
    return false;
  }

  public Control freshControl() {
    Control result = freshControl;
    freshControl = null;
    return result;
  }

  public boolean isAnyFreshControl() {
    return freshControl() != null;
  }

  public void clearFresh() {
    freshControl = null;
  }
}
