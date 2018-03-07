package top.liumingyi.ciel.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * ViewModel fragment 基类
 * Created by liumingyi on 2017/8/25.
 */

public abstract class BaseViewModelFragment<T extends BaseViewModel> extends BaseFragment {

  private T viewModel;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    viewModel = initInjector();
    if (viewModel != null) {
      Bundle bundle = getArguments();
      if (bundle == null) {
        bundle = new Bundle();
      }
      viewModel.init(bundle);
    }
  }

  @Override public void onStart() {
    super.onStart();
    if (viewModel != null) {
      viewModel.onStart();
    }
  }

  @Override public void onResume() {
    super.onResume();
    if (viewModel != null) {
      viewModel.onResume();
    }
  }

  @Override public void onPause() {
    super.onPause();
    if (viewModel != null) {
      viewModel.onPause();
    }
  }

  @Override public void onStop() {
    super.onStop();
    if (viewModel != null) {
      viewModel.onStop();
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    if (viewModel != null) {
      viewModel.onDestroy();
    }
  }

  protected abstract T initInjector();
}
