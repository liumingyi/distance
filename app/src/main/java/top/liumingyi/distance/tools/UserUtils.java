package top.liumingyi.distance.tools;

import java.util.Date;
import top.liumingyi.distance.data.User;

public class UserUtils {

  public static Date fetchBirthday(User user) {
    if (user == null) {
      return null;
    }
    int birthYear = user.getBirthYear();
    int birthMonth = user.getBirthMonth();
    int birthDayOfMonth = user.getBirthDayOfMonth();
    return new Date(birthYear, birthMonth - 1, birthDayOfMonth);
  }
}
