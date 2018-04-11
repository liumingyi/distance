package top.liumingyi.ciel;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import org.junit.Test;
import top.liumingyi.ciel.utils.TimeUtils;

import static junit.framework.Assert.assertEquals;

/**
 * Test method in {@link TimeUtils}
 * Created by liumingyi on 2018/3/2.
 */

public class TimeUtilsTest {

  // Handy constants for conversion methods
  static final long C0 = 1L;
  static final long C1 = C0 * 1000L;
  static final long C2 = C1 * 1000L;
  static final long C3 = C2 * 1000L;
  static final long C4 = C3 * 60L;
  static final long C5 = C4 * 60L;
  static final long C6 = C5 * 24L;

  @Test public void accuracy_test() throws Exception {
    long d = 86399996;// 1天
    d = 31535999992L;//wrong
    d = 2678400004L;
    d = 86400000;
    long d1 = 86399999;

    long days = TimeUnit.MILLISECONDS.toDays(d);
    long days1 = TimeUnit.MILLISECONDS.toDays(d1);
    System.out.println(days + " , " + days1);

    long s = (long) Math.ceil((double) d / 1000);
    long s1 = (long) Math.ceil((double) d1 / 1000);
    long n = TimeUnit.SECONDS.toDays(s);
    long n1 = TimeUnit.SECONDS.toDays(s1);
    System.out.println(n + " , " + n1);
  }

  @Test public void percent_test() throws Exception {
    long totalDays = 2301027385L;
    int pastDays = 10178;
    double p = (double) (Math.round(pastDays / totalDays));
    double percent = Math.round((double) pastDays / totalDays * 10000) / 100.0;
    System.out.println("p = " + p);
    System.out.println("percent = " + percent);
    System.out.println(" >> " + p + "%");
  }

  @Test public void test_test() throws Exception {
    int column = 30;
    int row = 21;
    int scale = 100000;
    long totalYear = 60000001;
    long v = row * column * scale - totalYear;
    System.out.println(">> v = " + v);
    int left = (int) Math.floor(v / scale);
    System.out.println(">> le = " + left);
    int endColumn = column - left;
    System.out.println(">> en = " + endColumn);
  }

  @Test public void calculateApartDays_test() throws Exception {
    Calendar start = Calendar.getInstance();
    start.set(2018, 2, 2);
    Calendar end = Calendar.getInstance();
    end.set(2018, 2, 1);
    //int days = TimeUtils.calculateApartDays(start, end);

    //assertEquals(1, days);

    // 2017.1.1 ~ 2016.12.31 1天
    start.set(2017, 0, 1);
    end.set(2016, 11, 31);
    assertEquals(1, TimeUtils.calculateApartDays(start, end));

    // 2018.1.1 ~ 2017.1.1 365天
    start.set(2018, 0, 1);
    end.set(2017, 0, 1);
    assertEquals(365, TimeUtils.calculateApartDays(start, end));

    // 2018.1.1 ~ 2018.2.1 31天
    start.set(2018, 0, 1);
    end.set(2018, 1, 1);
    assertEquals(31, TimeUtils.calculateApartDays(start, end));

    // 2016.2.1 ~ 2016.3.1 29天
    start.set(2016, 1, 1);
    end.set(2016, 2, 1);
    assertEquals(29, TimeUtils.calculateApartDays(start, end));

    // 1900.2.1 ~ 1900.3.1 28天
    start.set(1900, 1, 1);
    end.set(1900, 2, 1);
    assertEquals(28, TimeUtils.calculateApartDays(start, end));

    // 1900.2.1 ~ 2018.3.1 28天
    start.set(1900, 1, 1);
    end.set(2018, 2, 1);
    long i = TimeUtils.calculateApartDays(start, end);
    long l = TimeUtils.calculateApartDaysOld(start, end);
    assertEquals(i, l);
    System.out.println(i + " , " + l + " , " + (i == l));

    // 2016.2.1 ~ 2018.6.1 851天
    start.set(2016, 1, 1);
    end.set(2018, 5, 1);
    long j = TimeUtils.calculateApartDays(start, end);
    System.out.println(j);

    //  同一天
    start.set(2018, 5, 1);
    end.set(2018, 5, 1);
    long m = TimeUtils.calculateApartDays(start, end);
    System.out.println(m);

    // 86
    start.set(2018, 2, 7);
    end.set(2018, 5, 1);
    long n = TimeUtils.calculateApartDays(start, end);
    System.out.println(n);
  }
}
