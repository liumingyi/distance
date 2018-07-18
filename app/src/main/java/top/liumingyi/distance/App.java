package top.liumingyi.distance;

import android.app.Application;
import android.content.Context;
import com.orhanobut.logger.Logger;

public class App extends Application {

  private static Application application;

  public static Application getApplication() {
    return application;
  }

  @Override public void onCreate() {
    super.onCreate();

    Logger.init("Distance");

    application = this;
  }

  public static Context getContext() {
    return getApplication().getApplicationContext();
  }
}
