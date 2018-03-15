package top.liumingyi.distance.data;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息
 * Created by liumingyi on 2018/3/19.
 */

@Getter @Setter public class User implements Parcelable {

  /** 生日 */
  private String birthday;
  /** 期望的年龄 */
  private int wishAge;
  /** 期望的永寂日期 */
  private String wishDate;

  public User(String birthday, int wishAge, String wishDate) {
    this.birthday = birthday;
    this.wishAge = wishAge;
    this.wishDate = wishDate;
  }

  /**
   * month 从1开始
   */
  public User(int year, int month, int dateOfMonth, int wishAge) {
    this.birthday = generateBirthday(year, month, dateOfMonth);
    this.wishAge = wishAge;
    this.wishDate = generateWishDate(year, month, dateOfMonth, wishAge);
  }

  private String generateWishDate(int year, int month, int dayOfMonth, int wishAge) {
    return String.valueOf(year + wishAge) + "-" + month + "-" + dayOfMonth;
  }

  private String generateBirthday(int year, int month, int dateOfMonth) {
    return String.valueOf(year) + "-" + month + "-" + dateOfMonth;
  }

  protected User(Parcel in) {
    birthday = in.readString();
    wishAge = in.readInt();
    wishDate = in.readString();
  }

  public static final Creator<User> CREATOR = new Creator<User>() {
    @Override public User createFromParcel(Parcel in) {
      return new User(in);
    }

    @Override public User[] newArray(int size) {
      return new User[size];
    }
  };

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(birthday);
    dest.writeInt(wishAge);
    dest.writeString(wishDate);
  }

  public int getBirthYear() {
    //return Integer.valueOf(("-".split(birthday))[0]);
    return Integer.valueOf(birthday.split("-")[0]);
  }

  /**
   * month 从1开始
   */
  public int getBirthMonth() {
    return Integer.valueOf(birthday.split("-")[1]);
  }

  public int getBirthDayOfMonth() {
    return Integer.valueOf(birthday.split("-")[2]);
  }
}
