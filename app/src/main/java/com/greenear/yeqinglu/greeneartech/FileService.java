package com.greenear.yeqinglu.greeneartech;

import android.content.Context;

import java.io.FileOutputStream;

/**
 * Created by yeqing.lu on 2016/11/5.
 */

public class FileService {

    private Context context;

    public FileService (Context context)
    {
        this.context = context;
    }

    public boolean saveToRoom(String username, String password,
                              String filename)throws Exception{
        //以私有的方式打开一个文件
        FileOutputStream fos = context.openFileOutput(filename,Context.MODE_PRIVATE);
        String result = username+":"+password;
        fos.write(result.getBytes());
        fos.flush();
        fos.close();
        return true;
    }

}
