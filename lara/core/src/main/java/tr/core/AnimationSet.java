package tr.core;

import java.util.HashMap;
import java.util.Map;

public class AnimationSet {   
  public static enum LoadState {
    START_LOADING,
    LOADED;
  }

  private static final Map<String, Image[]> FRAMES = new HashMap<>();

  private final String pathPrefix;
  private final LoadState loadState;


  public AnimationSet(String pathPrefix, LoadState loadState) {
    if (!pathPrefix.endsWith("/")) pathPrefix += "/";
    this.pathPrefix = pathPrefix;
    this.loadState = loadState;
  }

  public SimpleAnimation.Builder load(String pathSuffix, int numFrames) {
    if (!pathSuffix.endsWith(".png")) pathSuffix += ".png";
    String path = pathPrefix + pathSuffix;
    switch (loadState) {
      case START_LOADING:
        Image.load(path);
        return SimpleAnimation.builder();
      case LOADED:
        Image[] frames = loadFrames(path, numFrames);
        return SimpleAnimation.builder().frames(frames);
    }
    throw new RuntimeException("Bad state: " + loadState);
  }

  public static Image[] loadFrames(String path, int numFrames) {
    String key = path + "[" + numFrames + "]";
    Image[] result = FRAMES.get(key);
    if (result == null) {
      result = Image.load(path).tileInto(numFrames, 1);
      FRAMES.put(key, result);
    }
    return result;
  }

  public Animation sequence(Animation... parts) {
    return AnimationSequence.of(parts);
  }

  public Animation repeat(Animation part, int count) {
    return RepeatedAnimation.of(part, count);
  }

  public Animation leadIn(Animation leadIn, Animation loop) {
    return LeadInAnimation.of(leadIn, loop);
  }
}