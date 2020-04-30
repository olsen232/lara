import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

public class AutoImageSplit {

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

  public static List<BufferedImage> horizontalTile(BufferedImage in) {
    List<BufferedImage> result = new ArrayList<>();
    horizontalTile(in, result);
    return result;
  }

  public static void horizontalTile(BufferedImage in, List<BufferedImage> result) {
    int visibleCols = 0;
    int visibleChunks = 0;
    for (int x = 0; x <= in.getWidth(); x++) {
      if (isVisibleCol(in, x)) {
        visibleCols++;
      } else {
        if (visibleCols != 0) {
          visibleChunks++;
          visibleCols = 0;
        }
      }
    }
    ImageSplit.horizontalTile(in, visibleChunks, result);
  }

  public static BufferedImage horizontalCrop(BufferedImage in) {
    int startX; int stopX;
    for (startX = 0; startX <= in.getHeight(); startX++) {
      if (!isVisibleRow(in, startX)) continue;
      break;
    }
    if (startX == in.getHeight()) return in;
    for (stopX = in.getHeight() - 1; stopX > startX; stopX--) {
      if (!isVisibleRow(in, stopX)) continue;
      break;
    }
    return in.getSubimage(startX, 0, stopX - startX + 1, in.getHeight());
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

  public static List<BufferedImage> verticalTile(BufferedImage in) {
    List<BufferedImage> result = new ArrayList<>();
    verticalTile(in, result);
    return result;
  }

  public static void verticalTile(BufferedImage in, List<BufferedImage> result) {
    int visibleRows = 0;
    int visibleChunks = 0;
    for (int y = 0; y <= in.getHeight(); y++) {
      if (isVisibleRow(in, y)) {
        visibleRows++;
      } else {
        if (visibleRows != 0) {
          visibleChunks++;
          visibleRows = 0;
        }
      }
    }
    ImageSplit.verticalTile(in, visibleChunks, result);
  }

  public static BufferedImage verticalCrop(BufferedImage in) {
    int startY; int stopY;
    for (startY = 0; startY <= in.getHeight(); startY++) {
      if (!isVisibleRow(in, startY)) continue;
      break;
    }
    if (startY == in.getHeight()) return in;
    for (stopY = in.getHeight() - 1; stopY > startY; stopY--) {
      if (!isVisibleRow(in, stopY)) continue;
      break;
    }
    return in.getSubimage(0, startY, in.getWidth(), stopY - startY + 1);
  }

  public static boolean isVisibleCol(BufferedImage in, int x) {
    if (x < 0 || x >= in.getWidth()) return false;
    for (int y = 0; y < in.getHeight(); y++) {
      if (isVisiblePixel(in, x, y)) return true;
    }
    return false;
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