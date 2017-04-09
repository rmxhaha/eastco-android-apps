package com.sai.customerapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.sai.customerapp.Fragments.FragmentShop;

public class MainActivity extends AppCompatActivity {

    private TextView myTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Action Bar Settings */
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        myTitle = (TextView) findViewById(R.id.titleBar);
        myTitle.setText("SHOP");

        //Bottom NavBar Fragments
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        FragmentShop fragmentShop = new FragmentShop();
        ft.replace(R.id.Frame_Container, fragmentShop);
        ft.commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                switch (id) {
                    case R.id.action_shop:
                        FragmentShop fragmentShop = new FragmentShop();
                        myTitle.setText("SHOP");

                        ft.replace(R.id.Frame_Container, fragmentShop);
                        ft.commit();

                        return true;
                    /*
                    case R.id.action_order:
                        FragmentOrder fragmentOrder = new FragmentOrder();
                        myTitle.setText("ORDER");

                        ft.replace(R.id.Frame_Container, fragmentOrder);
                        ft.commit();
                        return true;
                    case R.id.action_history:
                        FragmentHistory fragmentHistory = new FragmentHistory();
                        myTitle.setText("HISTORY");

                        ft.replace(R.id.Frame_Container, fragmentHistory);
                        ft.commit();
                        return true;
                    */
                }

                return false;

            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void switchContent(Fragment newFragment) {

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.Frame_Container, newFragment);
        ft.commit();

    }

}
