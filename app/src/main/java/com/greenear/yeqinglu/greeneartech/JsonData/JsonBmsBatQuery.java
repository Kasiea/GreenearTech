package com.greenear.yeqinglu.greeneartech.JsonData;

import java.util.ArrayList;

/**
 * Created by yeqing.lu on 2016/11/26.
 */

public class JsonBmsBatQuery {

    private String code;
    private String msg;
    private ArrayList<JsonBat> data;

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

    public ArrayList<JsonBat> getData() {
        return data;
    }

    public void setData(ArrayList<JsonBat> data) {
        this.data = data;
    }




}
