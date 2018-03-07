package top.liumingyi.ciel.utils;

import java.util.Calendar;

/**
 * 时间处理工具类
 * Created by liumingyi on 2018/3/2.
 */

public class TimeUtils {

  /**
   * 计算两个日期相距的天数
   */
  public static int calculateApartDays(Calendar start, Calendar end) {
    if (isBefore(end, start)) {
      return calculateApartDays(end, start);
    }

    int count = 0;
    while (isBefore(start, end)) {
      count++;
      start.add(Calendar.DATE, 1);
    }
    return count;
  }

  /**
   * 不用 millis 做比较，只比较到天
   *
   * 判断start是否在end之前
   *
   * @return 如果start在end之前返回true，如果start和end是同一天或者start在end之后返回false。
   */
  private static boolean isBefore(Calendar start, Calendar end) {
    int sYear = start.get(Calendar.YEAR);
    int sMonth = start.get(Calendar.MONTH);
    int sDay = start.get(Calendar.DAY_OF_MONTH);

    int eYear = end.get(Calendar.YEAR);
    int eMonth = end.get(Calendar.MONTH);
    int eDay = end.get(Calendar.DAY_OF_MONTH);

    //比较年份
    if (sYear == eYear) {
      //比较月份
      if (sMonth == eMonth) {
        //比较日期
        return sDay < eDay;
      } else {
        return sMonth < eMonth;
      }
    } else {
      return sYear < eYear;
    }
  }

  private static boolean isSameDay(Calendar start, Calendar end) {
    return start.get(Calendar.YEAR) == end.get(Calendar.YEAR)
        && start.get(Calendar.MONTH) == end.get(Calendar.MONTH)
        && start.get(Calendar.DAY_OF_MONTH) == end.get(Calendar.DAY_OF_MONTH);
  }
}
