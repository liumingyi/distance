package top.liumingyi.distance;

import android.support.test.runner.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;
import top.liumingyi.distance.data.User;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class) public class UserInfoTest {

  @Test public void getBirthdayTest() throws Exception {
    User user = new User(1990, 6, 1, 99);
    assertEquals(1990, user.getBirthYear());
    assertEquals(6, user.getBirthMonth());
    assertEquals(1, user.getBirthDayOfMonth());
    assertEquals(99, user.getWishAge());
    assertEquals("1990-6-1", user.getBirthday());
    assertEquals("2089-6-1", user.getWishDate());
  }
}
