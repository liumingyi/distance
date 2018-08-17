package top.liumingyi.distance.data.datakeywords.models;

import android.content.Context;
import java.util.Calendar;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.datakeywords.Config;

public class NextYearKeyword implements DateKeyword {

  private Calendar nextYear;
  private String name;

  public NextYearKeyword(Context context) {
    name = context.getString(R.string.next_year);
  }

  @Override public int getId() {
    return Config.KW_NEXT_YEAR;
  }

  @Override public Calendar getCalendar() {
    nextYear = Calendar.getInstance();
    nextYear.set(nextYear.get(Calendar.YEAR) + 1, 0, 1);
    return nextYear;
  }

  @Override public String getName() {
    return name;
  }
}
