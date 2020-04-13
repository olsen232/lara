import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;

public class Show {

  private static JFrame iframe = null; 

  public static void show(BufferedImage b) {
    if (iframe == null) {
      iframe = new JFrame();
      iframe.getContentPane().setLayout(new FlowLayout());
      iframe.setBounds(50, 50, 1000, 500);
      iframe.setVisible(true);
      iframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    iframe.getContentPane().add(new JLabel("|", new ImageIcon(b), 0));
    iframe.getContentPane().doLayout();
    iframe.getContentPane().repaint();
    iframe.repaint();
  }

  private static JFrame aframe;
  private static int t = 0;
  private static List<Animation> anims = new ArrayList<>();

  public static void show(Animation anim) {
    if (aframe == null) {
      aframe = new JFrame();
      final JPanel panel = new JPanel() {
        @Override
        public void paint(Graphics g) {
          super.paint(g);
          int x = 25;
          ((java.awt.Graphics2D)g).scale(2, 2);
          synchronized(anims) {
            for (Animation anim : anims) {
              BufferedImage f = anim.tFrame(t);
              boolean flip = (t / anim.tLength()) % 2 == 1;
              drawCentered(g, f, (x+=50), 100, flip);
            }
          }
        }
      };   
      aframe.setContentPane(panel);
      aframe.setBounds(50, 50, 1000, 500);
      aframe.setVisible(true);
      aframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          t++;
          panel.repaint();
        }
      }).start();
    }
    synchronized(anims) {
      anims.add(anim);
    }
  }

  private static void drawCentered(Graphics g, BufferedImage b, int x, int y, boolean flip) {
    if (flip) drawFlippedCentered(g, b, x, y);
    else drawCentered(g, b, x, y);
  }

  private static void drawCentered(Graphics g, BufferedImage b, int x, int y) {
    g.drawImage(b, x - b.getWidth() / 2, y - b.getHeight() / 2, null);
  }

  private static void drawFlippedCentered(Graphics g, BufferedImage b, int x, int y) {
    int w = b.getWidth();
    int h = b.getHeight();
    ((Graphics2D) g).drawImage(b, x + w / 2, y - h / 2, -w, h, null);
  }
}