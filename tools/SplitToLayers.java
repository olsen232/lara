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

public class SplitToLayers {

  public static void main(String[] args) throws Exception {
    BufferedImage im = ImageIO.read(new File(args[0]));
    im = AutoImageSplit.verticalCrop(im);
    List<BufferedImage> frames = AutoImageSplit.horizontal(im);
    writeToGif(frames, gifName(args[0]));
  }

  private static String gifName(String name) {
    int dotIndex = name.lastIndexOf(".");
    if (dotIndex != -1) {
      name = name.substring(0, dotIndex);
    }
    return name + ".gif";
  }

  private static void writeToGif(List<BufferedImage> frames, String filename) throws Exception {
      ImageOutputStream output = new FileImageOutputStream(new File(filename));   
      GifSequenceWriter writer = new GifSequenceWriter(output, BufferedImage.TYPE_INT_ARGB, 1, false);
      writer.writeToSequence(allocateCavas(frames));
      for (BufferedImage f : frames) {
        writer.writeToSequence(f);
      }
      writer.close();
      output.close();
  }

  private static BufferedImage allocateCavas(List<BufferedImage> frames) {
    int w = 0;
    int h = 0;
    for (BufferedImage f : frames) {
      w = Math.max(w, f.getWidth());
      h = Math.max(h, f.getHeight());
    }
    BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = result.getGraphics();
    g.fillRect(0, 0, w, h);
    return result;
  }
}