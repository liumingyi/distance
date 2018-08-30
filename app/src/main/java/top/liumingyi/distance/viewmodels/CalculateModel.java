package top.liumingyi.distance.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import lombok.Getter;
import top.liumingyi.distance.tools.DateUtils;
import top.liumingyi.tang.base.BaseViewModel;

public class CalculateModel extends BaseViewModel {

  @Getter private MutableLiveData<String> todayLiveData = new MutableLiveData<>();

  public CalculateModel() {
  }

  @Override public void init(Bundle bundle) {
    todayLiveData.setValue(DateUtils.getToday());
  }

  @Override public void onStart() {

  }

  @Override public void onResume() {

  }

  @Override public void onPause() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }
}
