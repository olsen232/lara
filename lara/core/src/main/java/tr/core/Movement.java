package tr.core;

import static tr.core.PixelConstants.*;

public final class Movement {
  public static Movement NOOP = Movement.builder().build();

  public final int dx;
  public final int dy;
  public final boolean flipX;
  public final int length;

  public Movement(Builder that) {
    this.dx = that.dx;
    this.dy = that.dy;
    this.flipX = that.flipX;
    this.length = that.length;
  }

  private int t = 0;

  public void tick() {
    t = (t + 1) % length;
  }

  public void restart() {
    t = 0;
  }

  public boolean done() {
    return t == 0;
  }

  public int xPx() {
    return dx * interp();
  }

  public int yPx() {
    return dy * interp();
  }

 private int interp() {
    return (2 * t + 1) * TILE_SIZE / length / 2;
 }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    public int dx = 0;
    public int dy = 0;
    public boolean flipX = false;
    public int length = 1;

    public Builder dx(int dx) {
      this.dx = dx;
      return this;
    }

    public Builder dy(int dy) {
      this.dy = dy;
      return this;
    }

    public Builder flipX() {
      this.flipX = true;
      return this;
    }

    public Builder length(int length) {
      this.length = length;
      return this;
    }

    public Movement build() {
      return new Movement(this);
    }
  }
}