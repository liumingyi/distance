package top.liumingyi.distance.ui;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import top.liumingyi.ciel.base.BaseViewModelFragment;
import top.liumingyi.distance.R;
import top.liumingyi.distance.adapters.EventsRecyclerAdapter;
import top.liumingyi.distance.viewmodels.EventsViewModel;

public class EventsFragment extends BaseViewModelFragment<EventsViewModel> {

  private static final int SPAN_COUNT = 2;

  @BindView(R.id.events_recycler) RecyclerView recyclerView;

  private EventsRecyclerAdapter eventsRecyclerAdapter;

  public static EventsFragment newInstance() {
    return new EventsFragment();
  }

  @Override protected EventsViewModel initInjector() {
    return new EventsViewModel(getActivity());
  }

  @Override protected int getLayoutResID() {
    return R.layout.fragment_events;
  }

  @Override protected void initVariables() {
  }

  @Override protected void initView(Bundle savedInstanceState) {
    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
    eventsRecyclerAdapter = new EventsRecyclerAdapter(getActivity());
    recyclerView.setAdapter(eventsRecyclerAdapter);
    initDefaultItems();
  }

  private void initDefaultItems() {
    eventsRecyclerAdapter.setData(viewModel.generateDefaultItems());
  }

  @Override protected void dataBinding() {
    viewModel.getRecyclerUpdateLiveData().observe(this, o -> {

    });
  }
}
