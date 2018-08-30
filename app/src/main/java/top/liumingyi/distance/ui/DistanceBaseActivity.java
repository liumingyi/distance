package top.liumingyi.distance.ui;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;
import top.liumingyi.distance.R;
import top.liumingyi.tang.base.BaseActivity;

public abstract class DistanceBaseActivity extends BaseActivity {

  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP) @Override protected void adaptation() {
    Window window = this.getWindow();
    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    window.setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color));
  }
}
