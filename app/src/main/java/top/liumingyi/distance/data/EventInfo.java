package top.liumingyi.distance.data;

import java.util.Calendar;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import top.liumingyi.ciel.utils.TimeUtils;

@Getter @Setter @ToString public class EventInfo {
  private String title;
  private String days;
  private String date;

  //  just for test
  //public EventInfo(String title, int days) {
  //  this.title = title;
  //  this.days = String.valueOf(days);
  //  Calendar targetCalendar = TimeUtils.getNextDaysCalendar(Calendar.getInstance(), days);
  //  this.date = TimeUtils.dateFormat(targetCalendar);
  //}

  public EventInfo(String title, Calendar endCalender) {
    this.title = title;
    this.days = String.valueOf(TimeUtils.calculateApartDays(Calendar.getInstance(), endCalender));
    this.date = TimeUtils.dateFormat(endCalender);
  }
}
