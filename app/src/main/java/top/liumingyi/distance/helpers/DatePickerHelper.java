package top.liumingyi.distance.helpers;

import android.support.annotation.NonNull;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import lombok.Getter;

/**
 * 日期选择帮助类
 *
 * 为日期选择器提供固定的数据源
 * 即：年份，月份，日期
 *
 * 年份范围为当前年份的前后20年
 *
 * Created by liumingyi on 2018/2/28.
 */

public final class DatePickerHelper {

  //年限范围前后20年
  private static final int YEAR_RANGE = 20;
  //总共41年
  private static final int YEAR_TOTAL = YEAR_RANGE * 2 + 1;

  private String[] DAYS_FEBRUARY = new String[] {
      "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
      "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28"
  };

  private String[] DAYS = new String[] {
      "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
      "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"
  };

  private String[] MONTHS = new String[] {
      "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
  };

  private String[] years;

  @Getter private int currentYear;
  /**
   * Month value is 0-based. e.g., 0 for January.
   */
  @Getter private int currentMonth;
  @Getter private int currentDay;
  @Getter private String today;

  public DatePickerHelper() {
    Calendar calendar = new GregorianCalendar();
    currentYear = calendar.get(Calendar.YEAR);
    currentMonth = calendar.get(Calendar.MONTH);
    currentDay = calendar.get(Calendar.DATE);
    DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
    today = dateFormat.format(calendar.getTime());
  }

  public String[] getMonths() {
    return MONTHS;
  }

  public String[] getCurrentDays() {
    return getDays(currentYear, currentMonth);
  }

  public String[] getDays(int year, int month) {
    if (isFebruary(month)) {
      //二月份
      if (isLeapYear(year)) {
        //闰年二月份
        return mergeStrings(DAYS_FEBRUARY, "29");
      } else {
        return DAYS_FEBRUARY;
      }
    } else if (isBigMonth(month)) {
      return mergeStrings(DAYS, "31");
    }
    return DAYS;
  }

  @NonNull private String[] mergeStrings(String[] source, String... addItems) {
    List<String> result = new ArrayList<>();
    result.addAll(Arrays.asList(source));
    result.addAll(Arrays.asList(addItems));
    return result.toArray(new String[result.size()]);
  }

  private boolean isBigMonth(int month) {
    if (month <= 6) {
      return month % 2 == 0;
    } else {
      return month % 2 == 1;
    }
  }

  private boolean isLeapYear(int year) {
    //普通年(不能被100整除的年份)能被4整除的为闰年。(如2004年就是闰年,1999年不是闰年)
    //世纪年(能被100整除的年份)能被400整除的是闰年。(如2000年是闰年，1900年不是闰年)
    return (year % 100 != 0 && year % 4 == 0) || (year % 100 == 0 && year % 400 == 0);
  }

  private boolean isFebruary(int month) {
    return month == 1;
  }

  public String[] getYears() {
    if (years == null) {
      years = new String[YEAR_TOTAL];
    }
    //int maxYear = currentYear + 20;
    int minYear = currentYear - YEAR_RANGE;
    for (int i = 0; i < YEAR_TOTAL; i++) {
      years[i] = String.valueOf(minYear + i);
    }
    return years;
  }

  public Calendar getCalendar(int yearIndex, int monthIndex, int dayIndex) {
    if (years == null || years.length == 0) {
      years = getYears();
    }
    int year = Integer.parseInt(years[yearIndex]);
    int day = dayIndex + 1;

    Calendar calendar = Calendar.getInstance();
    calendar.set(year, monthIndex, day);
    return calendar;
  }
}
