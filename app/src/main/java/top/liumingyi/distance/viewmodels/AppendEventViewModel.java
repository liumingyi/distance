package top.liumingyi.distance.viewmodels;

import android.os.Bundle;
import com.orhanobut.logger.Logger;
import java.util.Date;
import java.util.List;
import top.liumingyi.ciel.base.BaseViewModel;
import top.liumingyi.distance.data.datakeywords.DateKeyword;
import top.liumingyi.distance.data.datakeywords.DateKeywordController;

public class AppendEventViewModel extends BaseViewModel {

  private DateKeywordController kwController;

  public AppendEventViewModel() {
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

  public boolean hasKeywords() {
    return true;
  }

  public List<DateKeyword> getKeywords() {
    return kwController.getKeywords();
  }

  public void confirm() {

  }

  public void clickKeyword(int position, DateKeyword kw) {
    Date date = kw.getDate();
    Logger.d("Position -> " + position + "  Data -> " + date);
  }
}
