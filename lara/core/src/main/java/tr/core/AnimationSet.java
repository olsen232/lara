package tr.core;

import java.util.HashMap;
import java.util.Map;

public class AnimationSet {   
  public static enum LoadState {
    START_LOADING,
    LOADED;
  }

  private static final Map<String, Animation> ANIMATIONS = new HashMap<>();

  private final String pathPrefix;
  private final LoadState loadState;


  public AnimationSet(String pathPrefix, LoadState loadState) {
    if (!pathPrefix.endsWith("/")) pathPrefix += "/";
    this.pathPrefix = pathPrefix;
    this.loadState = loadState;
  }

  public Animation load(String pathSuffix, int numFrames) {
    if (!pathSuffix.endsWith(".png")) pathSuffix += ".png";
    String path = pathPrefix + pathSuffix;
    switch (loadState) {
      case START_LOADING:
        Image.load(path);
        return Animation.EMPTY;
      case LOADED:
        return loadAnimation(path, numFrames);
    }
    throw new RuntimeException("Bad state: " + loadState);
  }

  public static Animation loadAnimation(String path, int numFrames) {
    String key = path + "[" + numFrames + "]";
    Animation result = ANIMATIONS.get(key);
    if (result == null) {
      result = new Animation(Image.load(path).tileInto(numFrames, 1));
      ANIMATIONS.put(key, result);
    }
    return result;
  }

  public Animation sequence(Animation... parts) {
    return Animation.sequence(parts);
  }
}