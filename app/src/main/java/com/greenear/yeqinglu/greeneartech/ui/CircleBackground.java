package com.greenear.yeqinglu.greeneartech.ui;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.MyApplication;

/**
 * Created by yeqing.lu on 2017/3/21.
 */

public class CircleBackground extends RelativeLayout {

    public ImageView outside_circle;
    public ImageView inside_circle;
    public TextView circle_text;

    public CircleBackground(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取到LayoutInflater的实例,加载布局
        LayoutInflater.from(context).inflate(R.layout.circle_background, this);

        initView();
        initData();
    }

    public void initView()
    {
        outside_circle = (ImageView)findViewById(R.id.outside_circle);
        inside_circle = (ImageView)findViewById(R.id.inside_circle);
        circle_text = (TextView)findViewById(R.id.circle_text);
    }

    public void initData()
    {
        Animator animator1 = AnimatorInflater.loadAnimator(MyApplication.getContext(), R.animator.outside_circle);
        animator1.setTarget(outside_circle);
        animator1.start();

        Animator animator2 = AnimatorInflater.loadAnimator(MyApplication.getContext(), R.animator.inside_circle);
        animator2.setTarget(inside_circle);
        animator2.start();
    }

    public void setOutside_circle(ImageView outside_circle) {
        this.outside_circle = outside_circle;
    }

    public void setInside_circle(ImageView inside_circle) {
        this.inside_circle = inside_circle;
    }

    public void setCircle_text(TextView circle_text) {
        this.circle_text = circle_text;
    }

}
