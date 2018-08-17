package top.liumingyi.distance.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Calendar;
import top.liumingyi.ciel.RxBus;
import top.liumingyi.ciel.base.BaseRecyclerViewAdapter;
import top.liumingyi.ciel.utils.TimeUtils;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.Label;
import top.liumingyi.distance.events.OpenLabelAppendFragmentEvent;

public class LabelRecyclerAdapter extends BaseRecyclerViewAdapter<RecyclerView.ViewHolder, Label> {

  private static final int TYPE_FOOTER = 1;
  private static final int TYPE_NORMAL = 0;

  private Context context;

  public LabelRecyclerAdapter(Context context) {
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
      return new EventViewHolder(inflater.inflate(R.layout.item_label, parent, false));
    } else {
      return new EventFooterHolder(inflater.inflate(R.layout.item_label_add, parent, false));
    }
  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
    if (getItemViewType(position) == TYPE_NORMAL) {
      EventViewHolder holder = (EventViewHolder) viewHolder;
      Label item = getItem(position);
      holder.titleTv.setText(
          String.format(context.getString(R.string.event_title), item.getTitle()));
      Calendar endCalendar = item.getEndCalender();
      long days = TimeUtils.calculateApartDays(Calendar.getInstance(), endCalendar);
      holder.daysTv.setText(String.valueOf(days));
      holder.dateTv.setText(TimeUtils.dateFormat(endCalendar));
      holder.itemView.setTag(position);
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
      itemView.setOnLongClickListener(v -> {
        int position = (int) itemView.getTag();
        longClickListener.onRecyclerItemLongClick(position, getItem(position));
        return true;
      });
    }
  }

  private class EventFooterHolder extends RecyclerView.ViewHolder {

    EventFooterHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(v -> RxBus.getDefault().send(new OpenLabelAppendFragmentEvent()));
    }
  }
}
