package top.liumingyi.distance.views.tagview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import top.liumingyi.distance.tools.Utils;

/**
 * 标签布局
 *
 * 展示快捷选项标签
 */
public class TagLayout extends ViewGroup {

  private static final int MARGIN = (int) Utils.dpToPixel(8);

  private ItemClickListener itemClickListener;

  private Rect[] childrenBounds;

  public TagLayout(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthUsed = 0;//使用的宽
    int heightUsed = 0; //使用的高,PS:
    int childCount = getChildCount();
    int lineHeight = 0; //每一行高度
    int lineWidth = 0;

    // 创建 子View的Bounds
    if (childrenBounds == null) {
      childrenBounds = new Rect[childCount];
    } else if (childrenBounds.length < childCount) {
      childrenBounds = Arrays.copyOf(childrenBounds, childCount);
    }

    for (int i = 0; i < childCount; i++) {
      View child = getChildAt(i);

      //设置Id
      child.setTag(i);

      Rect childBounds = childrenBounds[i];

      lineWidth += MARGIN;

      // 测量子View,设置高宽的起始点
      measureChildWithMargins(child, widthMeasureSpec, lineWidth, heightMeasureSpec,
          MARGIN + heightUsed);

      // 判断换行
      if (MeasureSpec.getMode(widthMeasureSpec) != MeasureSpec.UNSPECIFIED
          && MeasureSpec.getSize(widthMeasureSpec)
          < lineWidth + child.getMeasuredWidth() + MARGIN) {

        lineWidth = MARGIN;
        heightUsed += lineHeight;
        lineHeight = 0;
        measureChildWithMargins(child, widthMeasureSpec, lineWidth, heightMeasureSpec, heightUsed);
      }

      if (childBounds == null) {
        childBounds = childrenBounds[i] = new Rect();
      }

      childBounds.set(lineWidth, MARGIN + heightUsed, lineWidth + child.getMeasuredWidth(),
          MARGIN + heightUsed + child.getMeasuredHeight());

      lineWidth += child.getMeasuredWidth();
      lineHeight = Math.max(lineHeight, child.getMeasuredHeight() + MARGIN);
      widthUsed = Math.max(widthUsed, lineWidth);
    }

    // 父View的高宽,这个margin是尾巴上的。高里面已经包含了margin
    int width = widthUsed + MARGIN;
    int height = heightUsed + lineHeight + MARGIN;

    setMeasuredDimension(resolveSizeAndState(width, widthMeasureSpec, 0),
        resolveSizeAndState(height, heightMeasureSpec, 0));
  }

  @Override protected void onLayout(boolean changed, int l, int t, int r, int b) {
    for (int i = 0, length = getChildCount(); i < length; i++) {
      View child = getChildAt(i);
      Rect rect;
      rect = childrenBounds[i];
      child.layout(rect.left, rect.top, rect.right, rect.bottom);
    }
  }

  private List<TagItemView> getTagItemViews(List<String> data) {
    List<TagItemView> tvList = new ArrayList<>();

    for (String datum : data) {
      TagItemView itemView = createTextView(datum);
      tvList.add(itemView);
    }

    return tvList;
  }

  private TagItemView createTextView(String text) {
    TagItemView itemView = new TagItemView(getContext(), text);
    itemView.setOnClickListener(v -> {
      if (itemClickListener != null) {
        itemClickListener.onItemClick((int) v.getTag(), text);
      }
    });
    return itemView;
  }

  public void setData(List<String> data) {
    if (data == null || data.size() == 0) {
      return;
    }
    List<TagItemView> children = getTagItemViews(data);
    for (TagItemView child : children) {
      addView(child);
    }
    requestLayout();
  }

  public void setOnItemClickListener(ItemClickListener l) {
    this.itemClickListener = l;
  }

  public void removeItem(int position) {
    removeViewAt(position);
  }

  public interface ItemClickListener {
    void onItemClick(int position, String text);
  }
}
