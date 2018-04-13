package top.liumingyi.ciel.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * 时间处理工具类
 * Created by liumingyi on 2018/3/2.
 */

public class TimeUtils {

  /**
   * 计算两个日期相距的天数
   */
  public static long calculateApartDays(Calendar start, Calendar end) {
    if (isBefore(end, start)) {
      return calculateApartDays(end, start);
    }
    long apartMillis = end.getTimeInMillis() - start.getTimeInMillis();
    //使用毫秒精度过高，会因为1毫秒的误差而多或少计算一天!
    long apartSecond = (long) Math.ceil((double) apartMillis / 1000);
    return TimeUnit.SECONDS.toDays(apartSecond);
  }

  @Deprecated public static long calculateApartDaysOld(Calendar start, Calendar end) {
    if (isBefore(end, start)) {
      return calculateApartDaysOld(end, start);
    }

    int count = 0;
    Calendar startCopy = Calendar.getInstance();
    startCopy.set(start.get(Calendar.YEAR), start.get(Calendar.MONTH),
        start.get(Calendar.DAY_OF_MONTH));
    while (isBefore(startCopy, end)) {
      count++;
      startCopy.add(Calendar.DATE, 1);
    }
    return count;
  }

  public static long calculateApartDaysSign(Calendar start, Calendar end) {
    return isBefore(end, start) ? 0 - calculateApartDays(end, start)
        : calculateApartDays(start, end);
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

  public static boolean isSameDay(Calendar start, Calendar end) {
    return start.get(Calendar.YEAR) == end.get(Calendar.YEAR)
        && start.get(Calendar.MONTH) == end.get(Calendar.MONTH)
        && start.get(Calendar.DAY_OF_MONTH) == end.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * month : 0 ~ 11 #JANUARY# which is 0.
   */
  public static boolean isBigMonth(int month) {
    if (month <= 6) {
      return month % 2 == 0;
    } else {
      return month % 2 == 1;
    }
  }

  /**
   * 是否是闰年
   */
  public static boolean isLeapYear(int year) {
    //普通年(不能被100整除的年份)能被4整除的为闰年。(如2004年就是闰年,1999年不是闰年)
    //世纪年(能被100整除的年份)能被400整除的是闰年。(如2000年是闰年，1900年不是闰年)
    return (year % 100 != 0 && year % 4 == 0) || (year % 100 == 0 && year % 400 == 0);
  }

  /**
   * 是否是二月
   *
   * @param month 0~11
   */
  public static boolean isFebruary(int month) {
    return month == 1;
  }

  /**
   * 得到目标Calendar
   *
   * @param originalCalendar 原始日期
   * @param dates 原始日期的第xx天,第一天是原始日期
   */
  public static Calendar getTargetCalendar(Calendar originalCalendar, int dates) {
    if (originalCalendar == null || dates <= 0) {
      return null;
    }
    Calendar result = Calendar.getInstance();
    result.setTime(originalCalendar.getTime());
    result.add(Calendar.DATE, dates - 1);
    return result;
  }

  public static String dateFormat(Calendar calendar) {
    DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    return dateFormat.format(calendar.getTime());
  }
}
