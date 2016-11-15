package com.greenear.yeqinglu.greeneartech.others;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by yeqing.lu on 2016/11/8.
 */

public class DBHelper extends SQLiteOpenHelper{

    public static final String BMS_DATA = "crate table bms_data("
            +"id integer primary key autoincrement,"
            +"bms_id integer,"
            +"soc float,"
            +"soh float,"
            +"vol float,"
            +"res float)";

    private Context mContext;


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        context = mContext;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(BMS_DATA);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_SHORT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
