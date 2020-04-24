  package tr.core;

  public enum LaraAnim implements Animation.Metadata {
    STAND(4, 5, BOOMERANG),
    STRETCH(14, 3),
    TURN(3, 2),
    HALF_RUN(1, 2),
    RUN(12, 2);

    private int length;
    private int timePerFrame;
    private int flags;

    LaraAnim(int length, int timePerFrame, int... flags) {
      this.length = length;
      this.timePerFrame = timePerFrame;
      for (int f : flags) {
        this.flags |= f;
      }
    }

    public int length() {
      return length;
    }

    public int timePerFrame() {
      return timePerFrame;
    }

    public int flags() {
      return flags;
    }
  }