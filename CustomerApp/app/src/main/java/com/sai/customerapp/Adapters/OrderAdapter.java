package com.sai.customerapp.Adapters;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sai.customerapp.FragmentClasses.OrderInfo;
import com.sai.customerapp.FragmentClasses.TenantInfo;
import com.sai.customerapp.Fragments.FragmentMenu;
import com.sai.customerapp.MainActivity;
import com.sai.customerapp.R;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<OrderInfo> orderInfos;

    public OrderAdapter(Context context, List<OrderInfo> orderInfos) {
        this.context = context;
        this.orderInfos = orderInfos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_order_adapter_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        OrderInfo orderInfo = orderInfos.get(position);

    }


    @Override
    public int getItemCount() {
        return orderInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageView imageViewOrderProfPic;
        public TextView textViewOrderName;
        public TextView textViewOrderDescription;
        public TextView textViewOrderPrice;
        public Spinner spinnerOrderAddressValues;
        public TextView textViewTotalPriceValue;
        public Button buttonOrder;

        public ViewHolder(View itemView) {

            super(itemView);

            imageViewOrderProfPic = (ImageView) itemView.findViewById(R.id.imageViewOrderProfPic);
            textViewOrderName = (TextView) itemView.findViewById(R.id.textViewOrderName);
            textViewOrderDescription = (TextView) itemView.findViewById(R.id.textViewOrderDescription);

            textViewOrderPrice = (TextView) itemView.findViewById(R.id.textViewOrderPrice);
            spinnerOrderAddressValues = (Spinner) itemView.findViewById(R.id.spinnerOrderAddressValues);
            textViewTotalPriceValue = (TextView) itemView.findViewById(R.id.textViewTotalPriceValue);

            buttonOrder = (Button) itemView.findViewById(R.id.buttonOrder);

            buttonOrder.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            // SEND POST REQUEST TO SERVER


        }

    }
}