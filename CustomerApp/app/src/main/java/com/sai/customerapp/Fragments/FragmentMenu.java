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

import com.sai.customerapp.Adapters.MenuAdapter;
import com.sai.customerapp.Adapters.ShopAdapter;
import com.sai.customerapp.FragmentClasses.MenuInfo;
import com.sai.customerapp.FragmentClasses.TenantInfo;
import com.sai.customerapp.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentMenu extends Fragment {

    View v;
    WebView myWeb;

    //recyclerview object
    private RecyclerView recyclerView;

    //adapter object
    private RecyclerView.Adapter adapter;

    private List<MenuInfo> menuInfos;

    private int tenantID_BUNDLE_DATA;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        // menambahkan layout
        v = inflater.inflate(R.layout.fragment_recyclerView, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;

    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //you can set the title for your toolbar here for different fragments different titles
        //getActivity().setTitle("All Reports");

        // get bundle data
        tenantID_BUNDLE_DATA = getArguments().getInt("tenantID");

        // retrieve report info from database
        retrieveMenuInfo();
    }


    private void retrieveMenuInfo() {

        // [TO-DO]
        // SEND POST REQUEST TO SERVER FOR THE LIST OF MENU BASED ON THE TENANT ID


        // [EXPERIMENT]

        // assuming that the responses are menu's ID, name, description, price, and picture
        int menuID = 1;
        String menuName = "Ayam Cabe-Cabean";
        String menuDescription = "Ayam cabe paling enak. Cabe nya mantap";
        int menuPrice = 50000;
        String menuProfPic = "background_login";

        // creating menuInfo object (CAN BE MORE THAT ONE)
        MenuInfo MI = new MenuInfo(menuID, menuName, menuDescription, menuPrice, menuProfPic);

        // clearing list of menuInfo
        menuInfos = new ArrayList<>();

        // adding menuInfo object to the list of menuInfo
        menuInfos.add(MI);

        // creating adapter
        adapter = new MenuAdapter(getActivity().getApplicationContext(), menuInfos);

        // adding adapter to recyclerview
        recyclerView.setAdapter(adapter);

    }

}