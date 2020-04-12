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
}