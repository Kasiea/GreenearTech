package com.greenear.yeqinglu.greeneartech.map;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.MyApplication;
import com.greenear.yeqinglu.greeneartech.model.SpecificAddress;
import com.greenear.yeqinglu.greeneartech.service.SpecificAddressAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yeqing.lu on 2017/4/20.
 */

public class SpecificPostion {

    //模糊搜索位置相关
    private String specificLocation;
    private SuggestionSearch slSuggestionSearch;

//    public ArrayAdapter<String> slArrayAdapter;
//    private List<String> slArrayList = new ArrayList<String>();

    //自定义ListView适配器
    public SpecificAddressAdapter specificAddressAdapter;
    private List<SpecificAddress> specificAddressList = new ArrayList<>();

    //获取具体位置LatLng相关
    public List<SuggestionResult.SuggestionInfo> specificLocationDetail;

    //Handler传输相关
    private Handler handler;

    public SpecificPostion(String specificLocation, Handler handler)
    {
        this.specificLocation = specificLocation;
        this.handler = handler;

        //模糊查询
        suggestionSearchAddress();

    }


    //模糊查询具体地址
    public void suggestionSearchAddress(){
        OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
            @Override
            public void onGetSuggestionResult(SuggestionResult suggestionResult) {
                if (suggestionResult == null || suggestionResult.getAllSuggestions() == null) {
                    return;
                    //未找到相关结果
                }else
                {
                    List<SuggestionResult.SuggestionInfo> resl=suggestionResult.getAllSuggestions();
                    //传入公共分享变量
                    specificLocationDetail = resl;

                    //获取具体地址列表
                    for(int i=0;i<resl.size();i++)
                    {
                        Log.i("result: ","city"+resl.get(i).city+" dis "+resl.get(i).district+"key "+resl.get(i).key);

//                        slArrayList.add(i,resl.get(i).city+" "+resl.get(i).district+" "+resl.get(i).key);

                        SpecificAddress specificAddress = new SpecificAddress(resl.get(i).city+" "+resl.get(i).district+" "+resl.get(i).key);
                        specificAddressList.add(i,specificAddress);
                    }
//                    slArrayAdapter = new ArrayAdapter<String>(MyApplication.getContext(),android.R.layout.simple_list_item_1,slArrayList);
                    //设置适配器
                    specificAddressAdapter = new SpecificAddressAdapter(MyApplication.getContext(), R.layout.specific_address_item, specificAddressList);

                    Message message = Message.obtain(handler);
                    message.what = 1;
                    message.sendToTarget();
                }
                //获取在线建议检索结果
        }
        };

        slSuggestionSearch = SuggestionSearch.newInstance();
        slSuggestionSearch.setOnGetSuggestionResultListener(listener);//设置监听
        slSuggestionSearch.requestSuggestion(new SuggestionSearchOption().
        city("上海").keyword(specificLocation));

    }
}
