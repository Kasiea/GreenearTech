package com.greenear.yeqinglu.greeneartech.JsonData;

/**
 * Created by yeqing.lu on 2016/11/15.
 */

public class JsonBmsData {

    private String id;
    private String bms_id;
    private String soc;
    private String soh;
    private String vol;
    private String res;
    private String longitude;
    private String latitude;
    private String altitude;
    private String speed;
    private String locate_mode;
    private String satellite;
    private String temp;
    private String current;
    private String charge;
    private JsonBms bms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBms_id() {
        return bms_id;
    }

    public void setBms_id(String bms_id) {
        this.bms_id = bms_id;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getSoh() {
        return soh;
    }

    public void setSoh(String soh) {
        this.soh = soh;
    }

    public String getVol() {
        return vol;
    }

    public void setVol(String vol) {
        this.vol = vol;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getLocate_mode() {
        return locate_mode;
    }

    public void setLocate_mode(String locate_mode) {
        this.locate_mode = locate_mode;
    }

    public String getSatellite() {
        return satellite;
    }

    public void setSatellite(String satellite) {
        this.satellite = satellite;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public JsonBms getBms() {
        return bms;
    }

    public void setBms(JsonBms bms) {
        this.bms = bms;
    }


}
