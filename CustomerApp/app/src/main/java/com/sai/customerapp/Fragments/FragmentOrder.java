package com.sai.customerapp.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.sai.customerapp.Adapters.HistoryAdapter;
import com.sai.customerapp.Adapters.OrderAdapter;
import com.sai.customerapp.FragmentClasses.HistoryInfo;
import com.sai.customerapp.FragmentClasses.OrderInfo;
import com.sai.customerapp.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentOrder extends Fragment {

    View v;
    WebView myWeb;

    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    private List<OrderInfo> orderInfos;

    private int orderAmount_BUNDLE_DATA;
    private String orderDescription_BUNDLE_DATA;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        // menambahkan layout
        v = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // get bundle data
        orderAmount_BUNDLE_DATA = getArguments().getInt("orderAmount");
        orderDescription_BUNDLE_DATA = getArguments().getString("orderDescription");

        // retrieve report info from database
        retrieveOrderInfo();
    }


    private void retrieveOrderInfo() {

        // [TO-DO]
        // SEND POST REQUEST TO SERVER FOR THE LIST OF ORDER


        // [EXPERIMENT]

        // assuming that the responses from the server are menu's name, address, and total price
        String orderMenuName = "Ayam Cabe-Cabean";
        int orderMenuQuantity = 2;
        int orderMenuPrice = 28000;
        String orderMenuAddress = "Labtek V ITB";

        // creating orderInfo object (CAN BE MORE THAT ONE)
        OrderInfo OI = new OrderInfo(orderMenuName, orderMenuQuantity, orderMenuPrice, orderMenuAddress);

        // clearing list of tenantInfo
        orderInfos = new ArrayList<>();

        // adding tenantInfo object to the list of tenantInfo
        orderInfos.add(OI);

        // creating adapter
        adapter = new OrderAdapter(getActivity().getApplicationContext(), orderInfos);

        // adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }

}