package com.sai.customerapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.sai.customerapp.R;

import java.util.ArrayList;

public class FragmentShop extends Fragment {

    View v;
    WebView myWeb;

    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        // menambahkan layout
        v = inflater.inflate(R.layout.fragment_shop_recyclerView, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        tenantInfo = new ArrayList<>();

        return v;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("All Reports");

        // retrieve report info from database
        retrieveShopInfo();
    }


    private void retrieveShopInfo() {

        // [TO-DO]
        // SEND POST REQUEST TO SERVER FOR THE LIST OF TENANT


        // [EXPERIMENT]

        // creating list of tenantInfo
        tenantInfo = new ArrayList<>();

        // creating tenantInfo object
        TenantInfo tenantInfo = new TenantInfo();

        // creating adapter
        adapter = new ShopAdapter(getActivity().getApplicationContext(), tenantInfo);

        // adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }

}