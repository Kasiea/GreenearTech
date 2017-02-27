package com.greenear.yeqinglu.greeneartech.others;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.greenear.yeqinglu.greeneartech.R;

public class MainDrawer extends AppCompatActivity {

    public DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_drawer);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
//        drawerLayout.openDrawer(GravityCompat.START);
    }
}
