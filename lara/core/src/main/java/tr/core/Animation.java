package tr.core;

public interface Animation {
    public int length();
    public int tLength();

    public Image frame(int f);
    public Image tFrame(int t);

    public boolean tick();
    public void restart();
    public Image currentFrame();
}