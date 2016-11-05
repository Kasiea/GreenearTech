package com.greenear.yeqinglu.greeneartech;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.password;

/**
 * Created by yeqing.lu on 2016/10/19.
 */

public class LoginActivity extends Activity {

    private EditText et_username;
    private EditText et_password;
    private Button login;
    private CheckBox save_info;
    private FileService fileService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        et_username = (EditText)findViewById(R.id.username);
        et_password = (EditText)findViewById(R.id.password);
        save_info = (CheckBox)findViewById(R.id.save_info);
        login = (Button)findViewById(R.id.login);

        //初始化文件服务
        fileService = new FileService(this);

//        try {
//            Map<String,String >map = fileService.getUserInfo("private.txt");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();


                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this,R.string.error,Toast.LENGTH_SHORT).show();
                }
                if (save_info.isChecked())
                {
                    Toast.makeText(LoginActivity.this,R.string.remember_password,Toast.LENGTH_SHORT).show();
                   try {
                       boolean result =fileService.saveToRoom(username,password,"private.txt");
                       if (result){
                           Toast.makeText(LoginActivity.this, R.string.remember_password,Toast.LENGTH_SHORT).show();
                       }else
                       {
                           Toast.makeText(LoginActivity.this, R.string.success,Toast.LENGTH_SHORT).show();
                       }
                   }
                   catch (Exception e){
                       e.printStackTrace();
                       Toast.makeText(LoginActivity.this, R.string.fail,Toast.LENGTH_SHORT).show();
                   }

                }
            }
        });

    }
}
