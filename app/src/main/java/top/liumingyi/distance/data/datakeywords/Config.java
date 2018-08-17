package top.liumingyi.distance.data.datakeywords;

import android.content.Context;
import android.support.annotation.NonNull;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import top.liumingyi.distance.App;
import top.liumingyi.distance.data.datakeywords.models.DateKeyword;

/**
 * 日期关键字 配置文件
 * 展示支持的关键字
 */
public class Config {

  private static final String BASE = DateKeyword.class.getPackage().getName();

  public static final int KW_NEXT_YEAR = 0;
  public static final int KW_BIRTHDAY = 1;
  public static final int KW_NEXT_VACATION = 2;

  private static List<Integer> allIds = new ArrayList<Integer>() {
    {
      add(KW_NEXT_YEAR);
      add(KW_BIRTHDAY);
      add(KW_NEXT_VACATION);
    }
  };

  private static Map<Integer, Object> mapping = new HashMap<Integer, Object>() {
    {
      put(KW_NEXT_YEAR, ".NextYearKeyword");
      put(KW_BIRTHDAY, ".BirthdayKeyword");
      put(KW_NEXT_VACATION, ".NextVacationKeyword");
    }
  };

  /**
   * catch (ClassNotFoundException e)
   * catch (NoSuchMethodException e)
   * catch (IllegalAccessException e)
   * catch (InstantiationException e)
   * catch (InvocationTargetException e)
   */
  public static List<DateKeyword> getShownKeyword(@NonNull List<Integer> ids) {
    List<DateKeyword> keywords = new ArrayList<>();
    Collections.sort(ids);
    try {
      for (Integer id : ids) {
        if (!allIds.contains(id)) {
          // 排除不只支持的id
          continue;
        }
        Class<?> clazz = Class.forName(BASE + mapping.get(id));
        Constructor<?> constructor = clazz.getConstructor(Context.class);
        DateKeyword dateKeyword =
            (DateKeyword) constructor.newInstance(new Object[] { App.getContext() });
        keywords.add(dateKeyword);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return keywords;
  }
}
