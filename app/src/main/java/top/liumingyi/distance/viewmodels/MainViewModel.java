package top.liumingyi.distance.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Bundle;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Calendar;
import lombok.Getter;
import top.liumingyi.ciel.base.BaseViewModel;
import top.liumingyi.ciel.utils.TimeUtils;
import top.liumingyi.distance.R;
import top.liumingyi.distance.helpers.DatePickerHelper;

public class MainViewModel extends BaseViewModel {

  @Getter private MutableLiveData<String> todayLiveData = new MutableLiveData<>();

  @Getter private MutableLiveData<PickerData> yearLiveData = new MutableLiveData<>();
  @Getter private MutableLiveData<PickerData> monthLiveData = new MutableLiveData<>();
  @Getter private MutableLiveData<PickerData> dateLiveData = new MutableLiveData<>();
  @Getter private MutableLiveData<String> apartDaysLiveData = new MutableLiveData<>();

  private PickerData yearData = new PickerData();
  private PickerData monthData = new PickerData();
  private PickerData dayData = new PickerData();

  private int year;

  private WeakReference<Context> context;

  public MainViewModel(Context context) {
    this.context = new WeakReference<>(context);
  }

  @Override public void init(Bundle bundle) {
    initPickerData();
  }

  private DatePickerHelper helper = new DatePickerHelper();

  private void initPickerData() {

    String[] years = helper.getYears();
    String[] months = helper.getMonths();
    String[] days = helper.getCurrentDays();

    year = helper.getCurrentYear();
    int yearIndex = Arrays.asList(years).indexOf(String.valueOf(year));
    yearData.values = years;
    yearData.index = yearIndex;

    int month = helper.getCurrentMonth() + 1;//0~base
    int monthIndex = Arrays.asList(months).indexOf(String.valueOf(month));
    monthData.values = months;
    monthData.index = monthIndex;

    int day = helper.getCurrentDay();
    int dayIndex = Arrays.asList(days).indexOf(String.valueOf(day));
    dayData.values = days;
    dayData.index = dayIndex;

    yearLiveData.setValue(yearData);
    monthLiveData.setValue(monthData);
    dateLiveData.setValue(dayData);
    todayLiveData.setValue(helper.getToday());
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
   * 月份更新 -> 需要更新日期 -> 记录更新
   */
  public void updateMonth(int monthIndex) {
    monthData.index = monthIndex;
    updateDayValues(monthIndex);
  }

  /**
   * 根据月份变更月份包含的日期数
   */
  private void updateDayValues(int monthIndex) {
    dayData.values = helper.getDays(year, monthIndex);
    //如果当前选择的日期>新的月份的最大日期，则选择新月份的最大日期。
    //eg:7月31日,月份变更为6月,则自动选择为6月30日
    if (dayData.index > dayData.values.length - 1) {
      dayData.index = dayData.values.length - 1;
    }
    dateLiveData.setValue(dayData);
  }

  /**
   * 日期更新 -> 只要记录更新
   */
  public void updateDay(int dayIndex) {
    dayData.index = dayIndex;
  }

  /**
   * 年份更新 -> 只要记录更新
   */
  public void updateYear(int yearIndex) {
    yearData.index = yearIndex;
  }

  /**
   * 计算选中的日期距离今天的天数
   */
  public void calculateDays() {
    Calendar target = helper.getCalendar(yearData.index, monthData.index, dayData.index);
    Calendar now = Calendar.getInstance();
    long days = TimeUtils.calculateApartDays(now, target);
    notifyCalculateResult(days);
  }

  /**
   * 通知计算结果
   */
  private void notifyCalculateResult(long days) {
    String result;
    if (days == 0) {
      result = context.get().getString(R.string.is_today);
    } else {
      result = String.format(context.get().getString(R.string.apart_days), days);
    }
    apartDaysLiveData.setValue(result);
  }

  public class PickerData {
    public String[] values;
    public int index;
  }
}
