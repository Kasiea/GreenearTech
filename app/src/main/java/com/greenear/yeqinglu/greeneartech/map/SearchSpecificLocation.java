package com.greenear.yeqinglu.greeneartech.map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.greenear.yeqinglu.greeneartech.R;
import com.greenear.yeqinglu.greeneartech.model.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

/**
 * Created by yeqing.lu on 2017/4/20.
 */

public class SearchSpecificLocation extends Activity {

    private EditText specific_location;
    private Button search_specific_location_cs;
    private ListView fuzzy_search_listview;

    public String specificLocationString;
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
        search_specific_location_cs = (Button)findViewById(R.id.search_specific_location_cs);
        fuzzy_search_listview = (ListView)findViewById(R.id.fuzzy_search_listview);
    }

    private void setConfigure(){
        TextChangeListener();
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

    public void setListViewAdapter()
    {
        fuzzy_search_listview.setAdapter(specificPostion.slArrayAdapter);
    }


}
