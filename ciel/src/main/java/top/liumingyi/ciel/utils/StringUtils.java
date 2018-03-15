package top.liumingyi.ciel.utils;

import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类<br>
 */
public class StringUtils {

  private static final String ZERO_STRING = "0";

  private static final String CHINESE_MOBILE_NUMBER_PREFIX_STRING =
      "130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,"
          + "145,146,147,148,149,150,151,152,153,154,155,156,157,158,159,"
          + "170,180,181,182,183,184,185,186,187,188,189,"
          + "171";

  private static final String[] CHINESE_MOBILE_NUMBER_PREFIX =
      CHINESE_MOBILE_NUMBER_PREFIX_STRING.split(",");

  /**
   * 剔除了空格的
   */
  public static boolean isBlank(CharSequence charSequence) {
    return charSequence == null || isBlank(charSequence.toString());
  }

  /**
   * 与 {@link TextUtils#isEmpty(CharSequence)}区别是 isBlank剔除了空格
   */
  public static boolean isBlank(String input) {
    if (input == null) {
      return true;
    }
    input = input.trim();
    return input.isEmpty();
  }

  public static boolean isBlankOrZero(String input) {
    return isBlank(input) || ZERO_STRING.equals(input);
  }

  public static String toLocalNumber(String rawNumber) {
    if (isBlank(rawNumber)) {
      return null;
    }
    String number = rawNumber.replaceAll("[^\\d\\+]", "");
    if (number.startsWith("+86")) {
      number = number.replaceFirst("^\\+86", "");
    } else if (number.startsWith("0086")) {
      number = number.replaceFirst("^0086", "");
    } else if (number.startsWith("00") || number.startsWith("+")) {
      return null;
    }
    number = number.replaceFirst("^0", "");
    if (isChineseMobileNumber(number)) {
      return number;
    }
    return null;
  }

  public static boolean isChineseMobileNumber(String number) {
    if (isBlank(number)) {
      return false;
    }
    if (number.length() != 11) {
      return false;
    }
    for (String prefix : CHINESE_MOBILE_NUMBER_PREFIX) {
      if (number.startsWith(prefix)) {
        return true;
      }
    }
    return false;
  }

  public static String join(List<String> paramsArray, String delimiter) {
    if (paramsArray == null || paramsArray.isEmpty() || isBlank(delimiter)) {
      return "";
    }
    StringBuilder stringBuilder = new StringBuilder();
    int size = paramsArray.size();
    for (int i = 0; i < size; i++) {
      if (i > 0) {
        stringBuilder.append(delimiter);
      }
      stringBuilder.append(paramsArray.get(i));
    }
    return stringBuilder.toString();
  }

  public static boolean equals(String s1, String s2) {
    return !(s1 == null || s2 == null) && s1.trim().equals(s2.trim());
  }

  public static boolean isChinese(String str) {
    for (int i = 0; i < str.length(); i = i + 1) {
      if (!Pattern.compile("[\u4e00-\u9fa5]").matcher(String.valueOf(str.charAt(i))).find()) {
        return false;
      }
    }
    return true;
  }

  public boolean conValidate(String str) {
    if (null != str && !"".equals(str)) {
      if ((isChinese(str) || str.matches("^[A-Za-z]+$")) && str.length() <= 10) {
        return true;
      }
    }
    return false;
  }

  public static boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("[0-9]*");
    Matcher isNum = pattern.matcher(str);
    return isNum.matches();
  }

  /**
   * 给String添加下划线
   *
   * @param string 需要添加下划线的字符串
   */
  public static SpannableString addUnderLine(String string) {
    SpannableString content = new SpannableString(string);
    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
    return content;
  }

  /**
   * 检测汽车车牌
   */
  private static boolean isChineseCarPlate(String carNo) {
    Pattern p;
    if (carNo.startsWith("WJ")) {//武警车牌
      p = Pattern.compile("^(WJ)[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{5}$");
    } else if (carNo.startsWith("使")) {//使馆车牌
      p = Pattern.compile("^[使]{1}[0-9]{6}$");
    } else if (carNo.startsWith("B")
        || carNo.startsWith("C")
        || carNo.startsWith("E")
        || carNo.startsWith("G")
        || carNo.startsWith("H")
        || carNo.startsWith("J")
        || carNo.startsWith("K")
        || carNo.startsWith("L")
        || carNo.startsWith("N")
        || carNo.startsWith("V")
        || carNo.startsWith("S")
        || carNo.startsWith("Z")) {//军牌
      p = Pattern.compile("^[BCEGHJKLNVSZ]{1}[A-Z]{1}[A-Z0-9]{5}$");
    } else {//普通车牌
      p = Pattern.compile(
          "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}[A-Z]{1}[A-Z0-9]{4,5}[A-Z0-9挂学警港澳台领]{1}$");
    }
    Matcher m = p.matcher(carNo);
    return m.matches();
  }

  public static int valueOfInt(String intStr) {
    if (isBlank(intStr)) {
      return 0;
    }
    return Integer.valueOf(intStr);
  }

  public static long valueOfLong(String longStr) {
    if (isBlank(longStr)) {
      return 0L;
    }
    return Long.valueOf(longStr);
  }

  public static float valueOfFloat(String floatStr) {
    if (isBlank(floatStr)) {
      return 0f;
    }
    return Float.valueOf(floatStr);
  }

  public static double valueOfDouble(String doubleStr) {
    if (isBlank(doubleStr)) {
      return 0d;
    }
    return Double.valueOf(doubleStr);
  }

  /**
   * 格式化距离
   *
   * @param distance 传入的距离 单位：米
   * @return 格式(保留两位小数 单位:千米) ： 4.01 km
   */
  public static String formatDistance(float distance) {
    float kiloMeter = distance / 1000;
    double result = Math.floor(kiloMeter * 100) / 100;
    return String.valueOf(result) + "km";
  }
}
