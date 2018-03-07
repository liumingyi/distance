package top.liumingyi.ciel.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import top.liumingyi.ciel.RxBus;

/**
 * 本项目的基类fragment<br>
 *
 * Created by liumingyi on 13/12/2016.
 *
 * @author liumingyi
 * @version 1.0
 */

public abstract class BaseFragment extends Fragment {

  /**
   * ButterKnife绑定view时返回的binder对象
   *
   * @see Unbinder
   */
  private Unbinder unbinder;

  /**
   * 输入法管理器
   */
  private InputMethodManager inputMethodManager;

  /**
   * {@link RxBus}的subscription对象,用于订阅和取消订阅
   */
  private Disposable rxBusSubscribe;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getLayoutResID(), container, false);
    view.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        hideKeyboard(v);
      }
    });
    return view;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    initVariables();
    initView(savedInstanceState);
    dataBinding();
  }

  @Override public void onResume() {
    super.onResume();
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);
  }

  @Override public void onPause() {
    super.onPause();
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    unregisterRxBus();
  }

  /**
   * 初始化RxBus
   */
  protected void registerRxBus() {
    rxBusSubscribe = RxBus.getDefault().toObservable().subscribe(new Consumer<Object>() {
      @Override public void accept(@NonNull Object event) throws Exception {
        rxBusEventReceive(event);
      }
    });
  }

  /**
   * RxBus Event 消息接收
   */
  protected void rxBusEventReceive(Object event) {

  }

  /**
   * 取消RxBus注册
   */
  protected void unregisterRxBus() {
    if (rxBusSubscribe != null) {
      rxBusSubscribe.dispose();
    }
  }

  /**
   * 隐藏输入法
   *
   * @param v 相应的view
   */
  private void hideKeyboard(View v) {
    if (inputMethodManager == null) {
      inputMethodManager =
          (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
    }
    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
  }

  /**
   * 获取布局ID
   *
   * @return 布局ID
   */
  protected abstract int getLayoutResID();

  /**
   * 初始化参数
   */
  protected abstract void initVariables();

  /**
   * 初始化控件
   *
   * @param savedInstanceState 来自{{@link #onActivityCreated(Bundle)}}方法
   * @see Fragment#onActivityCreated(Bundle)
   */
  protected abstract void initView(Bundle savedInstanceState);

  /**
   * 数据与view绑定
   */
  protected abstract void dataBinding();
}
