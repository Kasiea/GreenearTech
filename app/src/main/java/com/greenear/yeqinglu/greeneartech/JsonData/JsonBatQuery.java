package com.greenear.yeqinglu.greeneartech.JsonData;

import java.util.ArrayList;

/**
 * Created by yeqing.lu on 2016/11/15.
 */

public class JsonBatQuery {

    private String code;
    private String msg;
    private ArrayList<JsonBatData> data;

    public ArrayList<JsonBatData> getData() {
        return data;
    }

    public void setData(ArrayList<JsonBatData> data) {
        this.data = data;
    }

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


}
