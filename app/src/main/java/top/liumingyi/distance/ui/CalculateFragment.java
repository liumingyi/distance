package top.liumingyi.distance.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import java.util.Calendar;
import top.liumingyi.ciel.base.BaseViewModelFragment;
import top.liumingyi.ciel.views.TRxView;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.User;
import top.liumingyi.distance.helpers.UserInfoSaver;
import top.liumingyi.distance.viewmodels.MainViewModel;
import top.liumingyi.distance.views.DaysBetweenTwoDatesView;
import top.liumingyi.distance.views.MyXxDateView;
import top.liumingyi.distance.views.PickerView;
import top.liumingyi.distance.views.XxDaysAfterDateView;

public class CalculateFragment extends BaseViewModelFragment<MainViewModel> {

  @BindView(R.id.today_tv) TextView todayTv;

  @BindView(R.id.calculate_btn) Button calculateBtn;

  @BindView(R.id.calculate_type_1_tv) TextView typeBtn1;
  @BindView(R.id.calculate_type_2_tv) TextView typeBtn2;
  @BindView(R.id.calculate_type_3_tv) TextView typeBtn3;
  @BindView(R.id.calculate_type_4_tv) TextView typeBtn4;

  @BindView(R.id.picker_view) PickerView pickerView;
  @BindView(R.id.my_xx_date_tool) MyXxDateView myXxDateView;
  @BindView(R.id.xx_days_after_date_view) XxDaysAfterDateView xxDaysAfterDateView;
  @BindView(R.id.days_between_2_dates_view) DaysBetweenTwoDatesView daysBetweenTwoDatesView;

  private static final int CALCULATE_TOOL_1 = 0;
  private static final int CALCULATE_TOOL_2 = 1;
  private static final int CALCULATE_TOOL_3 = 2;
  private static final int CALCULATE_TOOL_4 = 3;

  public static CalculateFragment newInstance() {
    return new CalculateFragment();
  }

  @Override protected MainViewModel initInjector() {
    return new MainViewModel();
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
    initMyXxDateView();
    initListeners();
  }

  private void initMyXxDateView() {
    UserInfoSaver userInfoSaver = new UserInfoSaver(getContext());
    User userInfo = userInfoSaver.getUserInfo();
    Calendar birthday = Calendar.getInstance();
    birthday.set(userInfo.getBirthYear(), userInfo.getBirthMonth() - 1,
        userInfo.getBirthDayOfMonth());
    myXxDateView.setBirthday(birthday);
  }

  @SuppressLint("CheckResult") private void initListeners() {
    TRxView.clicks(calculateBtn).subscribe(o -> {
      switch (viewModel.getCalculateToolId()) {
        case CALCULATE_TOOL_1:
          pickerView.calculate();
          break;
        case CALCULATE_TOOL_2:
          myXxDateView.calculate();
          break;
        case CALCULATE_TOOL_3:
          xxDaysAfterDateView.calculate();
          break;
        case CALCULATE_TOOL_4:
          daysBetweenTwoDatesView.calculate();
      }
    });
    TRxView.clicks(typeBtn1).subscribe(o -> selectCalculateToolView(CALCULATE_TOOL_1));
    TRxView.clicks(typeBtn2).subscribe(o -> selectCalculateToolView(CALCULATE_TOOL_2));
    TRxView.clicks(typeBtn3).subscribe(o -> selectCalculateToolView(CALCULATE_TOOL_3));
    TRxView.clicks(typeBtn4).subscribe(o -> selectCalculateToolView(CALCULATE_TOOL_4));
  }

  private void selectCalculateToolView(int toolId) {
    viewModel.setCalculateToolId(toolId);
    pickerView.setVisibility(View.GONE);
    myXxDateView.setVisibility(View.GONE);
    xxDaysAfterDateView.setVisibility(View.GONE);
    daysBetweenTwoDatesView.setVisibility(View.GONE);
    if (toolId == CALCULATE_TOOL_1) {
      pickerView.setVisibility(View.VISIBLE);
    } else if (toolId == CALCULATE_TOOL_2) {
      myXxDateView.setVisibility(View.VISIBLE);
    } else if (toolId == CALCULATE_TOOL_3) {
      xxDaysAfterDateView.setVisibility(View.VISIBLE);
    } else if (toolId == CALCULATE_TOOL_4) {
      daysBetweenTwoDatesView.setVisibility(View.VISIBLE);
    }
  }
}
