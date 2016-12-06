package com.greenear.yeqinglu.greeneartech.JsonData;

import java.util.ArrayList;

/**
 * Created by yeqing.lu on 2016/12/6.
 */

public class JsonBmsInfo {

    private String code;
    private String msg;
    private ArrayList<JsonBms> data;

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

    public ArrayList<JsonBms> getData() {
        return data;
    }

    public void setData(ArrayList<JsonBms> data) {
        this.data = data;
    }


}
