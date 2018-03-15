package top.liumingyi.distance.events;

import lombok.Getter;
import top.liumingyi.distance.data.User;

public class UpdateUserInfoEvent {

  @Getter private User user;

  public UpdateUserInfoEvent(User user) {
    this.user = user;
  }
}
