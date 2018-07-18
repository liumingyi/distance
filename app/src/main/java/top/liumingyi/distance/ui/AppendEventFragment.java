package top.liumingyi.distance.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import com.orhanobut.logger.Logger;
import top.liumingyi.ciel.base.BaseViewModelFragment;
import top.liumingyi.ciel.views.TRxView;
import top.liumingyi.distance.R;
import top.liumingyi.distance.adapters.DateKeywordRecyclerAdapter;
import top.liumingyi.distance.viewmodels.AppendEventViewModel;

public class AppendEventFragment extends BaseViewModelFragment<AppendEventViewModel> {

  @BindView(R.id.keyword_layout) View keywordLayout;
  @BindView(R.id.keyword_recycler) RecyclerView keywordRecycler;
  @BindView(R.id.confirm_btn) Button confirmBtn;

  public static AppendEventFragment newInstance() {
    return new AppendEventFragment();
  }

  @Override protected AppendEventViewModel initInjector() {
    return new AppendEventViewModel();
  }

  @Override protected int getLayoutResID() {
    return R.layout.fragment_append_event;
  }

  @Override protected void initVariables() {

  }

  @Override protected void initView(Bundle savedInstanceState) {
    initLabels();
    initListeners();
  }

  @SuppressLint("CheckResult") private void initListeners() {
    TRxView.clicks(confirmBtn).subscribe(o -> {
      viewModel.confirm();
    });
  }

  private void initLabels() {
    if (!viewModel.hasKeywords()) {
      keywordLayout.setVisibility(View.GONE);
    } else {
      keywordLayout.setVisibility(View.VISIBLE);
      initLabelRecyclerView();
    }
  }

  private void initLabelRecyclerView() {
    DateKeywordRecyclerAdapter adapter = new DateKeywordRecyclerAdapter();
    keywordRecycler.setLayoutManager(new GridLayoutManager(getContext(), 4));
    keywordRecycler.setAdapter(adapter);
    adapter.setData(viewModel.getKeywords());

    adapter.setOnRecyclerItemClickListener((position, item) -> {
      Logger.d(">>>> click >>> " + position + " , " + item.getName());
      viewModel.clickKeyword(position, item);
    });
  }

  @Override protected void dataBinding() {

  }
}
