package top.liumingyi.distance.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DateUtils {

  /**
   * 日期格式：yyyy年MM月dd日
   */
  public static String getToday() {
    Calendar calendar = Calendar.getInstance();
    DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault());
    return dateFormat.format(calendar.getTime());
  }
}
