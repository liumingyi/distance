package top.liumingyi.distance.events;

import lombok.Getter;
import top.liumingyi.distance.data.Label;

@Getter public class LabelAddedEvent {

  private Label label;

  public LabelAddedEvent(Label label) {
    this.label = label;
  }
}
