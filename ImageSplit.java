import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

public class ImageSplit {

  public static List<BufferedImage> horizontal(BufferedImage in, int cols) {
    List<BufferedImage> result = new ArrayList<>();
    horizontalTile(in, cols, result);
    return result;
  }

  public static void horizontalTile(BufferedImage in, int cols, List<BufferedImage> result) {
    tile(in, cols, 1, result);
  }

  public static List<BufferedImage> verticalTile(BufferedImage in, int rows) {
    List<BufferedImage> result = new ArrayList<>();
    verticalTile(in, rows, result);
    return result;
  }

  public static void verticalTile(BufferedImage in, int rows, List<BufferedImage> result) {
    tile(in, 1, rows, result);
  }

  public static List<BufferedImage> tile(BufferedImage in, int cols, int rows) {
    List<BufferedImage> result = new ArrayList<>();
    tile(in, cols, rows, result);
    return result;
  }

  public static void tile(BufferedImage in, int cols, int rows, List<BufferedImage> result) {
    int w = in.getWidth() / cols;
    int h = in.getHeight() / rows;
    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < cols; x++) {
        result.add(in.getSubimage(x * w, y * h, w, h));
      }
    }
  }
}