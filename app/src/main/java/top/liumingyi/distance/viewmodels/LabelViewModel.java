package top.liumingyi.distance.viewmodels;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.internal.schedulers.IoScheduler;
import java.util.List;
import lombok.Getter;
import top.liumingyi.ciel.base.BaseViewModel;
import top.liumingyi.distance.App;
import top.liumingyi.distance.data.Label;
import top.liumingyi.distance.helpers.LabelSaver;

public class LabelViewModel extends BaseViewModel {

  @Getter MutableLiveData<List<Label>> recyclerUpdateLiveData = new MutableLiveData<>();

  public LabelViewModel() {
  }

  @SuppressLint("CheckResult") @Override public void init(Bundle bundle) {
    Observable.just("")
        .subscribeOn(new IoScheduler())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(o -> fetchData());
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

  private void fetchData() {
    LabelSaver labelSaver = new LabelSaver(App.getContext());
    List<Label> labels = labelSaver.getLabels();
    recyclerUpdateLiveData.setValue(labels);
  }

  @SuppressLint("CheckResult") public void deleteLabelWithSaver(Label item) {
    Observable.just(item)
        .subscribeOn(new IoScheduler())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(this::deleteData);
  }

  private void deleteData(Label label) {
    LabelSaver labelSaver = new LabelSaver(App.getContext());
    labelSaver.deleteLabel(label);
  }
}
