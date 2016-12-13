package com.greenear.yeqinglu.greeneartech.interf;

import com.greenear.yeqinglu.greeneartech.model.Bat;
import com.greenear.yeqinglu.greeneartech.model.Bms;
import com.greenear.yeqinglu.greeneartech.model.CharingStationAround;
import com.greenear.yeqinglu.greeneartech.model.UserInfo;

import java.util.ArrayList;

/**
 * Created by yeqing.lu on 2016/11/14.
 */

public interface BaseUser {
     void login();
     void register();
     void saveInfo();
     UserInfo getInfo();
     Bms getBms(String bms_id);
     Bat getBat(String bat_id, int num);
     Bms getBms_Bat();
     ArrayList<CharingStationAround> getChargingStation(double longitude, double latitude);
}
