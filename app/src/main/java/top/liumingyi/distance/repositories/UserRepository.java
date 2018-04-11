package top.liumingyi.distance.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import top.liumingyi.ciel.utils.TimeUtils;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.User;
import top.liumingyi.distance.helpers.UserInfoSaver;
import top.liumingyi.distance.viewmodels.UserViewModel;

/**
 * 为{@link top.liumingyi.distance.viewmodels.UserViewModel}提供数据
 * Created by liumingyi on 2018/3/19.
 */

public class UserRepository {

  private WeakReference<Context> context;

  public UserRepository(Context context) {
    this.context = new WeakReference<>(context);
  }

  public User fetchUserInfo() {
    UserInfoSaver userInfoSaver = new UserInfoSaver(context.get());
    return userInfoSaver.getUserInfo();
  }

  /**
   * 生成已经过去的天数的描述信息
   * 比如：今天是您生命的第xxx天
   *
   * 先要判断：死了还是活着
   */
  @NonNull public String generateDescribeMessage(User user) {
    int birthYear = user.getBirthYear();
    int birthMonth = user.getBirthMonth();
    int birthDayOfMonth = user.getBirthDayOfMonth();
    int wishAge = user.getWishAge();

    Calendar birthday = Calendar.getInstance();
    birthday.set(birthYear, birthMonth - 1, birthDayOfMonth);
    Calendar current = Calendar.getInstance();
    Calendar deathDay = Calendar.getInstance();
    deathDay.set(birthYear + wishAge, birthMonth - 1, birthDayOfMonth);

    boolean isAlive = user.isAlive();

    String message;

    long pastDays = TimeUtils.calculateApartDaysSign(birthday, current);
    if (isAlive) {
      // 活着 or 还没出生
      if (pastDays == 0) {
        message = context.get().getString(R.string.life_start);
      } else if (pastDays > 0) {
        float percent =
            (float) (Math.round((float) pastDays / user.getWishTotalDays() * 10000) / 100.0);
        message = String.format(context.get().getString(R.string.date_of_life), pastDays + 1,
            String.valueOf(percent).concat("%"));
      } else {
        // 天数为负,表示还没有出生
        message =
            String.format(context.get().getString(R.string.days_before_born), Math.abs(pastDays));
      }
    } else {
      // 已去世
      long daysOfLife = TimeUtils.calculateApartDays(birthday, deathDay);

      long daysOfLeave = pastDays - daysOfLife;

      if (daysOfLeave == 0) {
        message = context.get().getString(R.string.today_is_death_day);
      } else {
        message = String.format(context.get().getString(R.string.days_after_dead), daysOfLeave);
      }
    }

    return message;
  }

  public UserViewModel.LifeFormViewData generateLifeFormViewData(User user) {
    UserViewModel.LifeFormViewData data = new UserViewModel.LifeFormViewData();
    data.isAlive = user.isAlive();
    data.totalYear = user.getWishAge();
    data.progressYear = Calendar.getInstance().get(Calendar.YEAR) - user.getBirthYear();
    return data;
  }
}
