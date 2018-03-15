package top.liumingyi.ciel.views;

import android.view.View;
import com.jakewharton.rxbinding2.view.RxView;
import io.reactivex.Observable;
import java.util.concurrent.TimeUnit;

/**
 * 点击事件,防连击设置
 * Created by liumingyi on 2018/3/19.
 */

public class TRxView {

  public static Observable<Object> clicks(View view) {
    return RxView.clicks(view).throttleFirst(500, TimeUnit.MICROSECONDS);
  }
}
