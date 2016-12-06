package com.greenear.yeqinglu.greeneartech.model;

/**
 * Created by yeqing.lu on 2016/11/7.
 */

public class UserInfo {

    public String name;
    public String password;
    public String token;

    public String bms_id;
    public String bat_id1;
    public String bat_id2;
    public String bat_id3;
    public String bat_id4;

    public String getBms_id() {
        return bms_id;
    }

    public void setBms_id(String bms_id) {
        this.bms_id = bms_id;
    }

    public String getBat_id1() {
        return bat_id1;
    }

    public void setBat_id1(String bat_id1) {
        this.bat_id1 = bat_id1;
    }

    public String getBat_id2() {
        return bat_id2;
    }

    public void setBat_id2(String bat_id2) {
        this.bat_id2 = bat_id2;
    }

    public String getBat_id3() {
        return bat_id3;
    }

    public void setBat_id3(String bat_id3) {
        this.bat_id3 = bat_id3;
    }

    public String getBat_id4() {
        return bat_id4;
    }

    public void setBat_id4(String bat_id4) {
        this.bat_id4 = bat_id4;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
