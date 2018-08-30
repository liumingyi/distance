package top.liumingyi.distance.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import top.liumingyi.distance.R;
import top.liumingyi.distance.adapters.LabelRecyclerAdapter;
import top.liumingyi.distance.data.Label;
import top.liumingyi.distance.events.LabelAddedEvent;
import top.liumingyi.distance.viewmodels.LabelViewModel;
import top.liumingyi.tang.base.BaseRecyclerViewAdapter;
import top.liumingyi.tang.base.BaseViewModelFragment;

/**
 * 倒计时事件页面
 */
public class LabelFragment extends BaseViewModelFragment<LabelViewModel> {

  private static final int SPAN_COUNT = 2;

  @BindView(R.id.events_recycler) RecyclerView recyclerView;

  private LabelRecyclerAdapter labelRecyclerAdapter;
  private BaseRecyclerViewAdapter.RecyclerItemLongClickListener<Label> itemLongClickListener =
      (position, item) -> showDeleteDialog(getContext(), position, item);

  private void showDeleteDialog(Context context, int position, Label item) {
    new AlertDialog.Builder(context).setTitle("确认删除？")
        .setNegativeButton(R.string.cancel, (dialog, which) -> {

        })
        .setPositiveButton(R.string.confirm, (dialog, which) -> {
          labelRecyclerAdapter.removeWithAnimate(position);
          viewModel.deleteLabelWithSaver(item);
        })
        .create()
        .show();
  }

  @Override protected void rxBusEventReceive(Object event) {
    super.rxBusEventReceive(event);
    if (event instanceof LabelAddedEvent) {
      LabelAddedEvent ev = ((LabelAddedEvent) event);
      Label label = ev.getLabel();
      labelRecyclerAdapter.addItem(label);
    }
  }

  public static LabelFragment newInstance() {
    return new LabelFragment();
  }

  @Override protected LabelViewModel initInjector() {
    return new LabelViewModel();
  }

  @Override protected int getLayoutResID() {
    return R.layout.fragment_labels;
  }

  @Override protected void initVariables() {
  }

  @Override protected void initView(Bundle savedInstanceState) {
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
    labelRecyclerAdapter = new LabelRecyclerAdapter(getActivity());
    labelRecyclerAdapter.setOnRecyclerItemLongClickListener(itemLongClickListener);
    recyclerView.setAdapter(labelRecyclerAdapter);
    registerRxBus();
  }

  @Override protected void dataBinding() {
    viewModel.getRecyclerUpdateLiveData()
        .observe(this, labels -> labelRecyclerAdapter.replace(labels));
  }
}
