package com.greenear.yeqinglu.greeneartech.model;

/**
 * Created by yeqing.lu on 2016/11/15.
 */

public class Bat {

    public String id;
    public String bat_id;
    public String soc;
    public String soh;
    public String vol;
    public String res;

    public void setId(String id) {
        this.id = id;
    }

    public void setBat_id(String bat_id) {
        this.bat_id = bat_id;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public void setSoh(String soh) {
        this.soh = soh;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public void setRes(String res) {
        this.res = res;
    }



    public String getId() {
        return id;
    }

    public String getBat_id() {
        return bat_id;
    }

    public String getSoc() {
        return soc;
    }

    public String getSoh() {
        return soh;
    }

    public String getVol() {
        return vol;
    }

    public String getRes() {
        return res;
    }

}
