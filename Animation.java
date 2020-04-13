import java.awt.image.BufferedImage;
import java.util.List;

public class Animation {
  public final BufferedImage[] frames;
  public final int timePerFrame;

  public Animation(BufferedImage[] frames, int timePerFrame) {
    this.frames = frames;
    this.timePerFrame = timePerFrame;
  }

  private static final BufferedImage[] BUFFERED_IMAGE_ARRAY = new BufferedImage[0];
  public Animation(List<BufferedImage> frames, int timePerFrame) {
    this(frames.toArray(BUFFERED_IMAGE_ARRAY), timePerFrame);
  }

  public BufferedImage frame(int f) {
    return this.frames[f % frames.length];
  }

  public BufferedImage tFrame(int t) {
    return frame(t / timePerFrame);
  }

  public int length() {
    return frames.length;
  }

  public static Animation concat(Animation... anims) {
    int length = 0;
    for (Animation anim : anims) {
      length += anim.frames.length;
    }
    BufferedImage[] frames = new BufferedImage[length];
    int i = 0;
    for (Animation anim : anims) {
      System.arraycopy(anim.frames, 0, frames, i, anim.frames.length);
      i += anim.frames.length;
    }
    return new Animation(frames, anims[0].timePerFrame);
  }
}