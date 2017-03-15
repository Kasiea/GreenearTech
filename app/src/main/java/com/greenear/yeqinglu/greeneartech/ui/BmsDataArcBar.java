package com.greenear.yeqinglu.greeneartech.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.greenear.yeqinglu.greeneartech.R;
import com.shinelw.library.ColorArcProgressBar;

/**
 * Created by yeqing.lu on 2017/3/8.
 */

public class BmsDataArcBar extends Activity {

    private ColorArcProgressBar bms_bar;
    private ImageView outside_circle;
    private ImageView inside_circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bms_data_arc_bar_activity);

        initView();
        initData();

    }

    public void initView(){
        bms_bar = (ColorArcProgressBar) findViewById(R.id.bms_bar);
        outside_circle = (ImageView)findViewById(R.id.outside_circle);
        inside_circle = (ImageView)findViewById(R.id.inside_circle);

    }

    public void initData(){
        bms_bar.setCurrentValues(100);

        Animator animator1 = AnimatorInflater.loadAnimator(this,R.animator.outside_circle);
        animator1.setTarget(outside_circle);
        animator1.start();

        Animator animator2 = AnimatorInflater.loadAnimator(this,R.animator.inside_circle);
        animator2.setTarget(inside_circle);
        animator2.start();
    }

}
