package top.liumingyi.distance;

import org.junit.Test;
import top.liumingyi.ciel.utils.TimeUtils;

import static junit.framework.Assert.assertEquals;

/**
 * Test {@link top.liumingyi.ciel.utils.TimeUtils}
 * Created by liumingyi on 2018/3/29.
 */

public class TimeUtilsTest {

  @Test public void isBigMonth_test() throws Exception {
    boolean b1 = TimeUtils.isBigMonth(0);
    boolean b2 = TimeUtils.isBigMonth(1);
    boolean b3 = TimeUtils.isBigMonth(2);
    boolean b4 = TimeUtils.isBigMonth(3);
    boolean b5 = TimeUtils.isBigMonth(4);
    boolean b6 = TimeUtils.isBigMonth(5);
    boolean b7 = TimeUtils.isBigMonth(6);
    boolean b8 = TimeUtils.isBigMonth(7);
    boolean b9 = TimeUtils.isBigMonth(8);
    boolean b10 = TimeUtils.isBigMonth(9);
    boolean b11 = TimeUtils.isBigMonth(10);
    boolean b12 = TimeUtils.isBigMonth(11);
    assertEquals(b1, true);
    assertEquals(b2, false);
    assertEquals(b3, true);
    assertEquals(b4, false);
    assertEquals(b5, true);
    assertEquals(b6, false);
    assertEquals(b7, true);
    assertEquals(b8, true);
    assertEquals(b9, false);
    assertEquals(b10, true);
    assertEquals(b11, false);
    assertEquals(b12, true);
  }

  @Test public void isLeapYear() throws Exception {
    boolean isLeap1 = TimeUtils.isLeapYear(1900);
    boolean isLeap2 = TimeUtils.isLeapYear(1600);
    boolean isLeap3 = TimeUtils.isLeapYear(2000);
    boolean isLeap4 = TimeUtils.isLeapYear(2012);
    boolean isLeap5 = TimeUtils.isLeapYear(2007);
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
}
