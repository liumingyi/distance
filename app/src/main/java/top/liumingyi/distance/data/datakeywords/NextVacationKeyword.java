package top.liumingyi.distance.data.datakeywords;

import android.content.Context;
import java.util.Date;
import top.liumingyi.distance.R;

class NextVacationKeyword implements DateKeyword {

  private String name;

  NextVacationKeyword(Context context) {
    // TODO: 2018/7/20 需要一个能动态获取假期的Api
    name = context.getString(R.string.next_vacation);
  }

  @Override public Date getDate() {
    return null;
  }

  @Override public String getName() {
    return name;
  }
}
