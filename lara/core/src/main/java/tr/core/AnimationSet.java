package tr.core;

import java.util.HashMap;
import java.util.Map;

public class AnimationSet<K extends Animation.Metadata> {
    
  private String path;
  private int size;
  private K[] keys;
  private Image[] raw;
  private Animation[] animations;

  public AnimationSet(String path, K[] keys) {
    this.path = path;
    this.keys = keys;
    this.size = keys.length;
    int maxKeyIndex = maxKeyIndex(keys);
    this.raw = new Image[maxKeyIndex + 1];
    this.animations = new Animation[maxKeyIndex + 1];
  }

  public void startLoading() {
    for (K key : keys) {
      raw[index(key)] = Image.load(path + "/" + file(key));
    }
  }

  public void finishLoading() {
    for (K key : keys) {
      int index = index(key);
      animations[index] = Animation.load(raw[index], key);
    }
  }

  public Image raw(K key) {
    return raw[index(key)];
  }

  public Animation get(K key) {
    return animations[index(key)];
  }

  public int size() {
    return size;
  }

  private int index(K key) {
    return ((Enum) key).ordinal();
  }

  private String file(K key) {
    return ((Enum) key).name().toLowerCase() + ".png";
  }

  private int maxKeyIndex(K[] keys) {
    int maxKeyIndex = -1;
    for (K key : keys) {
      maxKeyIndex = Math.max(maxKeyIndex, index(key));
    }
    return maxKeyIndex;
  }

  public Animation concat(K... keys) {
    Animation[] pieces = new Animation[keys.length];
    int i = 0;
    for (K key : keys) {
      pieces[i++] = get(key);
    }
    return Animation.concat(pieces);
  }
}
