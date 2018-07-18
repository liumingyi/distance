package top.liumingyi.distance.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import top.liumingyi.ciel.RxBus;
import top.liumingyi.ciel.base.BaseRecyclerViewAdapter;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.EventInfo;
import top.liumingyi.distance.events.AppendEventItemEvent;

public class EventsRecyclerAdapter
    extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder, EventInfo> {

  private static final int TYPE_FOOTER = 1;
  private static final int TYPE_NORMAL = 0;

  private Context context;

  public EventsRecyclerAdapter(Context context) {
    this.context = context;
  }

  @Override public int getItemCount() {
    return super.getItemCount() + 1;
  }

  @Override public int getItemViewType(int position) {
    if (position == getItemCount() - 1) {
      return TYPE_FOOTER;
    }
    return TYPE_NORMAL;
  }

  @NonNull @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    if (viewType == TYPE_NORMAL) {
      return new EventViewHolder(inflater.inflate(R.layout.events_recycler_item, parent, false));
    } else {
      return new EventFooterHolder(inflater.inflate(R.layout.events_add_item, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
    if (getItemViewType(position) == TYPE_NORMAL) {
      EventViewHolder holder = (EventViewHolder) viewHolder;
      EventInfo item = getItem(position);
      holder.titleTv.setText(
          String.format(context.getString(R.string.event_title), item.getTitle()));
      holder.daysTv.setText(item.getDays());
      holder.dateTv.setText(item.getDate());
    }
  }

  class EventViewHolder extends RecyclerView.ViewHolder {

    private final TextView titleTv;
    private final TextView daysTv;
    private final TextView dateTv;

    EventViewHolder(View itemView) {
      super(itemView);
      titleTv = itemView.findViewById(R.id.event_title_tv);
      daysTv = itemView.findViewById(R.id.event_days_tv);
      dateTv = itemView.findViewById(R.id.event_date_tv);
    }
  }

  private class EventFooterHolder extends RecyclerView.ViewHolder {

    EventFooterHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(v -> RxBus.getDefault().send(new AppendEventItemEvent()));
    }
  }
}
