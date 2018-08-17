package top.liumingyi.distance.data.datakeywords.models;

import android.content.Context;
import java.util.Calendar;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.datakeywords.Config;

class NextVacationKeyword implements DateKeyword {

  private String name;

  NextVacationKeyword(Context context) {
    // TODO: 2018/7/20 需要一个能动态获取假期的Api
    name = context.getString(R.string.next_vacation);
  }

  @Override public int getId() {
    return Config.KW_NEXT_VACATION;
  }

  @Override public Calendar getCalendar() {
    return Calendar.getInstance();
  }

  @Override public String getName() {
    return name;
  }
}
