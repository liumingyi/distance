package top.liumingyi.distance.tools;

import android.content.res.Resources;
import android.util.TypedValue;

public class Utils {

  public static float dpToPixel(float dp) {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
        Resources.getSystem().getDisplayMetrics());
  }

  public static float pixelToDp(float px) {
    return px / Resources.getSystem().getDisplayMetrics().density;
  }
}
