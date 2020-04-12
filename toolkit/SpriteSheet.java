import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class SpriteSheet {


  public static void main(String[] args) throws Exception  {
    BufferedImage im = ImageIO.read(new File("lara.png"));
    List<BufferedImage> frames = smartSplit(im);

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int i = 0;
    Map<Integer, List<BufferedImage>> map = new HashMap<>();
    for (BufferedImage f : frames) {
      show(f);
      String s = br.readLine();
      try {
        i = Integer.parseInt(s);
      } catch (Exception e) {}
      if (!map.containsKey(i)) {
        map.put(i, new ArrayList<BufferedImage>());
      }
      map.get(i).add(f);
    }
    List<BufferedImage> result = new ArrayList<>();
    for (int j = 0; j < 100; j++) {
      if (map.containsKey(j)) {
        result.add(hconcat(map.get(j), 8));
      }
    }
    BufferedImage fin = vconcat(result, 8);
    show(fin);
    ImageIO.write(fin, "png", new File("finish.png"));
    //BufferedImage result = dolayout(frames, 440, 483);
    //show(result);

  }
  
  static int maxX, maxY;
  static int calcArea(List<BufferedImage> list, int width) {
    int x = 0, y = 0;
    maxX = 0;
    maxY = 0;
    for (BufferedImage im : list) {
      if (x + im.getWidth() > width) {
        x = 0;
        y = maxY + 2;
      } else {
        maxX = Math.max(maxX, x + im.getWidth());
        maxY = Math.max(maxY, y + im.getHeight());
        x += im.getWidth() + 2;
      }
    }
    return maxX * maxY;
  }
  
  static BufferedImage dolayout(List<BufferedImage> list, int width, int height) {
    BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    Graphics g = result.getGraphics();
    int x = 0, y = 0;
    maxX = 0;
    maxY = 0;
    for (BufferedImage im : list) {
      if (x + im.getWidth() > width) {
        x = 0;
        y = maxY + 2;
      } else {
        g.fillRect(x, y, im.getWidth(), im.getHeight());
        g.drawImage(im, x, y, null);
        maxX = Math.max(maxX, x + im.getWidth());
        maxY = Math.max(maxY, y + im.getHeight());
        x += im.getWidth() + 2;
      }
    }
    return result;
  }

  static List<BufferedImage> smartSplit(BufferedImage im) {
    ArrayList<BufferedImage> rows = new ArrayList<>();
    smartSplitVertical(im, rows);
    ArrayList<BufferedImage> uncropped = new ArrayList<>();
    for (BufferedImage r : rows) {
      smartSplitHorizontal(r, uncropped);
    }
    ArrayList<BufferedImage> cropped = new ArrayList<>();
    for (BufferedImage u : uncropped) {
      smartSplitVertical(u, cropped);
    }
    return cropped;
  }

  static BufferedImage relayoutRows(BufferedImage im) {
    ArrayList<BufferedImage> rows = new ArrayList<>();
    ArrayList<BufferedImage> rows2 = new ArrayList<>();

    smartSplitVertical(im, rows);
    for (BufferedImage r : rows) {
      ArrayList<BufferedImage> uncropped = new ArrayList<>();
      smartSplitHorizontal(r, uncropped);
      ArrayList<BufferedImage> cropped = new ArrayList<>();
      for (BufferedImage u : uncropped) {
        smartSplitVertical(u, cropped);
      }
      rows2.add(hconcat(cropped, 8));
    }
    return vconcat(rows2, 8);
  }

  static BufferedImage hconcat(List<BufferedImage> in, int margin) {
    int w = margin;
    int h = 0;
    for (BufferedImage i : in) {
      w += i.getWidth() + margin;
      h = Math.max(h, i.getHeight());
    }
    BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = result.getGraphics();
    int x = margin;
    for (BufferedImage i : in) {
      g.drawImage(i, x, (h - i.getHeight()) / 2, null);
      x += i.getWidth() + margin;
    }
    g.dispose();
    return result;
  }

  static BufferedImage vconcat(List<BufferedImage> in, int margin) {
    int h = margin;
    int w = 0;
    for (BufferedImage i : in) {
      h += i.getHeight() + margin;
      w = Math.max(w, i.getWidth());
    }
    BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics g = result.getGraphics();
    int y = margin;
    for (BufferedImage i : in) {
      g.drawImage(i, (w - i.getWidth()) / 2, y, null);
      y += i.getHeight() + margin;
    }
    g.dispose();
    return result;
  }

  static void smartSplitHorizontal(BufferedImage im, List<BufferedImage> result) {
    int visibleCols = 0;
    for (int x = 0; x <= im.getWidth(); x++) {
      if (isVisibleCol(im, x)) {
        visibleCols++;
      } else {
        if (visibleCols != 0) {
          result.add(im.getSubimage(x - visibleCols, 0, visibleCols, im.getHeight()));
          visibleCols = 0;
        }
      }
    }
  }

  static boolean isVisibleCol(BufferedImage im, int x) {
    if (x < 0 || x >= im.getWidth()) return false;
    for (int y = 0; y < im.getHeight(); y++) {
      if (isVisiblePixel(im, x, y)) return true;
    }
    return false;
  }

  static void smartSplitVertical(BufferedImage im, List<BufferedImage> result) {
    int visibleRows = 0;
    for (int y = 0; y <= im.getHeight(); y++) {
      if (isVisibleRow(im, y)) {
        visibleRows++;
      } else {
        if (visibleRows != 0) {
          result.add(im.getSubimage(0, y - visibleRows, im.getWidth(), visibleRows));
          visibleRows = 0;
        }
      }
    }
  }

  static boolean isVisibleRow(BufferedImage im, int y) {
    if (y < 0 || y >= im.getHeight()) return false;
    for (int x = 0; x < im.getWidth(); x++) {
      if (isVisiblePixel(im, x, y)) return true;
    }
    return false;
  }

  static boolean isVisiblePixel(BufferedImage im, int x, int y) {
    return (im.getRGB(x, y) & 0xc0000000) != 0;
  }


  static JFrame frame = null; 

  static void show(BufferedImage b) {
    if (frame == null) {
      frame = new JFrame();
      frame.getContentPane().setLayout(new FlowLayout());
      frame.setBounds(50, 50, 500, 500);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    frame.getContentPane().add(new JLabel("|", new ImageIcon(b), 0));
    frame.getContentPane().doLayout();
    frame.getContentPane().repaint();
    frame.repaint();
  }

}
