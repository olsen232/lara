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
      iframe.setBounds(50, 50, 500, 500);
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
          int x = 5;
          ((java.awt.Graphics2D)g).scale(2, 2);
          synchronized(anims) {
            for (Animation anim : anims) {
              g.drawImage(anim.tFrame(t), x += 50, 5, null);
            }
          }
        }
      };   
      aframe.setContentPane(panel);
      aframe.setBounds(50, 50, 500, 500);
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
}