package top.liumingyi.distance.data.datakeywords;

import android.content.Context;
import java.util.Date;
import top.liumingyi.distance.R;

public class NextYearKeyword implements DateKeyword {

  private Date date;
  private String name;

  public NextYearKeyword(Context context) {
    name = context.getString(R.string.next_year);
  }

  @Override public Date getDate() {
    return date;
  }

  @Override public String getName() {
    return name;
  }
}
