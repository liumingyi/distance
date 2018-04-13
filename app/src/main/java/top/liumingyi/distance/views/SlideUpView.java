package top.liumingyi.distance.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SlideUpView extends FrameLayout {

  private boolean isOpen;
  private int extraHeight;
  private StateChangeListener listener;

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
    if (listener != null) {
      listener.open();
    }
  }

  public void down() {
    animate().translationY(getHeight()).setDuration(200);
    isOpen = false;
    if (listener != null) {
      listener.closed();
    }
  }

  public boolean isOpen() {
    return isOpen;
  }

  public void toggle() {
    if (isOpen) {
      down();
    } else {
      up();
    }
  }

  public void setExtraHeight(int extraHeight) {
    this.extraHeight = extraHeight;
  }

  public interface StateChangeListener {
    void open();

    void closed();
  }

  public void setOpenStateChangeListener(StateChangeListener listener) {
    this.listener = listener;
  }
}
