import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;
import java.io.File;

public class Main {

  public static void main(String[] args) throws Exception {
    BufferedImage im = ImageIO.read(new File("lara_main.png"));
    SpriteSheet spriteSheet = SpriteSheet.load(im);
    int i = 0;
    List<Animation> anims = new ArrayList<>();
    for (SpriteSheet.Box b : spriteSheet.boxes) {
      anims.add(new Animation(b.splitIntoFrames(), 2));
    }
    Animation[] a = anims.toArray(new Animation[0]);
    anims.add(Animation.concat(a[0], a[0], a[1], a[0], a[3], a[4], a[4], a[3], a[0], a[2]));
    //anims.add(Animation.concat(anims.get(0), anims.get(2), anims.get(3), anims.get(2)));
    for (Animation anim : anims) {
      Show.show(anim);
    }
  }

  private static void writeToGif(Animation anim, String filename) throws Exception {
      ImageOutputStream output = new FileImageOutputStream(new File(filename));   
      GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_ARGB, 1, false);
      writer.writeToSequence(allocateCavas(anim));
      for (BufferedImage f : anim.frames) {
        writer.writeToSequence(f);
      }
      writer.close();
      output.close();
  }

  private static BufferedImage allocateCavas(Animation anim) {
    int w = 0;
    int h = 0;
    for (BufferedImage f : anim.frames) {
      w = Math.max(w, f.getWidth());
      h = Math.max(h, f.getHeight());
    }
    BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = result.getGraphics();
    g.fillRect(0, 0, w, h);
    return result;
  }
}