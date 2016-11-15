package com.greenear.yeqinglu.greeneartech.interf;

import com.greenear.yeqinglu.greeneartech.model.Bat;
import com.greenear.yeqinglu.greeneartech.model.Bms;

/**
 * Created by yeqing.lu on 2016/11/14.
 */

public interface BaseUser {
    public void login();
    public void register();
    public Bms getBms();
    public Bat getBat();
}
