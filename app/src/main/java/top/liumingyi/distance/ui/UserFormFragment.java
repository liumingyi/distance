package top.liumingyi.distance.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import io.reactivex.functions.Consumer;
import top.liumingyi.ciel.RxBus;
import top.liumingyi.ciel.base.BaseViewModelFragment;
import top.liumingyi.ciel.utils.ToastUtils;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.User;
import top.liumingyi.distance.events.CloseUserFormEvent;
import top.liumingyi.distance.viewmodels.UserFormViewModel;

/**
 * 用户信息表格-用户填写个人信息
 * Created by liumingyi on 2018/3/19.
 */

public class UserFormFragment extends BaseViewModelFragment<UserFormViewModel> {

  @BindView(R.id.user_info_submit_btn) Button submitBtn;
  @BindView(R.id.year_edt) EditText yearEdt;
  @BindView(R.id.month_edt) EditText monthEdt;
  @BindView(R.id.day_edt) EditText dayEdit;
  @BindView(R.id.wish_age_edt) EditText wishAgeEdt;

  public static UserFormFragment newInstance() {
    return new UserFormFragment();
  }

  @Override protected UserFormViewModel initInjector() {
    return new UserFormViewModel(getContext());
  }

  @Override protected int getLayoutResID() {
    return R.layout.fragment_user_form;
  }

  @Override protected void initVariables() {

  }

  @Override protected void initView(Bundle savedInstanceState) {
    RxView.clicks(submitBtn).subscribe(new Consumer<Object>() {
      @Override public void accept(Object o) throws Exception {
        viewModel.submit();
      }
    });
    RxTextView.textChanges(yearEdt).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence year) throws Exception {
        viewModel.setYear(year.toString());
      }
    });
    RxTextView.textChanges(monthEdt).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence month) throws Exception {
        viewModel.setMonth(month.toString());
      }
    });
    RxTextView.textChanges(dayEdit).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence day) throws Exception {
        viewModel.setDate(day.toString());
      }
    });
    RxTextView.textChanges(wishAgeEdt).subscribe(new Consumer<CharSequence>() {
      @Override public void accept(CharSequence wishAge) throws Exception {
        viewModel.setWishAge(wishAge.toString());
      }
    });
  }

  @Override protected void dataBinding() {
    viewModel.getCallbackLiveData().observe(this, new Observer<Integer>() {
      @Override public void onChanged(@Nullable Integer tag) {
        if (tag == null) {
          return;
        }
        switch (tag) {
          case UserFormViewModel.TAG_YEAR_MISSING:
            ToastUtils.show(getContext(), R.string.please_complete_birthday);
            yearEdt.findFocus();
            break;
          case UserFormViewModel.TAG_YEAR_TOO_EARLY:
            ToastUtils.show(getContext(), R.string.year_too_early);
            yearEdt.findFocus();
            break;

          case UserFormViewModel.TAG_MONTH_MISSING:
            ToastUtils.show(getContext(), R.string.please_complete_birthday);
            monthEdt.findFocus();
            break;
          case UserFormViewModel.TAG_MONTH_ILLEGAL:
            ToastUtils.show(getContext(), R.string.please_input_legal_month);
            monthEdt.findFocus();
            break;

          case UserFormViewModel.TAG_DATE_MISSING:
            ToastUtils.show(getContext(), R.string.please_complete_birthday);
            dayEdit.findFocus();
            break;
          case UserFormViewModel.TAG_DATE_ILLEGAL:
            ToastUtils.show(getContext(), R.string.please_input_legal_date);
            dayEdit.findFocus();
            break;

          case UserFormViewModel.TAG_WISH_AGE_MISSING:
            ToastUtils.showLong(getContext(), R.string.please_input_wish_age);
            wishAgeEdt.findFocus();
            break;
          case UserFormViewModel.TAG_USERINFO_SUBMITTED:
            RxBus.getDefault().send(new CloseUserFormEvent());
            break;
        }
      }
    });

    viewModel.getDataInitLiveData().observe(this, new Observer<User>() {
      @Override public void onChanged(@Nullable User user) {
        if (user == null) {
          return;
        }
        yearEdt.setText(String.valueOf(user.getBirthYear()));
        monthEdt.setText(String.valueOf(user.getBirthMonth()));
        dayEdit.setText(String.valueOf(user.getBirthDayOfMonth()));
        wishAgeEdt.setText(String.valueOf(user.getWishAge()));
      }
    });
  }
}
