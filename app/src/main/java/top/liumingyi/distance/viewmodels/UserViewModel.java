package top.liumingyi.distance.viewmodels;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.Bundle;
import lombok.Getter;
import top.liumingyi.ciel.base.BaseViewModel;
import top.liumingyi.distance.data.User;
import top.liumingyi.distance.repositories.UserRepository;

/**
 * ViewModel For UserFragment{@link top.liumingyi.distance.ui.UserFragment}
 * Created by liumingyi on 2018/3/16.
 */

public class UserViewModel extends BaseViewModel {

  @Getter private MutableLiveData<User> userInfoLiveData = new MutableLiveData<>();
  @Getter private MutableLiveData<String> describeMessageLiveData = new MutableLiveData<>();

  private UserRepository repository;

  public UserViewModel(Context context) {
    this.repository = new UserRepository(context);
  }

  @Override public void init(Bundle bundle) {
    User user = fetchUserInfo();
    if (user == null) {
      requestForUserInfo();
    } else {
      updateUserInfo(user);
    }
  }

  public void updateUserInfo(User user) {
    userInfoLiveData.setValue(user);
    describeMessageLiveData.setValue(repository.generateDescribeMessage(user));
  }

  private User fetchUserInfo() {
    return repository.fetchUserInfo();
  }

  private void requestForUserInfo() {
    //没有用户信息
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
