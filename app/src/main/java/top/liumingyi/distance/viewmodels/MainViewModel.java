package top.liumingyi.distance.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import lombok.Getter;
import lombok.Setter;
import top.liumingyi.ciel.base.BaseViewModel;

public class MainViewModel extends BaseViewModel {

  @Getter private MutableLiveData<String> todayLiveData = new MutableLiveData<>();

  @Setter @Getter private int calculateToolId;

  public MainViewModel() {
  }

  @Override public void init(Bundle bundle) {
    Calendar calendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    String today = dateFormat.format(calendar.getTime());
    todayLiveData.setValue(today);
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
