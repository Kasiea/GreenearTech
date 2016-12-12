package com.greenear.yeqinglu.greeneartech.JsonData;

import java.util.ArrayList;

/**
 * Created by yeqing.lu on 2016/12/12.
 */

public class JsonBmsBatData {

    public String code;
    public String msg;
    public ArrayList<JsonBatData> data;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<JsonBatData> getData() {
        return data;
    }

    public void setData(ArrayList<JsonBatData> data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
