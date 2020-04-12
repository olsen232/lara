import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

public class ImageSplit {

  public static List<BufferedImage> horizontal(BufferedImage in) {
    List<BufferedImage> result = new ArrayList<>();
    horizontal(in, result);
    return result;
  }

  public static void horizontal(BufferedImage in, List<BufferedImage> result) {
    int visibleCols = 0;
    for (int x = 0; x <= in.getWidth(); x++) {
      if (isVisibleCol(in, x)) {
        visibleCols++;
      } else {
        if (visibleCols != 0) {
          result.add(in.getSubimage(x - visibleCols, 0, visibleCols, in.getHeight()));
          visibleCols = 0;
        }
      }
    }
  }

  public static boolean isVisibleCol(BufferedImage in, int x) {
    if (x < 0 || x >= in.getWidth()) return false;
    for (int y = 0; y < in.getHeight(); y++) {
      if (isVisiblePixel(in, x, y)) return true;
    }
    return false;
  }

  public static List<BufferedImage> vertical(BufferedImage in) {
    List<BufferedImage> result = new ArrayList<>();
    vertical(in, result);
    return result;
  }

  public static void vertical(BufferedImage in, List<BufferedImage> result) {
    int visibleRows = 0;
    for (int y = 0; y <= in.getHeight(); y++) {
      if (isVisibleRow(in, y)) {
        visibleRows++;
      } else {
        if (visibleRows != 0) {
          result.add(in.getSubimage(0, y - visibleRows, in.getWidth(), visibleRows));
          visibleRows = 0;
        }
      }
    }
  }

  public static boolean isVisibleRow(BufferedImage in, int y) {
    if (y < 0 || y >= in.getHeight()) return false;
    for (int x = 0; x < in.getWidth(); x++) {
      if (isVisiblePixel(in, x, y)) return true;
    }
    return false;
  }

  public static boolean isVisiblePixel(BufferedImage in, int x, int y) {
    return (in.getRGB(x, y) & 0xff000000) != 0;
  }

}