package top.liumingyi.distance.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import top.liumingyi.ciel.base.BaseViewModelFragment;
import top.liumingyi.ciel.views.TRxView;
import top.liumingyi.distance.R;
import top.liumingyi.distance.viewmodels.CalculateModel;
import top.liumingyi.distance.views.PickerView;

public class CalculateFragment extends BaseViewModelFragment<CalculateModel> {

  @BindView(R.id.today_tv) TextView todayTv;

  @BindView(R.id.calculate_btn) Button calculateBtn;

  @BindView(R.id.picker_view) PickerView pickerView;

  public static CalculateFragment newInstance() {
    return new CalculateFragment();
  }

  @Override protected CalculateModel initInjector() {
    return new CalculateModel();
  }

  @Override protected void dataBinding() {
    viewModel.getTodayLiveData().observe(this, today -> todayTv.setText(today));
  }

  @Override public int getLayoutResID() {
    return R.layout.fragment_calculate;
  }

  @Override public void initVariables() {

  }

  @Override protected void initView(Bundle savedInstanceState) {
    initListeners();
  }

  @SuppressLint("CheckResult") private void initListeners() {
    TRxView.clicks(calculateBtn).subscribe(o -> {
      pickerView.calculate();
    });
  }
}
