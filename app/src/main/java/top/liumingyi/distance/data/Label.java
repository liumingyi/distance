package top.liumingyi.distance.data;

import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Label
 */
@Getter @Setter @ToString public class Label {
  private String title;
  private Calendar endCalender;

  public Label(String title, Calendar endCalender) {
    this.title = title;
    this.endCalender = endCalender;
  }
}
