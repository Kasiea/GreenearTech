package com.greenear.yeqinglu.greeneartech.JsonData;

import java.util.ArrayList;

/**
 * Created by yeqing.lu on 2016/12/12.
 */

public class JsonStationQuery {

    public String code;
    public String msg;
    public ArrayList<JsonStationData> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ArrayList<JsonStationData> getData() {
        return data;
    }

    public void setData(ArrayList<JsonStationData> data) {
        this.data = data;
    }

}
