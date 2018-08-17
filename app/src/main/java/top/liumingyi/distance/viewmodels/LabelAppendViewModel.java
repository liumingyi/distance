package top.liumingyi.distance.viewmodels;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import top.liumingyi.ciel.RxBus;
import top.liumingyi.ciel.base.BaseViewModel;
import top.liumingyi.distance.App;
import top.liumingyi.distance.data.Label;
import top.liumingyi.distance.data.datakeywords.DateKeywordController;
import top.liumingyi.distance.data.datakeywords.models.DateKeyword;
import top.liumingyi.distance.events.LabelAddedEvent;
import top.liumingyi.distance.helpers.LabelSaver;

public class LabelAppendViewModel extends BaseViewModel {

  private DateKeywordController kwController;
  private String labelName;
  private Label label;

  public LabelAppendViewModel() {
  }

  @Override public void init(Bundle bundle) {
    kwController = new DateKeywordController();
  }

  @Override public void onStart() {

  }

  @Override public void onResume() {

  }

  @Override public void onPause() {

  }

  @Override public void onStop() {

  }

  @Override public void onDestroy() {

  }

  public List<String> getKeywords() {
    List<DateKeyword> keywords = kwController.getKeywords();
    List<String> names = new ArrayList<>();
    for (DateKeyword keyword : keywords) {
      names.add(keyword.getName());
    }
    return names;
  }

  public void clickKeyword(String text) {
    List<DateKeyword> keywords = kwController.getKeywords();
    for (DateKeyword kw : keywords) {
      if (text.equals(kw.getName())) {
        labelName = kw.getName();
        confirm(kw.getCalendar());
      }
    }
  }

  public synchronized void confirm(Calendar calendar) {
    if (labelName != null && calendar != null) {
      label = new Label(labelName, calendar);
      RxBus.getDefault().send(new LabelAddedEvent(label));
      saveLabelAsynchronous();
    }
  }

  public synchronized void setLabelName(CharSequence name) {
    if (name == null) {
      name = "";
    }
    this.labelName = name.toString();
  }

  private void saveLabelAsynchronous() {
    Executor executor = Executors.newCachedThreadPool();
    executor.execute(saveLabelRunnable);
  }

  private Runnable saveLabelRunnable = this::run;

  private void run() {
    LabelSaver saver = new LabelSaver(App.getContext());
    saver.addLabel(label);
  }
}
