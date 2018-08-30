package top.liumingyi.distance.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import java.util.Calendar;
import lombok.Getter;
import top.liumingyi.distance.App;
import top.liumingyi.distance.data.User;
import top.liumingyi.distance.events.UpdateUserInfoEvent;
import top.liumingyi.distance.helpers.UserInfoSaver;
import top.liumingyi.tang.RxBus;
import top.liumingyi.tang.base.BaseViewModel;
import top.liumingyi.tang.utils.TimeUtils;

/**
 * ViewModel for{@link top.liumingyi.distance.ui.UserFormFragment}
 * Created by liumingyi on 2018/3/29.
 */

public class UserFormViewModel extends BaseViewModel {

  public static final int TAG_YEAR_MISSING = 10;
  public static final int TAG_YEAR_TOO_EARLY = 11;

  public static final int TAG_MONTH_MISSING = 20;
  public static final int TAG_MONTH_ILLEGAL = 21;

  public static final int TAG_DATE_MISSING = 30;
  public static final int TAG_DATE_ILLEGAL = 31;

  public static final int TAG_WISH_AGE_MISSING = 40;

  public static final int TAG_USERINFO_SUBMITTED = 0;

  private static final int YEAR_RANGE = 150;

  @Getter MutableLiveData<Integer> callbackLiveData = new MutableLiveData<>();
  @Getter MutableLiveData<User> dataInitLiveData = new MutableLiveData<>();

  private int year;
  private int month;//从1开始
  private int date;
  private int wishAge;

  public UserFormViewModel() {
  }

  @Override public void init(Bundle bundle) {
    User user = fetchUserInfo();
    if (user == null) {
      user = new User(1990, 6, 1, 99);
    }
    dataInitLiveData.setValue(user);
  }

  private User fetchUserInfo() {
    UserInfoSaver userInfoSaver = new UserInfoSaver(App.getContext());
    return userInfoSaver.getUserInfo();
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

  public void submit() {
    if (checkInputs()) {
      saveUserInfo();
    }
  }

  private void saveUserInfo() {
    new Thread(() -> {
      UserInfoSaver saver = new UserInfoSaver(App.getContext());
      User user = new User(year, month, date, wishAge);
      saver.saveUserInfo(user);
      callbackLiveData.setValue(TAG_USERINFO_SUBMITTED);
      RxBus.getDefault().send(new UpdateUserInfoEvent(user));
    }).run();
  }

  private boolean checkInputs() {
    return checkYear() && checkMonth() && checkDate() && checkWishAge();
  }

  private boolean checkWishAge() {
    boolean isLegal = wishAge > 0;
    if (!isLegal) {
      callbackLiveData.setValue(TAG_WISH_AGE_MISSING);
    }
    return isLegal;
  }

  private boolean checkDate() {
    if (date <= 0) {
      // 没填
      callbackLiveData.setValue(TAG_DATE_MISSING);
      return false;
    }

    boolean isLegal;

    if (month == 2) {
      // 2月
      if (TimeUtils.isLeapYear(year)) {
        // 闰年 1~29
        isLegal = date <= 29;
      } else {
        // 平年 1~28
        isLegal = date <= 28;
      }
    } else if (TimeUtils.isBigMonth(month)) {
      // 大月
      isLegal = date <= 31;
    } else {
      // 小月
      isLegal = date <= 30;
    }

    if (!isLegal) {
      callbackLiveData.setValue(TAG_DATE_ILLEGAL);
    }
    return isLegal;
  }

  private boolean checkMonth() {
    if (month <= 0) {
      // 没填
      callbackLiveData.setValue(TAG_MONTH_MISSING);
      return false;
    }

    boolean isLegal = month <= 12;
    if (!isLegal) {
      callbackLiveData.setValue(TAG_MONTH_ILLEGAL);
    }
    return isLegal;
  }

  private boolean checkYear() {
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    if (year <= 0) {
      // 没填
      callbackLiveData.setValue(TAG_YEAR_MISSING);
      return false;
    }
    if (year < currentYear - YEAR_RANGE) {
      // 年纪超出范围做个小提示,但依然支持计算
      callbackLiveData.setValue(TAG_YEAR_TOO_EARLY);
    }
    return true;
  }

  public void setYear(String year) {
    try {
      this.year = Integer.valueOf(year);
    } catch (Exception e) {
      this.year = -1;
    }
  }

  public void setMonth(String month) {
    try {
      this.month = Integer.valueOf(month);
    } catch (Exception e) {
      this.month = -1;
    }
  }

  public void setDate(String date) {
    try {
      this.date = Integer.valueOf(date);
    } catch (Exception e) {
      this.date = -1;
    }
  }

  public void setWishAge(String wishAge) {
    try {
      this.wishAge = Integer.valueOf(wishAge);
    } catch (Exception e) {
      this.wishAge = -1;
    }
  }
}
