package top.liumingyi.distance;

import java.lang.reflect.Method;
import java.util.Calendar;
import org.junit.Test;
import top.liumingyi.distance.helpers.DatePickerHelper;

import static junit.framework.TestCase.assertEquals;

/**
 * Test {@link DatePickerHelper}
 * Created by liumingyi on 2018/3/1.
 */

public class DatePickHelperTest {

  private String[] bigMonth = new String[] {
      "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17",
      "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"
  };

  private DatePickerHelper datePickerHelper = new DatePickerHelper();

  @Test public void getCurrentDays_test() throws Exception {
    String[] days = datePickerHelper.getCurrentDays();
    //System.out.println("length : " + bigMonth.length + " , " + days.length);
    assertEquals(bigMonth.length, days.length);
    for (int i = 0; i < bigMonth.length; i++) {
      //System.out.println("i : " + bigMonth[i] + " , " + days[i]);
      assertEquals(bigMonth[i], days[i]);
    }
  }

  @Test public void getYears_test() throws Exception {
    String[] years = datePickerHelper.getYears();
    assertEquals(41, years.length);
    for (String year : years) {
      System.out.print(year + " ");
    }
    System.out.println();
  }

  @Test public void isLeapYear() throws Exception {
    Method method = DatePickerHelper.class.getDeclaredMethod("isLeapYear", int.class);
    method.setAccessible(true);
    boolean isLeap1 = (boolean) method.invoke(datePickerHelper, 1900);
    boolean isLeap2 = (boolean) method.invoke(datePickerHelper, 1600);
    boolean isLeap3 = (boolean) method.invoke(datePickerHelper, 2000);
    boolean isLeap4 = (boolean) method.invoke(datePickerHelper, 2012);
    boolean isLeap5 = (boolean) method.invoke(datePickerHelper, 2007);
    System.out.println("1900 : " + isLeap1);
    System.out.println("1600 : " + isLeap2);
    System.out.println("2000 : " + isLeap3);
    System.out.println("2012 : " + isLeap4);
    System.out.println("2007 : " + isLeap5);
    assertEquals(false, isLeap1);
    assertEquals(true, isLeap2);
    assertEquals(true, isLeap3);
    assertEquals(true, isLeap4);
    assertEquals(false, isLeap5);
  }

  @Test public void mergeStrings_test() throws Exception {
    Method method =
        DatePickerHelper.class.getDeclaredMethod("mergeStrings", String[].class, String[].class);
    method.setAccessible(true);
    String[] result = (String[]) method.invoke(datePickerHelper, new String[] { "First", "Second" },
        new String[] { "Third" });
    for (String aResult : result) {
      System.out.println(aResult);
    }
    assertEquals(3, result.length);
  }

  @Test public void isBigMonth_test() throws Exception {
    Method method = DatePickerHelper.class.getDeclaredMethod("isBigMonth", int.class);
    method.setAccessible(true);
    boolean isBig1 = (boolean) method.invoke(datePickerHelper, 0);
    boolean isBig2 = (boolean) method.invoke(datePickerHelper, 1);
    boolean isBig3 = (boolean) method.invoke(datePickerHelper, 2);
    boolean isBig4 = (boolean) method.invoke(datePickerHelper, 3);
    boolean isBig5 = (boolean) method.invoke(datePickerHelper, 4);
    boolean isBig6 = (boolean) method.invoke(datePickerHelper, 5);
    boolean isBig7 = (boolean) method.invoke(datePickerHelper, 6);
    boolean isBig8 = (boolean) method.invoke(datePickerHelper, 7);
    boolean isBig9 = (boolean) method.invoke(datePickerHelper, 8);
    boolean isBig10 = (boolean) method.invoke(datePickerHelper, 9);
    boolean isBig11 = (boolean) method.invoke(datePickerHelper, 10);
    boolean isBig12 = (boolean) method.invoke(datePickerHelper, 11);

    assertEquals(true, isBig1);
    assertEquals(false, isBig2);
    assertEquals(true, isBig3);
    assertEquals(false, isBig4);
    assertEquals(true, isBig5);
    assertEquals(false, isBig6);
    assertEquals(true, isBig7);
    assertEquals(true, isBig8);
    assertEquals(false, isBig9);
    assertEquals(true, isBig10);
    assertEquals(false, isBig11);
    assertEquals(true, isBig12);
  }

  @Test public void getCalendar_test() throws Exception {
    Calendar calendar = datePickerHelper.getCalendar(20, 3, 4);
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    System.out.println(calendar.getTime());
    System.out.println("" + year + "-" + month + "-" + day);
    assertEquals(2018, year);
    assertEquals(3, month);
    assertEquals(5, day);
  }
}
