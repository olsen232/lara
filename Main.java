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
    for (SpriteSheet.Box b : spriteSheet.boxes) {
      Animation anim = new Animation(b.splitIntoFrames(), 2);
      //Show.show(anim);

      ImageOutputStream output = 
        new FileImageOutputStream(new File((i++) + ".gif"));
      
      GifSequenceWriter writer = 
        new GifSequenceWriter(output, BufferedImage.TYPE_INT_ARGB, 1, false);
      
      for (BufferedImage f : anim.frames) {
        writer.writeToSequence(f);
      }
      
      writer.close();
      output.close();  
    }
  }
}