package com.flb.sample.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.flb.sample.BaseApplication;
import com.flb.sample.model.AlarmClockBean;

import java.util.ArrayList;
import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/5/9
 */
public class DBHelper extends SQLiteOpenHelper{

    public DBHelper(Context context) {
        super(context, Constant.type.TABLE_NAME, null, Constant.type.DB_VERSION);
    }

    // 当数据库文件创建时，执行初始化操作，并且只执行一次
    @Override
    public void onCreate(SQLiteDatabase db) {
        // 建表
        String sql = "create table " +
                Constant.type.TABLE_NAME +
                "( id integer primary key autoincrement, " +
                Constant.type.NAME + " varchar, " +
                Constant.type.time + " varchar, " +
                Constant.type.type + " varchar, " +
                Constant.type.number + " varchar, "+
                Constant.type.interval_time + " varchar, "+
                Constant.type.alarm_remark  + " varchar, " +
                Constant.type.open + " varchar, " +
                Constant.type.isdelete + " varchar, " +
                Constant.type.isShake + " varchar, " +
                Constant.type.isCommit + " varchar" +
                ")";

        db.execSQL(sql);
    }

    // 添加
    public static void insetClockData(AlarmClockBean bean){
        ContentValues values = new ContentValues();
        values.put(Constant.type.NAME, bean.getNAME());
        values.put(Constant.type.time, bean.getTime());
        values.put(Constant.type.type, bean.getType());
        values.put(Constant.type.number, bean.getNumber());
        values.put(Constant.type.interval_time, bean.getInterval_time());
        values.put(Constant.type.alarm_remark, bean.getAlarm_remark());
        values.put(Constant.type.open, bean.getOpen());
        values.put(Constant.type.isdelete, bean.getIsDelete());
        values.put(Constant.type.isShake, bean.getIsShake());
        values.put(Constant.type.isCommit, bean.getIsCommit());

        if (BaseApplication.getSQLiteDatabase().isOpen())
            BaseApplication.getSQLiteDatabase().insert(Constant.type.TABLE_NAME, null, values);
    }

    // 删除
    public static int deleteClockData(String clockTime){
        int count = BaseApplication.getSQLiteDatabase().delete(Constant.type.TABLE_NAME, Constant.type.NAME + " = ?", new String[]{clockTime});
        return count;
    }

    // 查询
    public static List<AlarmClockBean>  getClockDataAll(){
        AlarmClockBean clockBean = new AlarmClockBean();
        ArrayList<AlarmClockBean> clockBeanArrayList = new ArrayList<>();
        Cursor cursor = BaseApplication.getSQLiteDatabase().query(Constant.type.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        int name = cursor.getColumnIndex(Constant.type.NAME);
        int time = cursor.getColumnIndex(Constant.type.time);
        int type = cursor.getColumnIndex(Constant.type.type);
        int number = cursor.getColumnIndex(Constant.type.number);
        int interval_time = cursor.getColumnIndex(Constant.type.interval_time);
        int alarm_remark = cursor.getColumnIndex(Constant.type.alarm_remark);
        int open = cursor.getColumnIndex(Constant.type.open);
        int isDelete = cursor.getColumnIndex(Constant.type.isdelete);
        int isShake = cursor.getColumnIndex(Constant.type.isShake);
        int isCommit = cursor.getColumnIndex(Constant.type.isCommit);
        if (BaseApplication.getSQLiteDatabase().isOpen()){
            while (cursor.moveToNext()) {

                String q_name = cursor.getString(name);
                String q_time = cursor.getString(time);
                String q_type = cursor.getString(type);
                String q_number = cursor.getString(number);
                String q_interval_time = cursor.getString(interval_time);
                String q_alarm_remark = cursor.getString(alarm_remark);
                String q_open = cursor.getString(open);
                String q_isDelete = cursor.getString(isDelete);
                String q_isShake = cursor.getString(isShake);
                String q_isCommit = cursor.getString(isCommit);

                clockBean.setNAME(q_name);
                clockBean.setTime(q_time);
                clockBean.setType(q_type);
                clockBean.setNumber(q_number);
                clockBean.setInterval_time(q_interval_time);
                clockBean.setAlarm_remark(q_alarm_remark);
                clockBean.setOpen(q_open);
                clockBean.setIsDelete(q_isDelete);
                clockBean.setIsShake(q_isShake);
                clockBean.setIsCommit(q_isCommit);
                clockBeanArrayList.add(clockBean);
            }
        }

        return clockBeanArrayList;
    }

    public static int  isOpen(Boolean b,String time){
        ContentValues values = new ContentValues();
        values.put(Constant.type.time, time);
        if (b){
            values.put(Constant.type.open, "1");
        }
        if (!b){
            values.put(Constant.type.open, "0");
        }
        int count = BaseApplication.getSQLiteDatabase().update(Constant.type.TABLE_NAME, values, Constant.type.time + " = ?", new String[]{time});
        return count;
    }


    // 当数据库版本更新执行该方法
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
