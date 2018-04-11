package top.liumingyi.distance.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import top.liumingyi.ciel.utils.DensityUtils;
import top.liumingyi.ciel.views.TRxView;
import top.liumingyi.distance.R;
import top.liumingyi.distance.events.CloseUserFormEvent;
import top.liumingyi.distance.helpers.UserInfoSaver;

/**
 * 主页面-管理多个分页
 * Created by liumingyi on 2018/3/15.
 */

public class MainActivity extends DistanceBaseActivity {

  private static final String TAG_CALCULATE_FRAGMENT = "tag_calculate_fragment";
  private static final String TAG_USER_FRAGMENT = "tag_user_fragment";

  private static final String TAG_HOME = TAG_CALCULATE_FRAGMENT;
  private static final String TAG_USER_FORM_FRAGMENT = "tag_user_form_fragment";

  private CalculateFragment calculateFragment;
  private UserFragment userFragment;

  @BindView(R.id.slidLayout) SlideUpView slideUpView;
  @BindView(R.id.navigation) BottomNavigationView navigationView;
  @BindView(R.id.toolbar) Toolbar toolbar;

  TextView toolbarEditTv;

  private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
            case R.id.navigation_calculate:
              replaceFragment(calculateFragment, R.id.frame_layout, TAG_CALCULATE_FRAGMENT);
              return true;
            case R.id.navigation_user:
              if (userFragment == null) {
                userFragment = UserFragment.newInstance();
              }
              replaceFragment(userFragment, R.id.frame_layout, TAG_USER_FRAGMENT);
              return true;
          }
          return false;
        }
      };

  @Override protected void rxBusEventReceive(Object event) {
    if (event instanceof CloseUserFormEvent) {
      slideUpView.down();
    }
  }

  @Override public int getLayoutResID() {
    return R.layout.activity_main;
  }

  @Override public void inject() {

  }

  @Override public void initVariables() {
    registerRxBus();
  }

  @Override public void initViews(Bundle savedInstanceState) {
    initToolbar();
    initNavigationView();
    initSlideUpView();
    initContentFragments();
    initUserFormFragment();
    checkUserInfo();
  }

  private void initSlideUpView() {
    slideUpView.bringToFront();
    slideUpView.setExtraHeight(DensityUtils.dip2px(this, 49));//底部导航栏高度，直接写死了
    slideUpView.setOpenStateChangeListener(new SlideUpView.StateChangeListener() {
      @Override public void open() {
        if (toolbarEditTv != null) {
          toolbarEditTv.setText(getString(R.string.cancel));
        }
      }

      @Override public void closed() {
        if (toolbarEditTv != null) {
          toolbarEditTv.setText(getString(R.string.edit));
        }
      }
    });
  }

  private void initNavigationView() {
    navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
  }

  private void initToolbar() {
    getLayoutInflater().inflate(R.layout.my_toolbar, toolbar);
    toolbarEditTv = toolbar.findViewById(R.id.toolbar_edit_tv);
    TRxView.clicks(toolbarEditTv).subscribe(new Consumer<Object>() {
      @Override public void accept(Object o) throws Exception {
        slideUpView.toggle();
      }
    });
  }

  private void initContentFragments() {
    calculateFragment = CalculateFragment.newInstance();
    addFragment(calculateFragment, R.id.frame_layout, TAG_CALCULATE_FRAGMENT);
  }

  private void initUserFormFragment() {
    UserFormFragment userFormFragment = UserFormFragment.newInstance();
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(slideUpView.getId(), userFormFragment, TAG_USER_FORM_FRAGMENT).commit();
  }

  private void checkUserInfo() {
    if (hasUserInfo()) {
      return;
    }
    Observable.just("").delay(500, TimeUnit.MILLISECONDS).subscribe(new Consumer<Object>() {
      @Override public void accept(Object o) throws Exception {
        slideUpView.up();
      }
    });
  }

  private boolean hasUserInfo() {
    UserInfoSaver saver = new UserInfoSaver(this);
    return saver.hasUser();
  }

  @Override public void onBackPressed() {
    if (slideUpView.isOpen()) {
      slideUpView.down();
    } else if (isHomePage()) {
      finish();
    } else {
      super.onBackPressed();
      navigationView.setSelectedItemId(R.id.navigation_calculate);
    }
  }

  private boolean isHomePage() {
    FragmentManager fm = getSupportFragmentManager();
    int count = fm.getBackStackEntryCount();
    if (count == 0) {
      return true;
    }
    FragmentManager.BackStackEntry lastFragment = fm.getBackStackEntryAt(count - 1);
    return lastFragment == null || TAG_HOME.equals(lastFragment.getName());
  }
}
