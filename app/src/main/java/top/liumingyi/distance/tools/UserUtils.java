package top.liumingyi.distance.tools;

import java.util.Calendar;
import java.util.GregorianCalendar;
import top.liumingyi.distance.data.User;

public class UserUtils {

  public static Calendar fetchBirthday(User user) {
    if (user == null) {
      return null;
    }
    int birthYear = user.getBirthYear();
    int birthMonth = user.getBirthMonth();
    int birthDayOfMonth = user.getBirthDayOfMonth();
    return new GregorianCalendar(birthYear, birthMonth - 1, birthDayOfMonth);
  }

  public static Calendar getNextBirthday(User user) {
    if (user == null) {
      return null;
    }
    int birthMonth = user.getBirthMonth();
    int birthDayOfMonth = user.getBirthDayOfMonth();

    Calendar now = Calendar.getInstance();
    int nowMonth = now.get(Calendar.MONTH);
    int nowDayOfMonth = now.get(Calendar.DAY_OF_MONTH);

    int year = now.get(Calendar.YEAR);

    if (birthMonth < nowMonth) {
      // 今年的生日已过
      year += 1;
    } else if (birthDayOfMonth < nowDayOfMonth) {
      // 今年的生日已过
      year += 1;
    }

    return new GregorianCalendar(year, birthMonth, birthDayOfMonth);
  }
}
