package top.liumingyi.ciel.base;

import android.os.Bundle;
import io.reactivex.annotations.NonNull;

/**
 * 适配Activity，Fragment生命周期
 * Created by liumingyi on 2017/8/9.
 */

interface ViewModelLifecycle {

  void init(@NonNull Bundle bundle);

  void onStart();

  void onResume();

  void onPause();

  void onStop();

  void onDestroy();
}
