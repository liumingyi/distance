package top.liumingyi.distance.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import top.liumingyi.ciel.base.BaseRecyclerViewAdapter;
import top.liumingyi.distance.R;
import top.liumingyi.distance.data.datakeywords.DateKeyword;

public class DateKeywordRecyclerAdapter
    extends BaseRecyclerViewAdapter<DateKeywordRecyclerAdapter.LabelViewHolder, DateKeyword>
    implements View.OnClickListener {

  @NonNull @Override
  public LabelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.label_recycler_item, parent, false);
    view.setOnClickListener(this);
    return new LabelViewHolder(view);
  }

  @Override public void onBindViewHolder(@NonNull LabelViewHolder holder, int position) {
    DateKeyword keyword = getItem(position);
    holder.labelTv.setText(keyword.getName());
    holder.itemView.setTag(position);
  }

  @Override public void onClick(View v) {
    int position = (int) v.getTag();
    if (listener != null) {
      listener.onRecyclerItemClick(position, getItem(position));
    }
  }

  class LabelViewHolder extends RecyclerView.ViewHolder {

    private TextView labelTv;

    LabelViewHolder(View itemView) {
      super(itemView);
      labelTv = itemView.findViewById(R.id.label_tv);
    }
  }
}
