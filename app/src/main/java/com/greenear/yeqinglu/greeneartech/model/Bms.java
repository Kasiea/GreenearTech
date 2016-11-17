package com.greenear.yeqinglu.greeneartech.model;

import java.util.ArrayList;

/**
 * Created by yeqing.lu on 2016/11/15.
 */

public class Bms {

    public String id;
    public String bms_id;
    public String soc;
    public String soh;
    public String vol;
    public String res;
    public String longitude;
    public String latitude;
    public String altitde;
    public String speed;
    public String locate_mode;
    public String satellite;
    public String temp;
    public String current;
    public String charge;

    public ArrayList<Bat> BatList;

    public ArrayList<Bat> getBatList() {
        return BatList;
    }

    public void setBatList(ArrayList<Bat> batList) {
        BatList = batList;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setBms_id(String bms_id) {
        this.bms_id = bms_id;
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

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setAltitde(String altitde) {
        this.altitde = altitde;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public void setLocate_mode(String locate_mode) {
        this.locate_mode = locate_mode;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }



    public String getId() {
        return id;
    }

    public String getBms_id() {
        return bms_id;
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

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getAltitde() {
        return altitde;
    }

    public String getSpeed() {
        return speed;
    }

    public String getLocate_mode() {
        return locate_mode;
    }

    public String getSatellite() {
        return satellite;
    }

    public String getTemp() {
        return temp;
    }

    public String getCurrent() {
        return current;
    }

    public String getCharge() {
        return charge;
    }


}
