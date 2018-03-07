package top.liumingyi.ciel.base;

import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

/**
 * Eleting's RecyclerViewAdapter 基类<br>
 *
 * Created by liumingyi on 25/12/2016.
 */

public abstract class BaseRecyclerViewAdapter<VH extends RecyclerView.ViewHolder, D>
    extends RecyclerView.Adapter<VH> {

  /**
   * 装载数据的list
   */
  protected List<D> list;

  /**
   * 设置数据
   *
   * @param list 数据源list
   */
  public void setData(List<D> list) {
    this.list = new ArrayList<>(list);
  }

  @Override public int getItemCount() {
    return list == null ? 0 : list.size();
  }

  /**
   * 获取Item数据
   *
   * @param position item位置
   * @return 该item的数据
   */
  protected D getItem(int position) {
    return list == null || position >= list.size() || position < 0 ? null : list.get(position);
  }

  /**
   * 添加item
   *
   * @param item 要添加的item
   */
  public void addItem(D item) {
    if (list == null) {
      return;
    }
    list.add(item);
    notifyDataSetChanged();
  }

  /**
   * 添加item到固定位置
   *
   * @param position 添加的位置
   * @param item 要添加的item
   */
  public void addItem(int position, D item) {
    if (list == null) {
      return;
    }
    list.add(position, item);
    notifyDataSetChanged();
  }

  /**
   * 删除item
   *
   * @param position 要删除的item位置
   */
  public void remove(int position) {
    if (list == null) {
      return;
    }
    list.remove(position);
    notifyDataSetChanged();
  }

  /**
   * 删除item 带动画
   */
  public void removeWithAnimate(int position) {
    if (list == null) {
      return;
    }
    list.remove(position);
    notifyItemRemoved(position);
  }

  /**
   * 替换某个位置的item
   *
   * @param position 要替换的item位置
   * @param item 新的item数据
   */
  public void replace(int position, D item) {
    if (list == null) {
      return;
    }
    list.add(position, item);
    notifyDataSetChanged();
  }

  /**
   * 替换所有数据
   * <p>
   * 相比{@link #setData(List)} 多了刷新列表操作
   * </p>
   *
   * @param list 新的数据集合
   */
  public void replace(List<D> list) {
    this.list = new ArrayList<>(list);
    notifyDataSetChanged();
  }

  /**
   * 清空所有数据
   */
  public void clean() {
    if (list == null || list.isEmpty()) {
      return;
    }
    list.clear();
    notifyDataSetChanged();
  }

  /**
   * item点击监听
   */
  protected RecyclerItemClickListener<D> listener;

  /**
   * 设置item的点击事件
   *
   * @param listener item点击监听
   */
  public void setOnRecyclerItemClickListener(RecyclerItemClickListener<D> listener) {
    this.listener = listener;
  }

  /**
   * item点击事件接口
   *
   * @param <D> 数据类型
   */
  public interface RecyclerItemClickListener<D> {
    void onRecyclerItemClick(int position, D item);
  }

  /**
   * item长按监听
   */
  protected RecyclerItemLongClickListener<D> longClickListener;

  /**
   * 设置item长按监听
   *
   * @param longClickListener item长按监听
   */
  public void setOnRecyclerItemLongClickListener(
      RecyclerItemLongClickListener<D> longClickListener) {
    this.longClickListener = longClickListener;
  }

  /**
   * item长按监听接口
   *
   * @param <D> 数据类型
   */
  public interface RecyclerItemLongClickListener<D> {
    void onRecyclerItemLongClick(int position, D item);
  }
}
