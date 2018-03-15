package top.liumingyi.distance.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SlideUpView extends FrameLayout {

  private boolean isOpen;
  private int extraHeight;

  public SlideUpView(Context context) {
    this(context, null);
  }

  public SlideUpView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public SlideUpView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    if (isOpen) {
      return;
    }
    initPosition();
  }

  private void initPosition() {
    float y = getY();
    setY(y + getHeight());
  }

  public void up() {
    animate().translationY(-extraHeight).setDuration(200);
    isOpen = true;
  }

  public void down() {
    animate().translationY(getHeight()).setDuration(200);
    isOpen = false;
  }

  public boolean isOpen() {
    return isOpen;
  }

  public void setExtraHeight(int extraHeight) {
    this.extraHeight = extraHeight;
  }
}
