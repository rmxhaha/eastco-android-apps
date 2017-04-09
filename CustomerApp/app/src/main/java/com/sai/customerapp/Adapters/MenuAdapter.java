package com.sai.customerapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sai.customerapp.FragmentClasses.MenuInfo;
import com.sai.customerapp.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context;
    private List<MenuInfo> menuInfos;

    public MenuAdapter(Context context, List<MenuInfo> menuInfos) {
        this.context = context;
        this.menuInfos = menuInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_menu_adapter_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        MenuInfo menuInfo = menuInfos.get(position);

    }


    @Override
    public int getItemCount() {
        return menuInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {

            super(itemView);

        }

    }
}