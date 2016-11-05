package com.greenear.yeqinglu.greeneartech;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by yeqing.lu on 2016/11/5.
 */

public class RegisterActivity extends Activity {

    private EditText et_username;
    private EditText et_email;
    private EditText et_password;
    private EditText et_password_ens;
    private Button register;
    private Button cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        et_username = (EditText)findViewById(R.id.username);
        et_email = (EditText)findViewById(R.id.email);
        et_password = (EditText)findViewById(R.id.password);
        et_password_ens = (EditText)findViewById(R.id.password_ens);

        register = (Button)findViewById(R.id.register);
        cancel = (Button)findViewById(R.id.cancel);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = et_username.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String password_ens = et_password_ens.getText().toString().trim();

                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(email)||
                        TextUtils.isEmpty(password)||TextUtils.isEmpty(password_ens))
                {
                    Toast.makeText(RegisterActivity.this,"缺少必填信息！",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(RegisterActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
