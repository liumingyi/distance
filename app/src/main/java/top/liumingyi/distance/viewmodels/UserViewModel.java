package top.liumingyi.distance.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Bundle;
import lombok.Getter;
import top.liumingyi.distance.data.User;
import top.liumingyi.distance.repositories.UserRepository;
import top.liumingyi.tang.base.BaseViewModel;

/**
 * ViewModel For UserFragment{@link top.liumingyi.distance.ui.UserFragment}
 * Created by liumingyi on 2018/3/16.
 */

public class UserViewModel extends BaseViewModel {

  @Getter private MutableLiveData<User> userInfoLiveData = new MutableLiveData<>();
  @Getter private MutableLiveData<String> describeMessageLiveData = new MutableLiveData<>();
  @Getter private MutableLiveData<LifeFormViewData> lifeFormViewData = new MutableLiveData<>();

  public static class LifeFormViewData {
    public boolean isAlive;
    public int totalYear;
    public int progressYear;
  }

  private UserRepository repository;

  public UserViewModel(Context context) {
    this.repository = new UserRepository(context);
  }

  @Override public void init(Bundle bundle) {
    User user = fetchUserInfo();
    if (user != null) {
      updateUserInfo(user);
    }
  }

  public void updateUserInfo(User user) {
    userInfoLiveData.setValue(user);
    describeMessageLiveData.setValue(repository.generateDescribeMessage(user));
    lifeFormViewData.setValue(repository.generateLifeFormViewData(user));
  }

  private User fetchUserInfo() {
    return repository.fetchUserInfo();
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
}
