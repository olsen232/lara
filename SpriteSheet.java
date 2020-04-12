import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;


public class SpriteSheet {

  public final List<Box> boxes;
  private List<BufferedImage> contents;

  public SpriteSheet(List<Box> boxes) {
    this.boxes = boxes;
  }

  public List<BufferedImage> contents() {
    if (this.contents == null) {
      this.contents = new ArrayList<>();
      for (Box box : boxes) {
        contents.add(box.content);
      }
    }
    return this.contents;
  }

  public static SpriteSheet load(BufferedImage in) {
    List<Box> boxes = new ArrayList<>();
    for (int x = 0; x < in.getWidth(); x++) {
      for (int y = 0; y < in.getHeight(); y++) {
        for (Box box : boxes) {
          if (box.contains(x, y)) {
            y = box.y + box.h;
            continue;
          }
        }
        if (ImageSplit.isVisiblePixel(in, x, y)) {
          boxes.add(findBox(in, x, y));
        }
      }
    }
    return new SpriteSheet(boxes);
  }

  static Box findBox(BufferedImage in, int x, int y) {
    int w; int h;
    for (w = 0; x + w < in.getWidth(); w++) {
      if (!ImageSplit.isVisiblePixel(in, x + w, y)) break;
    }
    for (h = 0; y + h < in.getHeight(); h++) {
      if (!ImageSplit.isVisiblePixel(in, x, y + h)) break;
    }
    int color = in.getRGB(x, y);
    BufferedImage content = in.getSubimage(x + 1, y + 1, w - 2, h - 2);
    return new Box(x, y, w, h, color, content);
  }

  static class Box {
    int x; int y; int w; int h; int color;
    BufferedImage content;

    public Box(int x, int y, int w, int h, int color, BufferedImage content) {
      this.x = x; this.y = y; this.w = w; this.h = h;
      this.color = color;
      this.content = content;
    }

    public boolean contains(int x, int y) {
      return this.x <= x
             && x <= this.x + this.w 
             && this.y <= y
             && y <= this.y + this.h;
    }

    public List<BufferedImage> splitIntoFrames() {
      List<BufferedImage> uncropped = new ArrayList<>();
      ImageSplit.horizontal(content, uncropped);
      List<BufferedImage> cropped = new ArrayList<>();
      for (BufferedImage uc : uncropped) {
        ImageSplit.vertical(uc, cropped);
      }
      return cropped;
    }
  }
}
