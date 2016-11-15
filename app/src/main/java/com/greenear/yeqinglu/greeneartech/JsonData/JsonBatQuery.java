package com.greenear.yeqinglu.greeneartech.JsonData;

/**
 * Created by yeqing.lu on 2016/11/15.
 */

public class JsonBatQuery {

    private String code;
    private String msg;
    private JsonBatData data;

    public JsonBatData getData() {
        return data;
    }

    public void setData(JsonBatData data) {
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
