package com.greenear.yeqinglu.greeneartech.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.greenear.yeqinglu.greeneartech.R;
import com.shinelw.library.ColorArcProgressBar;

/**
 * Created by yeqing.lu on 2017/3/8.
 */

public class BmsDataArcBar extends Activity {

    private ColorArcProgressBar bms_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bms_data_arc_bar_activity);

        initView();
        initData();

    }

    public void initView(){
        bms_bar = (ColorArcProgressBar) findViewById(R.id.bms_bar);

    }

    public void initData(){
        bms_bar.setCurrentValues(100);
    }

}
