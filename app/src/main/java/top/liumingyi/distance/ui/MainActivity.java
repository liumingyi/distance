package top.liumingyi.distance.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import top.liumingyi.distance.R;
import top.liumingyi.distance.events.CloseUserFormEvent;
import top.liumingyi.distance.events.OpenLabelAppendFragmentEvent;
import top.liumingyi.distance.helpers.UserInfoSaver;
import top.liumingyi.distance.views.SlideUpView;
import top.liumingyi.tang.utils.DensityUtils;
import top.liumingyi.tang.views.TRxView;

/**
 * 主页面-管理多个分页
 * Created by liumingyi on 2018/3/15.
 */

public class MainActivity extends DistanceBaseActivity {

  private static final String TAG_USER_FORM_FRAGMENT = "tag_user_form_fragment";
  private static final String TAG_APPEND_EVENT_FRAGMENT = "tag_append_event_fragment";

  @BindView(R.id.slidLayout) SlideUpView slideUpView;
  @BindView(R.id.navigation) BottomNavigationView navigationView;
  @BindView(R.id.toolbar) Toolbar toolbar;
  @BindView(R.id.viewpager) ViewPager viewPager;

  TextView toolbarEditTv;

  int[] navigatorIds = new int[] {
      R.id.navigation_labels, R.id.navigation_user, R.id.navigation_calculate
  };

  private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
      new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
          switch (item.getItemId()) {
            case R.id.navigation_labels:
              viewPager.setCurrentItem(MainViewPagerAdapter.INDEX_LABEL_FRAGMENT);
              return true;
            case R.id.navigation_calculate:
              viewPager.setCurrentItem(MainViewPagerAdapter.INDEX_CALCULATE_FRAGMENT);
              return true;
            case R.id.navigation_user:
              viewPager.setCurrentItem(MainViewPagerAdapter.INDEX_USER_FRAGMENT);
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
    } else if (event instanceof OpenLabelAppendFragmentEvent) {
      addAppendEventFragmentToSlideUpView();
      slideUpView.up();
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
          toolbarEditTv.setText(getString(R.string.setting));
        }
      }
    });
    addUserFragmentToSlideUpView();
  }

  private void initNavigationView() {
    navigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
  }

  @SuppressLint("CheckResult") private void initToolbar() {
    getLayoutInflater().inflate(R.layout.my_toolbar, toolbar);
    toolbarEditTv = toolbar.findViewById(R.id.toolbar_edit_tv);
    TRxView.clicks(toolbarEditTv).subscribe(o -> {
      addUserFragmentToSlideUpView();
      slideUpView.toggle();
    });
  }

  UserFormFragment userFormFragment;
  LabelAppendFragment appendEventFragment;

  private void addUserFragmentToSlideUpView() {
    if (userFormFragment == null) {
      userFormFragment = UserFormFragment.newInstance();
    }
    FragmentManager fm = getSupportFragmentManager();
    if (hasEventFragment(fm)) {
      fm.beginTransaction()
          .replace(slideUpView.getId(), userFormFragment, TAG_USER_FORM_FRAGMENT)
          .commit();
    } else if (!hasUserFragment(fm)) {
      fm.beginTransaction()
          .add(slideUpView.getId(), userFormFragment, TAG_USER_FORM_FRAGMENT)
          .commit();
    }
  }

  /**
   * 存在添加倒计时的fragment
   */
  private boolean hasEventFragment(FragmentManager fm) {
    return fm.findFragmentByTag(TAG_APPEND_EVENT_FRAGMENT) != null;
  }

  /**
   * 存在用户信息fragment
   */
  private boolean hasUserFragment(FragmentManager fm) {
    return fm.findFragmentByTag(TAG_USER_FORM_FRAGMENT) != null;
  }

  private void addAppendEventFragmentToSlideUpView() {
    if (appendEventFragment == null) {
      appendEventFragment = LabelAppendFragment.newInstance();
    }
    FragmentManager fm = getSupportFragmentManager();
    if (hasUserFragment(fm)) {
      fm.beginTransaction()
          .replace(slideUpView.getId(), appendEventFragment, TAG_APPEND_EVENT_FRAGMENT)
          .commit();
    } else if (!hasEventFragment(fm)) {
      fm.beginTransaction()
          .add(slideUpView.getId(), appendEventFragment, TAG_APPEND_EVENT_FRAGMENT)
          .commit();
    }
  }

  @SuppressLint("CheckResult") private void checkUserInfo() {
    if (hasUserInfo()) {
      return;
    }
    Observable.just("")
        .delay(500, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
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

    private static final int FRAGMENT_COUNT = 3;

    private static final int INDEX_CALCULATE_FRAGMENT = 2;
    private static final int INDEX_USER_FRAGMENT = 1;
    private static final int INDEX_LABEL_FRAGMENT = 0;

    MainViewPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override public Fragment getItem(int position) {
      if (position == INDEX_CALCULATE_FRAGMENT) {
        return CalculateFragment.newInstance();
      } else if (position == INDEX_USER_FRAGMENT) {
        return UserFragment.newInstance();
      } else if (position == INDEX_LABEL_FRAGMENT) {
        return LabelFragment.newInstance();
      }
      return null;
    }

    @Override public int getCount() {
      return FRAGMENT_COUNT;
    }
  }
}
