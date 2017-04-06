package mj.eastco;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class mainActivity extends AppCompatActivity {


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
        myTitle.setText("Your Orders");

        //Bottom NavBar Fragments
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        FragmentOrders fragmentOrders = new FragmentOrders();
        ft.replace(R.id.Frame_Container, fragmentOrders);
        ft.commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        int id = item.getItemId();
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction ft = fm.beginTransaction();
                        switch (id) {
                            case R.id.action_order:
                                FragmentOrders fragmentOrders = new FragmentOrders();
                                myTitle.setText("Your Orders");
                                ft.replace(R.id.Frame_Container, fragmentOrders);
                                ft.commit();
                                return true;
                            case R.id.action_menu:
                                FragmentMenu fragmentMenu = new FragmentMenu();
                                myTitle.setText("Your Menu");
                                ft.replace(R.id.Frame_Container, fragmentMenu);
                                ft.commit();
                                return true;
                            case R.id.action_home:
                                FragmentTenant fragmentTenant= new FragmentTenant();
                                myTitle.setText("Home");
                                ft.replace(R.id.Frame_Container, fragmentTenant);
                                ft.commit();
                                return true;
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
}
