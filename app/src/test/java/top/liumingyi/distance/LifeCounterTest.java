package top.liumingyi.distance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link top.liumingyi.distance.views.LifeCounter}
 * Created by liumingyi on 2018/4/9.
 */

public class LifeCounterTest {

  private static final int DEFAULT_SCALE = 1;

  private static final int SPEC_1 = 10 * 10;
  private static final int SPEC_2 = 20 * 20;
  private static final int SPEC_3 = 30 * 30;
  private static final int SPEC_4 = 40 * 40;
  private static final int SPEC_5 = 50 * 50;
  private static final int SPEC_6 = 60 * 60;

  private int scale = DEFAULT_SCALE;

  @Test public void calculateSpecification_test() throws Exception {
    assertEquals(SPEC_1, calculateSpecification(99));

    assertEquals(SPEC_2, calculateSpecification(101));
    assertEquals(SPEC_2, calculateSpecification(150));

    assertEquals(SPEC_2, calculateSpecification(199));
    assertEquals(SPEC_2, calculateSpecification(299));
    assertEquals(SPEC_2, calculateSpecification(399));

    assertEquals(SPEC_3, calculateSpecification(401));
    assertEquals(SPEC_3, calculateSpecification(499));
    assertEquals(SPEC_3, calculateSpecification(900));

    assertEquals(SPEC_4, calculateSpecification(1200));
    assertEquals(SPEC_4, calculateSpecification(901));
    assertEquals(SPEC_4, calculateSpecification(1600));

    assertEquals(SPEC_5, calculateSpecification(1601));
    assertEquals(SPEC_5, calculateSpecification(2000));
    assertEquals(SPEC_5, calculateSpecification(2500));

    assertEquals(SPEC_6, calculateSpecification(2501));
    assertEquals(SPEC_6, calculateSpecification(2901));
    assertEquals(SPEC_6, calculateSpecification(3600));

    scale = 1;
    assertEquals(SPEC_2, calculateSpecification(3999));
    assertEquals(SPEC_2, calculateSpecification(4000));
    assertEquals(SPEC_2, calculateSpecification(3601));

    scale = 1;
    assertEquals(SPEC_3, calculateSpecification(4001));
    assertEquals(SPEC_3, calculateSpecification(40001));

    scale = 1;
    assertEquals(SPEC_4, calculateSpecification(10000));
  }

  private int calculateSpecification(int totalYear) {
    int spec;
    if (totalYear <= SPEC_1 * scale) {
      spec = SPEC_1;
    } else if (totalYear <= SPEC_2 * scale) {
      spec = SPEC_2;
    } else if (totalYear <= SPEC_3 * scale) {
      spec = SPEC_3;
    } else if (totalYear <= SPEC_4 * scale) {
      spec = SPEC_4;
    } else if (totalYear <= SPEC_5 * scale) {
      spec = SPEC_5;
    } else if (totalYear <= SPEC_6 * scale) {
      spec = SPEC_6;
    } else {
      //大于当前最大年份
      scale *= 10;
      spec = calculateSpecification(totalYear);
    }
    return spec;
  }
}
