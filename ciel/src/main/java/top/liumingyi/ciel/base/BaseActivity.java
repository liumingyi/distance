package top.liumingyi.ciel.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import top.liumingyi.ciel.RxBus;

/**
 * 基类Activity<br>
 * 功能描述:<br>
 * <p>
 * 本项目的基类Activity
 * </p>
 * <br> Created by liumingyi on 05/12/2016.
 *
 * @author liumingyi
 * @version 1.0
 */

public abstract class BaseActivity extends AppCompatActivity {

  /**
   * 输入法管理对象
   */
  private InputMethodManager inputMethodManager;

  /**
   * Butterknife
   */
  private Unbinder unbinder;

  /**
   * {@link RxBus}的subscription对象,用于订阅和取消订阅
   */
  private Disposable rxBusSubscribe;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResID());
    inject();
    initVariables();
    initViews(savedInstanceState);
  }

  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    //adaptation();
    bindViews();
  }

  /**
   * 初始化RxBus对象
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
   * 适配,Android 5.0 以上渲染 status bar
   */
  private void adaptation() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      //使statusBar透明
      getWindow().setStatusBarColor(Color.TRANSPARENT);
      Window window = this.getWindow();

      // clear FLAG_TRANSLUCENT_STATUS flag:
      window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

      // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
      window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

      // finally change the color
      //window.setStatusBarColor(Color.RED);
      window.getDecorView()
          .setSystemUiVisibility(
              View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
  }

  /**
   * 绑定空件
   */
  private void bindViews() {
    unbinder = ButterKnife.bind(this);
  }

  /**
   * 所有之类在重写此方法时，其中包含的自定义view如果有相应的生命周期处理，
   * 要在super.onDestroy()以前调用，否则view在unbinder.unbind()后就为null了
   */
  @Override protected void onDestroy() {
    super.onDestroy();
    if (unbinder != null) {
      unbinder.unbind();
    }
    unregisterRxBus();
  }

  /**
   * 添加Fragment
   *
   * @param fragment 目标fragment
   * @param resId 布局ID
   * @param tag 标志位
   */
  protected void addFragment(Fragment fragment, int resId, String tag) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(resId, fragment, tag).commit();
  }

  /**
   * 替换fragment
   *
   * @param fragment 目标fragment
   * @param resId fragment要放置的布局ID
   * @param tag 标识位
   */
  protected void replaceFragment(Fragment fragment, int resId, String tag) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(resId, fragment, tag);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }

  /**
   * 添加fragment
   *
   * @param fragment 目标fragment
   * @param resId fragment要放置的布局ID
   */
  protected void addFragment(Fragment fragment, int resId) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(resId, fragment).commit();
  }

  /**
   * 替换fragment
   *
   * @param fragment 目标fragment
   * @param resId fragment要放置的布局ID
   */
  protected void replaceFragment(Fragment fragment, int resId) {
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(resId, fragment);
    fragmentTransaction.addToBackStack(null);
    fragmentTransaction.commit();
  }

  /**
   * 从fragment栈中推出Fragment
   */
  protected void popFragment() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.popBackStack();
  }

  /**
   * 显示软键盘
   */
  protected void showKeyboard(View v) {
    if (inputMethodManager == null) {
      inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    }
    if (inputMethodManager != null) {
      inputMethodManager.showSoftInput(v, 0);
    }
  }

  /**
   * 隐藏键盘
   */
  protected void hideKeyboard(View v) {
    if (inputMethodManager == null) {
      inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
    }
    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
  }

  /**
   * 获取布局ID
   *
   * @return 布局的ID
   */
  public abstract int getLayoutResID();

  /**
   * 注入方法
   */
  public abstract void inject();

  /**
   * 初始化变量的方法
   */
  public abstract void initVariables();

  /**
   * 初始化控件的方法
   */
  public abstract void initViews(Bundle savedInstanceState);
}
