package top.liumingyi.distance.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Bundle;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import lombok.Getter;
import top.liumingyi.ciel.base.BaseViewModel;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.EventInfo;

public class EventsViewModel extends BaseViewModel {

  @Getter MutableLiveData<List<EventInfo>> recyclerUpdateLiveData = new MutableLiveData<>();

  private WeakReference<Context> context;

  public EventsViewModel(Context context) {
    this.context = new WeakReference<>(context);
  }

  @Override public void init(Bundle bundle) {
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

  /**
   * 生成初始时刻的默认倒计时项
   *
   * 1. "距离明年的时间"
   * 2. "距离最近的一个假期"
   * 3. "如果有个人信息，距离生日的时间"
   */
  public List<EventInfo> generateDefaultItems() {
    List<EventInfo> list = new ArrayList<>();

    // 1. 距离明年的时间
    Calendar ca1 = Calendar.getInstance();
    int nextYear = ca1.get(Calendar.YEAR) + 1;
    ca1.set(nextYear, 0, 1);
    EventInfo eventInfo1 = new EventInfo(context.get().getString(R.string.next_year), ca1);
    list.add(eventInfo1);
    // 2.
    return list;
  }
}
