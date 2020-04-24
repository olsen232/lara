package tr.core;

public class Canvas implements DrawImage {
  public final playn.core.Canvas raw;
  public final Image image;
  
  public Canvas(playn.core.Canvas raw) {
    this.raw = raw;
    this.image = new Image(raw.image);
  }
  
  public void clear() { raw.clear(); }
  
  public void draw(Image image, int x, int y) {
    raw.draw(image.raw, x, y);
  }
  
  public void fillRect(int x, int y, int w, int h, int color) {
    raw.setFillColor(color);
    raw.fillRect(x, y, w, h);
  }
  
  public void fillRectPlusBorder(int x, int y, int w, int h, int color, int b) {
    int b2 = 2 * b;
    fillRect(x - b, y - b, w + b2, h + b2, color);
  }
  
  public void strokeRect(int x, int y, int w, int h, int color) {
    raw.setFillColor(color);
    raw.fillRect(x, y, w, 1);
    raw.fillRect(x, y + h - 1, w, 1);
    raw.fillRect(x, y, 2, h);
    raw.fillRect(x + w - 2, y, 2, h);
  }
  
  public void strokeRectPlusBorder(int x, int y, int w, int h, int color, int b) {
    int b2 = 2 * b;
    strokeRect(x - b, y - b, w + b2, h + b2, color);
  }
  
  public Image snapshot() {
    return new Image(raw.snapshot());
  }
}