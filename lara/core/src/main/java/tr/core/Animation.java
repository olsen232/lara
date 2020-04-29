package tr.core;

public final class Animation {
  public static final Animation EMPTY = new Animation(new Image[]{});

  private Image[] frames;
  private int t = 0;

  public Animation(Image[] frames) {
    if (frames == null) throw new NullPointerException("frames");
    this.frames = frames;
  }

  public int length() {
    return frames.length;
  }

  public Image frame(int t) {
    return frames[t % frames.length];
  }

  public void tick() {
    t = (t + 1) % frames.length;
  }

  public void restart() {
    t = 0;
  }

  public boolean done() {
    return t == 0;
  }

  public Image currentFrame() {
    return frames[t];
  }

  public Animation slow(int factor) {
    if (this == EMPTY) return this;
    Image[] newFrames = new Image[frames.length * factor];
    for (int i = 0; i < newFrames.length; i++) {
      newFrames[i] = frames[i / factor];
    }
    return new Animation(newFrames);
  }

  public Animation boomerang() {
    if (this == EMPTY) return this;
    Image[] newFrames = new Image[frames.length * 2];
    int end = newFrames.length - 1;
    for (int i = 0; i < frames.length; i++) {
      newFrames[i] = frames[i];
      newFrames[end - i] = frames[i];
    }
    return new Animation(newFrames);
  }

  public Animation repeat(int count) {
    if (this == EMPTY) return this;
    Image[] newFrames = new Image[frames.length * count];
    for (int i = 0; i < newFrames.length; i++) {
      newFrames[i] = frames[i % frames.length];
    }
    return new Animation(newFrames);
  }

  public Animation append(Animation that) {
    return sequence(new Animation[]{this, that});
  }

  public static Animation sequence(Animation... parts) {
    int length = 0;
    for (Animation part : parts) {
      length += part.length();
    }
    if (length == 0) return Animation.EMPTY;
    Image[] newFrames = new Image[length];
    int offset = 0;
    for (Animation part : parts) {
      System.arraycopy(part.frames, 0, newFrames, offset, part.length());
      offset += part.length();
    }
    return new Animation(newFrames);
  }
}