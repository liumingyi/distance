package top.liumingyi.distance.views;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.NumberPicker;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.Calendar;
import top.liumingyi.ciel.utils.TimeUtils;
import top.liumingyi.distance.R;
import top.liumingyi.distance.helpers.DatePickerHelper;

/**
 * 计算与今天相距多少天的控件
 * Created by liumingyi on 2018/4/16.
 */

public class PickerView extends ConstraintLayout {

  NumberPicker yearPicker;
  NumberPicker monthPicker;
  NumberPicker dayPicker;
  TextView apartDaysTv;

  WeakReference<Context> context;

  DatePickerHelper helper = new DatePickerHelper();

  int yearIndex;
  int monthIndex;//0~11
  int dayIndex;

  public PickerView(Context context) {
    this(context, null);
  }

  public PickerView(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public PickerView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  private void init(Context context) {
    this.context = new WeakReference<>(context);
    LayoutInflater layoutInflater =
        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    if (layoutInflater == null) {
      throw new RuntimeException("Error: can not get layout inflater service");
    }
    layoutInflater.inflate(R.layout.picker_layout, this, true);
  }

  @Override protected void onFinishInflate() {
    super.onFinishInflate();
    yearPicker = findViewById(R.id.year_picker);
    monthPicker = findViewById(R.id.month_picker);
    dayPicker = findViewById(R.id.day_picker);
    apartDaysTv = findViewById(R.id.apart_days_tv);
    initPickerData();
    initDatePickerListeners();
  }

  private void initDatePickerListeners() {
    yearPicker.setWrapSelectorWheel(false);
    monthPicker.setWrapSelectorWheel(false);
    dayPicker.setWrapSelectorWheel(false);
    yearPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateYear(oldVal, newVal));
    monthPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateMonth(oldVal, newVal));
    dayPicker.setOnValueChangedListener((picker, oldVal, newVal) -> updateDay(newVal));
  }

  private void initPickerData() {

    String[] years = helper.getYears();
    String[] months = helper.getMonths();
    String[] days = helper.getCurrentDays();

    int year = helper.getCurrentYear();
    int yearIndex = Arrays.asList(years).indexOf(String.valueOf(year));
    this.yearIndex = yearIndex;
    setPickerValues(yearPicker, years, yearIndex);

    int currentMonth = helper.getCurrentMonth() + 1;//0~base
    int monthIndex = Arrays.asList(months).indexOf(String.valueOf(currentMonth));
    this.monthIndex = monthIndex;
    setPickerValues(monthPicker, months, monthIndex);

    int currentDay = helper.getCurrentDay();
    int dayIndex = Arrays.asList(days).indexOf(String.valueOf(currentDay));
    this.dayIndex = dayIndex;
    setPickerValues(dayPicker, days, dayIndex);
  }

  private void setPickerValues(NumberPicker picker, String[] values, int index) {
    if (values == null) {
      return;
    }
    String[] displayedValues = picker.getDisplayedValues();

    //解决因为values长度变化导致的ArrayIndexOutOfBoundsException
    if (displayedValues != null && values.length > displayedValues.length) {
      picker.setDisplayedValues(values);
      picker.setMinValue(0);
      picker.setMaxValue(values.length - 1);
    } else {
      picker.setMinValue(0);
      picker.setMaxValue(values.length - 1);
      picker.setDisplayedValues(values);
    }

    picker.setValue(index);
  }

  /**
   * 月份更新 -> 需要更新日期 -> 记录更新
   */
  public void updateMonth(int oldIndex, int newIndex) {
    this.monthIndex = newIndex;
    checkDaysByMonthIndex(oldIndex, newIndex);
  }

  /**
   * 月份改变影响到天数. 大小月份变更,2月
   *
   * month (0~11) -> 等价于 index (0~11)
   */
  private void checkDaysByMonthIndex(int oldIndex, int newIndex) {
    if (oldIndex == newIndex) {
      return;
    }
    if (TimeUtils.isBigMonth(oldIndex) && TimeUtils.isBigMonth(newIndex)) {
      return;
    }
    if (!TimeUtils.isBigMonth(oldIndex) && !TimeUtils.isBigMonth(newIndex)) {
      return;
    }
    setPickerValues(dayPicker, helper.getDays(helper.getYear(yearIndex), newIndex), dayIndex);
  }

  /**
   * 日期更新
   */
  public void updateDay(int dayIndex) {
    this.dayIndex = dayIndex;
  }

  /**
   * 年份更新 -> 年份改变可能影响到天数. if 闰年2月
   */
  public void updateYear(int oldIndex, int newIndex) {
    this.yearIndex = newIndex;
    checkDaysByYearIndex(oldIndex, newIndex);
  }

  /**
   * 年份改变影响到天数.
   * 只会影响到2月.
   */
  private void checkDaysByYearIndex(int oldIndex, int newIndex) {
    int oldYear = helper.getYear(oldIndex);
    int newYear = helper.getYear(newIndex);
    int month = monthIndex;
    if (!TimeUtils.isFebruary(month)) {
      // 不是二月
      return;
    }
    // 是二月
    if (TimeUtils.isLeapYear(oldYear) && TimeUtils.isLeapYear(newYear)) {
      // 都是闰年
      return;
    }
    if (!TimeUtils.isLeapYear(oldYear) && !TimeUtils.isLeapYear(newYear)) {
      // 都是平年
      return;
    }
    // 二月 && 一个闰年一个平年
    setPickerValues(dayPicker, helper.getDays(newYear, month), dayIndex);
  }

  public void calculate() {
    Calendar target = helper.getCalendar(yearIndex, monthIndex, dayIndex);
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
    apartDaysTv.setText(result);
  }
}
