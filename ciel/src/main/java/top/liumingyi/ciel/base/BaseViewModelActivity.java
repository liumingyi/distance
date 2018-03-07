package top.liumingyi.ciel.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import io.reactivex.annotations.Nullable;

/**
 * baseActivity inject viewmodel<br>
 *
 * <p>
 * 针对使用
 * </p>
 *
 * Created by liumingyi on 2017/8/25.
 */

public abstract class BaseViewModelActivity<T extends BaseViewModel> extends BaseActivity {

  protected T viewModel;

  @CallSuper @Override public void inject() {
    viewModel = initInjector();
    Intent intent = getIntent();
    Bundle bundle = intent.getExtras();
    if (bundle == null) {
      bundle = new Bundle();
    }
    viewModel.init(bundle);
  }

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    dataBinding();
  }

  @Override protected void onStart() {
    super.onStart();
    if (viewModel != null) {
      viewModel.onStart();
    }
  }

  @Override protected void onResume() {
    super.onResume();
    if (viewModel != null) {
      viewModel.onResume();
    }
  }

  @Override protected void onPause() {
    super.onPause();
    if (viewModel != null) {
      viewModel.onPause();
    }
  }

  @Override protected void onStop() {
    super.onStop();
    if (viewModel != null) {
      viewModel.onStop();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (viewModel != null) {
      viewModel.onDestroy();
    }
  }

  protected abstract T initInjector();

  protected abstract void dataBinding();
}
