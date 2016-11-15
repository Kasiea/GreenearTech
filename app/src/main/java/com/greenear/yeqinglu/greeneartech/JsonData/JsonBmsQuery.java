package com.greenear.yeqinglu.greeneartech.JsonData;

/**
 * Created by yeqing.lu on 2016/11/15.
 */

public class JsonBmsQuery {

    private String code;
    private String msg;
    private JsonBmsData data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public JsonBmsData getData() {
        return data;
    }

    public void setData(JsonBmsData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
