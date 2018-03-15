package top.liumingyi.ciel.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

public class ToastUtils {

  public static void show(Context context, int resId) {
    if (context == null) {
      return;
    }
    Toast.makeText(context, context.getString(resId), Toast.LENGTH_SHORT).show();
  }

  public static void showLong(Context context, int resId) {
    if (context == null) {
      return;
    }
    Toast.makeText(context, context.getString(resId), Toast.LENGTH_LONG).show();
  }

  public static void show(Context context, String msg) {
    if (TextUtils.isEmpty(msg) || context == null) {
      return;
    }
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
  }

  public static void showLong(Context context, String msg) {
    if (TextUtils.isEmpty(msg) || context == null) {
      return;
    }
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
  }
}
