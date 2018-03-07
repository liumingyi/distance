package top.liumingyi.ciel;

import java.util.Calendar;
import org.junit.Test;
import top.liumingyi.ciel.utils.TimeUtils;

import static junit.framework.Assert.assertEquals;

/**
 * Test method in {@link TimeUtils}
 * Created by liumingyi on 2018/3/2.
 */

public class TimeUtilsTest {

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
    int i = TimeUtils.calculateApartDays(start, end);
    System.out.println(i);

    // 2016.2.1 ~ 2018.6.1 851天
    start.set(2016, 1, 1);
    end.set(2018, 5, 1);
    int j = TimeUtils.calculateApartDays(start, end);
    System.out.println(j);

    //  同一天
    start.set(2018, 5, 1);
    end.set(2018, 5, 1);
    int m = TimeUtils.calculateApartDays(start, end);
    System.out.println(m);

    // 86
    start.set(2018, 2, 7);
    end.set(2018, 5, 1);
    int n = TimeUtils.calculateApartDays(start, end);
    System.out.println(n);
  }
}
