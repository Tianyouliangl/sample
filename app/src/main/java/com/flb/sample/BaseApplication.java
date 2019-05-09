package com.flb.sample;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.flb.sample.common.DBHelper;
import com.flb.sample.widgets.LogUtil;

/**
 * author : 冯张伟
 * date : 2019/5/9
 */
public class BaseApplication extends Application{

    private static DBHelper helper;
    private static SQLiteDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        helper = new DBHelper(this);
        database = helper.getWritableDatabase();
        LogUtil.init(true);
    }

    public static SQLiteDatabase getSQLiteDatabase(){
        return database;
    }

}
