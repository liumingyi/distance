package top.liumingyi.distance.data.datakeywords.models;

import android.content.Context;
import java.util.Calendar;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.User;
import top.liumingyi.distance.data.datakeywords.Config;
import top.liumingyi.distance.helpers.UserInfoSaver;
import top.liumingyi.distance.tools.UserUtils;

public class BirthdayKeyword implements DateKeyword {

  private Calendar birthday;
  private String name;

  BirthdayKeyword(Context context) {
    UserInfoSaver userInfoSaver = new UserInfoSaver(context);
    User user = userInfoSaver.getUserInfo();
    birthday = UserUtils.getNextBirthday(user);
    name = context.getString(R.string.birthday);
  }

  @Override public int getId() {
    return Config.KW_BIRTHDAY;
  }

  @Override public Calendar getCalendar() {
    return birthday;
  }

  @Override public String getName() {
    return name;
  }
}
