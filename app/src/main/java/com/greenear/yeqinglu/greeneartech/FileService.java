package com.greenear.yeqinglu.greeneartech;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.StreamTokenizer;
import java.util.HashMap;

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

//    public Map<String,String> getUserInfo(String filename)throws Exception{
////        File file = new File("data/data/greenear.yeqinglu.greeneartech/files/"+filename);
////        FileInputStream fis = new FileInputStream(file);
//        //以上的两句代码也可以通过以下的代码实现：
//        FileInputStream fis = context.openFileInput(filename);
//
//        byte[] data = StreamTools.getBytes(fis);
//        String result = new String(data);
//        String results[] = result.split(":");
//        Map<String,String> map = new HashMap<String,String>();
//        map.put("username",results[0]);
//        map.put("password",results[1]);
//        return map;
//    }

}
