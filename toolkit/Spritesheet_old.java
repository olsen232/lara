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

public class Spritesheet_old {

  static final Animation IDLE = new Animation(0, 14, 200);
  static final Animation AIM = new Animation(14, 4, 300);
  static final Animation RUN = new Animation(18, 13, 100);
  static final Animation RUN_AND_AIM = new Animation(31, 12, 100);
  static final Animation JUMP_UP = new Animation(43, 8, 200);
  static final Animation STAND_JUMP = new Animation(51, 9, 200);
  static final Animation STAND_JUMP_AND_AIM = new Animation(60, 8, 200);
  static final Animation FORWARD_JUMP_AND_AIM = new Animation(68, 7, 200);
  static final Animation LAYBACKING = new Animation(75, 9, 200);
  static final Animation JUMP_TO_LEDGE_GRAB = new Animation(84, 8, 200);
  static final Animation CLIMB_JUMP_CLIMB = new Animation(92, 11, 200);
  static final Animation SCRAMBLE_UP = new Animation(103, 17, 150);
  static final Animation SIDE_JUMP = new Animation(120, 7, 200);
  static final Animation HANG = new Animation(127, 1, 200);
  static final Animation CLIMB = new Animation(128, 9, 200);
  static final Animation DIE = new Animation(137, 9, 200);



  public static void main(String[] args) throws Exception  {
    BufferedImage im = ImageIO.read(new File("lara.png"));
    show(relayoutRows(im));
    //List<BufferedImage> frames = smartSplit(im);
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
  
  static class Animation {
    int first;
    int length;
    int speed;
    Animation(int first, int length, int speed) {
      this.first = first;
      this.length = length;
      this.speed = speed;
    }
  }
  
  static int f = 0;
  static ImageIcon anim;
  static BufferedImage[] animList;
  static JFrame frame;
  
  static void anim(final BufferedImage[] frames, final Animation a) {
    final JFrame frame = new JFrame();
    frame.setContentPane(new JPanel() {
      int f = 0;
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BufferedImage ff = frames[a.first + (f++) % a.length];
        g.drawImage(ff, 100 - ff.getWidth() / 2, 100 - ff.getHeight() / 2, ff.getWidth() * 2, ff.getHeight() * 2, null);
      }
    });
    frame.setBounds(50, 50, 500, 500);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    new Timer(a.speed, new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.repaint();
      }
    }).start();
    
    frame.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        frame.repaint();
      }
    });
  }

  static void show(BufferedImage... list) {
    JFrame frame = new JFrame();
    frame.getContentPane().setLayout(new FlowLayout());
    for (BufferedImage b : list) {
      frame.getContentPane().add(new JLabel("|", new ImageIcon(b), 0));
    }
    frame.setBounds(50, 50, 500, 500);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

}
