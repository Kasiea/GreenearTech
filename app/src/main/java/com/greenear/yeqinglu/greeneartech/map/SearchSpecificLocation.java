package com.greenear.yeqinglu.greeneartech.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.baidu.mapapi.model.LatLng;
import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.MyApplication;


/**
 * Created by yeqing.lu on 2017/4/20.
 */

public class SearchSpecificLocation extends Activity {

    private EditText specific_location;
    private Button cancel;
    private ListView fuzzy_search_listview;

    private String specificLocationString;
    private SpecificPostion specificPostion;

    private static int isFinished = 1;

    //Handler更新ListView
    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == isFinished){
                setListViewAdapter();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_specific_location);

        initView();
        setConfigure();
    }

    private void initView(){
        specific_location = (EditText)findViewById(R.id.specific_location);
        cancel = (Button)findViewById(R.id.cancel);
        fuzzy_search_listview = (ListView)findViewById(R.id.fuzzy_search_listview);
    }

    private void setConfigure(){
        TextChangeListener();
        setListViewItemListener();
        cancel();
    }

    //地址输入栏监听
    private void TextChangeListener(){
        specific_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                specificLocationString = specific_location.getText().toString();
                specificPostion = new SpecificPostion(specificLocationString, handler);
            }
        });
    }

    private void setListViewAdapter() {
        fuzzy_search_listview.setAdapter(specificPostion.slArrayAdapter);
    }

    //ListView点击监听
    private void setListViewItemListener(){
        fuzzy_search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (specificPostion.specificLocationDetail.get(position).pt == null){
                    Toast.makeText(MyApplication.getContext(), "无法找到该位置！", Toast.LENGTH_SHORT).show();
                }else {
                    double latitude = specificPostion.specificLocationDetail.get(position).pt.latitude;
                    double longitude = specificPostion.specificLocationDetail.get(position).pt.longitude;
//                    Toast.makeText(MyApplication.getContext(), "Latng" + latitude + "," + longitude, Toast.LENGTH_SHORT).show();

                    //向上一级Activity传入所选择的具体位置的坐标
                    Intent intent = new Intent();
                    intent.putExtra("latitude", latitude);
                    intent.putExtra("longitude",longitude);
                    setResult(RESULT_OK, intent);

                    finish();
                }
            }
        });
    }

    public void cancel(){
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
