package top.liumingyi.distance.helpers;

import android.content.Context;
import com.google.gson.Gson;
import top.liumingyi.ciel.utils.SharedPreferencesSaver;
import top.liumingyi.distance.data.User;

/**
 * 用户信息存储器
 * Created by liumingyi on 2018/3/19.
 */

public class UserInfoSaver extends SharedPreferencesSaver {

  private static final String LIB_KEY = "per_lib_key_userinfo";
  private static final String KEY_USER_INFO = "key_user_info";

  public UserInfoSaver(Context context) {
    super(context, LIB_KEY);
  }

  public void saveUserInfo(User user) {
    Gson gson = new Gson();
    String info = gson.toJson(user);
    save(KEY_USER_INFO, info);
  }

  public User getUserInfo() {
    String info = getString(KEY_USER_INFO, null);
    Gson gson = new Gson();
    return info == null ? null : gson.fromJson(info, User.class);
  }

  public boolean hasUser() {
    return hasData(KEY_USER_INFO);
  }
}
