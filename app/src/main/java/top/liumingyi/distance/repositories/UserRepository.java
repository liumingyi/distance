package top.liumingyi.distance.repositories;

import android.content.Context;
import android.support.annotation.NonNull;
import java.lang.ref.WeakReference;
import java.util.Calendar;
import java.util.GregorianCalendar;
import top.liumingyi.ciel.utils.TimeUtils;
import top.liumingyi.distance.data.User;
import top.liumingyi.distance.helpers.UserInfoSaver;

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

    Calendar birthday = new GregorianCalendar(birthYear, birthMonth - 1, birthDayOfMonth);
    Calendar current = new GregorianCalendar();
    Calendar deathDay = new GregorianCalendar(birthYear + wishAge, birthMonth - 1, birthDayOfMonth);

    boolean isAlive = isAlive(deathDay);

    String message;

    if (isAlive) {
      // 活着 or 还没出生
      int pastDays = TimeUtils.calculateApartDaysSign(birthday, current);
      if (pastDays == 0) {
        message = "美妙人生从今天开始";
      } else if (pastDays > 0) {
        message = "今天是我生命中第" + (pastDays + 1) + "天";
      } else {
        // 天数为负,表示还没有出生
        message = "距离我出生还有" + Math.abs(pastDays) + "天";
      }
    } else {
      int pastDays = TimeUtils.calculateApartDaysSign(birthday, current);
      int daysOfLife = TimeUtils.calculateApartDays(birthday, deathDay);

      int daysOfLeave = pastDays - daysOfLife;

      if (daysOfLeave == 0) {
        message = "今日即永恒";
      } else {
        message = "已离开" + daysOfLeave + "天";
      }
    }

    return message;
  }

  private boolean isAlive(Calendar deathDay) {
    Calendar current = new GregorianCalendar();
    int currentYear = current.get(Calendar.YEAR);
    int currentMonth = current.get(Calendar.MONTH);
    int currentDayOfMonth = current.get(Calendar.DAY_OF_MONTH);

    int deathYear = deathDay.get(Calendar.YEAR);
    int deathMonth = deathDay.get(Calendar.MONTH);
    int deathDayOfMonth = deathDay.get(Calendar.DAY_OF_MONTH);

    return currentYear < deathYear || currentYear == deathYear && (currentMonth < deathMonth
        || currentMonth == deathMonth && currentDayOfMonth < deathDayOfMonth);
  }
}
