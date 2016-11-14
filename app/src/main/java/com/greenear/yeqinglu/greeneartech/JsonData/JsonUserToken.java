package com.greenear.yeqinglu.greeneartech.JsonData;

/**
 * Created by yeqing.lu on 2016/11/12.
 */

public class JsonUserToken {

    private String code;
    private String msg;
    private JsonTokenData data;

    public JsonTokenData getData() {
        return data;
    }

    public void setData(JsonTokenData data) {
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
