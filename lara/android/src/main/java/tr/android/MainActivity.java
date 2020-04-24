package tr.android;

import playn.android.GameActivity;

import tr.core.Main;

public class MainActivity extends GameActivity {

  @Override public void main () {
    new Main(platform());
  }
}
