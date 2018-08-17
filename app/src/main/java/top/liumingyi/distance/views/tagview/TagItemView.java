package top.liumingyi.distance.views.tagview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import top.liumingyi.distance.R;
import top.liumingyi.distance.tools.Utils;

/**
 * 需求:
 * 1. 字体大小,字体颜色,item背景颜色,item最小尺寸在xml中的设置
 * 2. 点击事件
 * 3. 删除
 */
@SuppressLint("ViewConstructor") public class TagItemView
    extends android.support.v7.widget.AppCompatTextView {

  private static final int MIN_WIDTH = (int) Utils.dpToPixel(50);
  private static final int HORIZONTAL_PADDING = (int) Utils.dpToPixel(8);
  private static final int VERTICAL_PADDING = (int) Utils.dpToPixel(4);

  private float textWidth;

  public TagItemView(Context context, String text) {
    super(context);
    init(text);
  }

  private void init(String text) {

    setTextSize(Utils.dpToPixel(4.5f));
    setText(text);

    Paint paint = new Paint();
    Rect rect = new Rect();
    paint.getTextBounds(text, 0, text.length(), rect);
    textWidth = rect.width();

    setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_keyword));
    ViewGroup.MarginLayoutParams marginLayoutParams =
        new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT);
    setLayoutParams(marginLayoutParams);

    setPadding(HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING);

    setMaxLines(1);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int width = getMeasuredWidth();

    if (width < MIN_WIDTH) {
      width = MIN_WIDTH;
      setPadding((int) (width - textWidth) / 2, VERTICAL_PADDING, (int) (width - textWidth) / 2,
          VERTICAL_PADDING);
    }

    setMeasuredDimension(width, getMeasuredHeight());
  }
}
