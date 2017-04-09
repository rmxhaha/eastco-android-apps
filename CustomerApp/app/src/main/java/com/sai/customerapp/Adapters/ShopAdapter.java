package com.sai.customerapp.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sai.customerapp.FragmentClasses.TenantInfo;
import com.sai.customerapp.Fragments.FragmentMenu;
import com.sai.customerapp.MainActivity;
import com.sai.customerapp.R;

import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private Context context;
    private List<TenantInfo> tenantInfos;

    public ShopAdapter(Context context, List<TenantInfo> tenantInfos) {
        this.context = context;
        this.tenantInfos = tenantInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_shop_adapterLayout, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        TenantInfo tenantInfo = tenantInfos.get(position);

        holder.imageViewTenantProfPic.setImageResource(R.drawable.background_login);
        holder.textViewTenantName.setText(tenantInfo.tenantName);
        holder.textViewTenantDescription.setText(tenantInfo.tenantDescription);

    }


    @Override
    public int getItemCount() {
        return tenantInfos.size();
    }

    public void switchContent(Fragment fragment) {
        if (context == null)
            return;
        if (context instanceof MainActivity) {
            MainActivity mainActivity = (MainActivity) context;
            Fragment frag = fragment;
            mainActivity.switchContent(frag);
        }

    }

    private void fragmentJump(TenantInfo tenantInfoSelected) {
        FragmentMenu fragmentMenu = new FragmentMenu();

        Bundle data = new Bundle();
        data.putInt("tenantID", tenantInfoSelected.tenantID);

        fragmentMenu.setArguments(data);

        switchContent(fragmentMenu);
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageViewTenantProfPic;
        public TextView textViewTenantName;
        public TextView textViewTenantDescription;

        public ViewHolder(View itemView) {

            super(itemView);

            imageViewTenantProfPic = (ImageView) itemView.findViewById(R.id.imageViewTenantProfPic);
            textViewTenantName = (TextView) itemView.findViewById(R.id.textViewTenantName);
            textViewTenantDescription = (TextView) itemView.findViewById(R.id.textViewTenantDescription);

            imageViewTenantProfPic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    fragmentJump(tenantInfos.get(getAdapterPosition()));

                }

            });

        }

    }
}