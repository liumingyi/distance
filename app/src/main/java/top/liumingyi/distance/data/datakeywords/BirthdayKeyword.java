package top.liumingyi.distance.data.datakeywords;

import android.content.Context;
import java.util.Date;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.User;
import top.liumingyi.distance.helpers.UserInfoSaver;
import top.liumingyi.distance.tools.UserUtils;

public class BirthdayKeyword implements DateKeyword {

  private Date birthday;
  private String name;

  BirthdayKeyword(Context context) {
    UserInfoSaver userInfoSaver = new UserInfoSaver(context);
    User user = userInfoSaver.getUserInfo();
    birthday = UserUtils.fetchBirthday(user);
    name = context.getString(R.string.birthday);
  }

  @Override public Date getDate() {
    return birthday;
  }

  @Override public String getName() {
    return name;
  }
}
