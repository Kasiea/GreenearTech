package com.greenear.yeqinglu.greeneartech.interf;

import com.greenear.yeqinglu.greeneartech.model.Bat;
import com.greenear.yeqinglu.greeneartech.model.Bms;
import com.greenear.yeqinglu.greeneartech.model.UserInfo;

/**
 * Created by yeqing.lu on 2016/11/14.
 */

public interface BaseUser {
    public void login();
    public void register();
    public void saveInfo();
    public UserInfo getInfo();
    public Bms getBms();
    public Bat getBat();
}
