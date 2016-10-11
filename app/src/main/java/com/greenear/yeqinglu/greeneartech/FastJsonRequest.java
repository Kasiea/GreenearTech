package com.greenear.yeqinglu.greeneartech;

import com.alibaba.fastjson.JSONObject;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

/**
 * Created by yeqing.lu on 2016/9/8.
 */
public class FastJsonRequest<T> extends Request<T> {

    private final Response.Listener<T> mListener;
    private Class<T> mClass;
    private JSONObject mFastJson;

    public FastJsonRequest(int method, String url, Class<T> clazz, Response.Listener<T> listener,
                           Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        mFastJson = new JSONObject();
        mClass = clazz;
        mListener = listener;
    }

    /*将服务器响应的数据解析出来，然后通过调用Gson的fromJson方法将数据组装成对象。
    在deliverResponse方法中仍然是将最终的数据进行回调*/
    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            return Response.success(mFastJson.parseObject(jsonString,mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        }catch (UnsupportedEncodingException e){
            return Response.error(new ParseError());
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
