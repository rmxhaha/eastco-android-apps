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

import com.sai.customerapp.Adapters.ShopAdapter;
import com.sai.customerapp.FragmentClasses.TenantInfo;
import com.sai.customerapp.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentShop extends Fragment {

    View v;
    WebView myWeb;

    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    private List<TenantInfo> tenantInfos;

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

        // retrieve report info from database
        retrieveShopInfo();
    }


    private void retrieveShopInfo() {

        // [TO-DO]
        // SEND POST REQUEST TO SERVER FOR THE LIST OF TENANT


        // [EXPERIMENT]

        // assuming that the responses from the server are ID, PROFPIC SOURCE, TENANT'S NAME, and TENANT'S DESCRIPTION
        int tenantID = 1;
        String tenantProfPic = "background_login";
        String tenantName = "Ayam-Ayaman";
        String tenantDescription = "Spesial chicken rice";

        // creating tenantInfo object (CAN BE MORE THAT ONE)
        TenantInfo TI = new TenantInfo(tenantID, tenantProfPic, tenantName, tenantDescription);

        // clearing list of tenantInfo
        tenantInfos = new ArrayList<>();

        // adding tenantInfo object to the list of tenantInfo
        tenantInfos.add(TI);

        // creating adapter
        adapter = new ShopAdapter(getActivity().getApplicationContext(), tenantInfos);

        // adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }

}