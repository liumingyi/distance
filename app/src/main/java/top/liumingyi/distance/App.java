package top.liumingyi.distance;

import android.app.Application;
import com.orhanobut.logger.Logger;

public class App extends Application {

  @Override public void onCreate() {
    super.onCreate();

    Logger.init("Tick");
  }
}
