package com.sai.customerapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sai.customerapp.FragmentClasses.HistoryInfo;
import com.sai.customerapp.R;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private Context context;
    private List<HistoryInfo> historyInfos;

    public HistoryAdapter(Context context, List<HistoryInfo> historyInfos) {
        this.context = context;
        this.historyInfos = historyInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_history_adapter_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        HistoryInfo historyInfo = historyInfos.get(position);

        holder.textViewMenuName_HISTORY.setText(historyInfo.historyMenuName);
        holder.textViewAddress_HISTORY.setText(historyInfo.historyMenuName);
        holder.textViewMenuPrice_HISTORY.setText(historyInfo.historyMenuName);

    }


    @Override
    public int getItemCount() {
        return historyInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewMenuName_HISTORY;
        public TextView textViewAddress_HISTORY;
        public TextView textViewMenuPrice_HISTORY;

        public ViewHolder(View itemView) {

            super(itemView);

            textViewMenuName_HISTORY = (TextView) itemView.findViewById(R.id.textViewMenuName_HISTORY);
            textViewAddress_HISTORY = (TextView) itemView.findViewById(R.id.textViewAddress_HISTORY);
            textViewMenuPrice_HISTORY = (TextView) itemView.findViewById(R.id.textViewMenuPrice_HISTORY);

        }

    }
}