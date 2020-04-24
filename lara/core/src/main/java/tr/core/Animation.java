package tr.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Animation {
  public final Image[] frames;
  public final int timePerFrame;

  public static Animation load(Image raw, Metadata metadata) {
    Animation result = new Animation(raw.tileInto(metadata.length(), 1), metadata.timePerFrame());
    if ((metadata.flags() & Metadata.BOOMERANG) != 0) {
      result = Animation.concat(result, result.reverse());
    }
    return result;
  }

  public Animation(Image[] frames, int timePerFrame) {
    this.frames = frames;
    this.timePerFrame = timePerFrame;
  }

  private static final Image[] BUFFERED_IMAGE_ARRAY = new Image[0];
  public Animation(List<Image> frames, int timePerFrame) {
    this(frames.toArray(BUFFERED_IMAGE_ARRAY), timePerFrame);
  }

  public Image frame(int f) {
    return this.frames[f % frames.length];
  }

  public Image tFrame(int t) {
    return frame(t / timePerFrame);
  }

  public int length() {
    return frames.length;
  }

  public int tLength() {
    return frames.length * timePerFrame;
  }

  public Animation reverse() {
    Image[] reversed = this.frames.clone();
    Collections.reverse(Arrays.asList(reversed));
    return new Animation(reversed, timePerFrame);
  }

  public static Animation concat(Animation... anims) {
    int length = 0;
    for (Animation anim : anims) {
      length += anim.frames.length;
    }
    Image[] frames = new Image[length];
    int i = 0;
    for (Animation anim : anims) {
      System.arraycopy(anim.frames, 0, frames, i, anim.frames.length);
      i += anim.frames.length;
    }
    return new Animation(frames, anims[0].timePerFrame);
  }

  public static interface Metadata {
    public static final int BOOMERANG = 0x1;

    int length();
    int timePerFrame();
    int flags();
  }
}