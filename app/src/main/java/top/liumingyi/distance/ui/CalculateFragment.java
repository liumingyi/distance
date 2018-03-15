package top.liumingyi.distance.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import butterknife.BindView;
import io.reactivex.functions.Consumer;
import top.liumingyi.ciel.base.BaseViewModelFragment;
import top.liumingyi.ciel.views.TRxView;
import top.liumingyi.distance.R;
import top.liumingyi.distance.viewmodels.MainViewModel;

public class CalculateFragment extends BaseViewModelFragment<MainViewModel> {

  @BindView(R.id.today_tv) TextView todayTv;
  @BindView(R.id.year_picker) NumberPicker yearPicker;
  @BindView(R.id.month_picker) NumberPicker monthPicker;
  @BindView(R.id.day_picker) NumberPicker dayPicker;
  @BindView(R.id.apart_days_tv) TextView apartDaysTv;
  @BindView(R.id.calculate_btn) Button calculateBtn;

  public static CalculateFragment newInstance() {
    return new CalculateFragment();
  }

  @Override protected MainViewModel initInjector() {
    return new MainViewModel(getContext());
  }

  @Override protected void dataBinding() {
    viewModel.getYearLiveData().observe(this, new Observer<MainViewModel.PickerData>() {
      @Override public void onChanged(@Nullable MainViewModel.PickerData pickerData) {
        if (pickerData == null) {
          return;
        }
        setPickerValues(yearPicker, pickerData.values, pickerData.index);
      }
    });
    viewModel.getMonthLiveData().observe(this, new Observer<MainViewModel.PickerData>() {
      @Override public void onChanged(@Nullable MainViewModel.PickerData pickerData) {
        if (pickerData == null) {
          return;
        }
        setPickerValues(monthPicker, pickerData.values, pickerData.index);
      }
    });
    viewModel.getDateLiveData().observe(this, new Observer<MainViewModel.PickerData>() {
      @Override public void onChanged(@Nullable MainViewModel.PickerData pickerData) {
        if (pickerData == null) {
          return;
        }
        setPickerValues(dayPicker, pickerData.values, pickerData.index);
      }
    });
    viewModel.getTodayLiveData().observe(this, new Observer<String>() {
      @Override public void onChanged(@Nullable String today) {
        todayTv.setText(today);
      }
    });
    viewModel.getApartDaysLiveData().observe(this, new Observer<String>() {
      @Override public void onChanged(@Nullable String s) {
        apartDaysTv.setText(s);
      }
    });
  }

  @Override public int getLayoutResID() {
    return R.layout.fragment_calculate;
  }

  @Override public void initVariables() {

  }

  @Override protected void initView(Bundle savedInstanceState) {
    initDatePickers();
    initListeners();
  }

  private void initListeners() {
    TRxView.clicks(calculateBtn).subscribe(new Consumer<Object>() {
      @Override public void accept(Object o) throws Exception {
        viewModel.calculateDays();
      }
    });
  }

  private void initDatePickers() {
    yearPicker.setWrapSelectorWheel(false);
    monthPicker.setWrapSelectorWheel(false);
    dayPicker.setWrapSelectorWheel(false);
    yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
      @Override public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        viewModel.updateYear(newVal);
      }
    });
    monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
      @Override public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        viewModel.updateMonth(newVal);
      }
    });
    dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
      @Override public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        viewModel.updateDay(newVal);
      }
    });
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
}
