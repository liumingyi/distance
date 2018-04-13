package top.liumingyi.distance.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
import top.liumingyi.distance.views.SlideUpView;

/**
 * 主页面-管理多个分页
 * Created by liumingyi on 2018/3/15.
 */

public class MainActivity extends DistanceBaseActivity {

  private static final String TAG_USER_FORM_FRAGMENT = "tag_user_form_fragment";

  @BindView(R.id.slidLayout) SlideUpView slideUpView;
  @BindView(R.id.navigation) BottomNavigationView navigationView;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.viewpager) ViewPager viewPager;

  TextView toolbarEditTv;

  int[] navigatorIds = new int[] {
      R.id.navigation_calculate, R.id.navigation_user
  };

  private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
            case R.id.navigation_calculate:
              viewPager.setCurrentItem(MainViewPagerAdapter.CALCULATE_FRAGMENT_INDEX);
              return true;
            case R.id.navigation_user:
              viewPager.setCurrentItem(MainViewPagerAdapter.USER_FRAGMENT_INDEX);
              return true;
          }
          return false;
        }
      };
  private ViewPager.OnPageChangeListener pageChangeListener =
      new ViewPager.SimpleOnPageChangeListener() {
        @Override public void onPageSelected(int position) {
          navigationView.setSelectedItemId(navigatorIds[position]);
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
    initViewPager();
    initUserFormFragment();
    checkUserInfo();
  }

  private void initViewPager() {
    MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
    viewPager.setAdapter(adapter);
    viewPager.addOnPageChangeListener(pageChangeListener);
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

  @SuppressLint("CheckResult") private void initToolbar() {
    getLayoutInflater().inflate(R.layout.my_toolbar, toolbar);
    toolbarEditTv = toolbar.findViewById(R.id.toolbar_edit_tv);
    TRxView.clicks(toolbarEditTv).subscribe(o -> slideUpView.toggle());
  }

  private void initUserFormFragment() {
    UserFormFragment userFormFragment = UserFormFragment.newInstance();
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.add(slideUpView.getId(), userFormFragment, TAG_USER_FORM_FRAGMENT).commit();
  }

  @SuppressLint("CheckResult") private void checkUserInfo() {
    if (hasUserInfo()) {
      return;
    }
    Observable.just("")
        .delay(500, TimeUnit.MILLISECONDS)
        .subscribe((Consumer<Object>) o -> slideUpView.up());
  }

  private boolean hasUserInfo() {
    UserInfoSaver saver = new UserInfoSaver(this);
    return saver.hasUser();
  }

  @Override public void onBackPressed() {
    if (slideUpView.isOpen()) {
      slideUpView.down();
    } else {
      super.onBackPressed();
    }
  }

  private class MainViewPagerAdapter extends FragmentPagerAdapter {

    private static final int FRAGMENT_COUNT = 2;

    private static final int CALCULATE_FRAGMENT_INDEX = 0;
    private static final int USER_FRAGMENT_INDEX = 1;

    MainViewPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      if (position == CALCULATE_FRAGMENT_INDEX) {
        return CalculateFragment.newInstance();
      } else if (position == USER_FRAGMENT_INDEX) {
        return UserFragment.newInstance();
      }
      return null;
    }

    @Override public int getCount() {
      return FRAGMENT_COUNT;
    }
  }
}
