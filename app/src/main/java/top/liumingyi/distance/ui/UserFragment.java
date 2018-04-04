package top.liumingyi.distance.ui;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import io.reactivex.functions.Consumer;
import top.liumingyi.ciel.RxBus;
import top.liumingyi.ciel.base.BaseViewModelFragment;
import top.liumingyi.ciel.views.TRxView;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.User;
import top.liumingyi.distance.events.OpenUserFormEvent;
import top.liumingyi.distance.events.UpdateUserInfoEvent;
import top.liumingyi.distance.viewmodels.UserViewModel;
import top.liumingyi.distance.views.LifeFormView;

/**
 * 个人信息
 * Created by liumingyi on 2018/3/16.
 */

public class UserFragment extends BaseViewModelFragment<UserViewModel> {

  @BindView(R.id.wish_age_tv) TextView wishAgeTv;
  @BindView(R.id.birthday_tv) TextView birthdayTv;
  @BindView(R.id.forever_day_tv) TextView foreverDayTv;
  @BindView(R.id.past_days_tv) TextView pastDaysTv;
  @BindView(R.id.edit_btn) Button editBtn;
  @BindView(R.id.lifeFormView) LifeFormView lifeFormView;

  @Override protected void rxBusEventReceive(Object event) {
    if (event instanceof UpdateUserInfoEvent) {
      User user = ((UpdateUserInfoEvent) event).getUser();
      if (viewModel != null) {
        viewModel.updateUserInfo(user);
      }
    }
  }

  public static UserFragment newInstance() {
    return new UserFragment();
  }

  @Override protected UserViewModel initInjector() {
    return new UserViewModel(getContext());
  }

  @Override protected int getLayoutResID() {
    return R.layout.fragment_user;
  }

  @Override protected void initVariables() {
    registerRxBus();
  }

  @Override protected void initView(Bundle savedInstanceState) {
    TRxView.clicks(editBtn).subscribe(new Consumer<Object>() {
      @Override public void accept(Object o) throws Exception {
        RxBus.getDefault().send(new OpenUserFormEvent());
      }
    });
  }

  @Override protected void dataBinding() {
    viewModel.getUserInfoLiveData().observe(this, new Observer<User>() {
      @Override public void onChanged(@Nullable User user) {
        if (user == null) {
          return;
        }
        birthdayTv.setText(user.getBirthday());
        wishAgeTv.setText(String.valueOf(user.getWishAge()));
        foreverDayTv.setText(user.getWishDate());
      }
    });

    viewModel.getDescribeMessageLiveData().observe(this, new Observer<String>() {
      @Override public void onChanged(@Nullable String msg) {
        pastDaysTv.setText(msg);
      }
    });

    viewModel.getLifeFormViewData().observe(this, new Observer<UserViewModel.LifeFormViewData>() {
      @Override public void onChanged(@Nullable UserViewModel.LifeFormViewData lifeFormViewData) {
        // TODO: 2018/4/8 set LifeFormView's data
        if (lifeFormViewData == null || !lifeFormViewData.isAlive) {
          lifeFormView.setVisibility(View.GONE);
          return;
        }
        lifeFormView.setVisibility(View.VISIBLE);
        lifeFormView.setTotalYear(lifeFormViewData.totalYear);
        lifeFormView.setProgressYear(lifeFormViewData.progressYear);
        lifeFormView.invalidate();
      }
    });
  }
}
