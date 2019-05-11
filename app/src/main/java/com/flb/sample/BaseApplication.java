package com.flb.sample;

import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import com.flb.sample.alarm.ClockRemindActivity;
import com.flb.sample.common.DBHelper;
import com.flb.sample.model.AlarmClockBean;
import com.flb.sample.widgets.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
        if(!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    @Override
    public void onTerminate() {
        // 程序终止的时候执行
        EventBus.getDefault().unregister(this);
        super.onTerminate();
    }


    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessageEvent(AlarmClockBean bean) {
        Intent i=new Intent(this,ClockRemindActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("bean",bean);
        startActivity(i);
    }


    public static SQLiteDatabase getSQLiteDatabase(){
        return database;
    }


}
