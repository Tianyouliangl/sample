package com.flb.sample.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.flb.sample.common.DBHelper;
import com.flb.sample.model.AlarmClockBean;
import com.flb.sample.widgets.TimeUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/5/10
 */
public class AlarmClockService extends Service{


    private List<AlarmClockBean> sqList;
    private MyThread myThread;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            AlarmClockBean bean = (AlarmClockBean) msg.obj;
            switch (msg.what){
                case 1:
                    if (bean != null){
                        notification(bean);
                    }
                    break;
                    default:
                        myThread.run();
                        break;
            }
        }
    };

    private void notification(AlarmClockBean bean) {
        String number = bean.getNumber();
        String type = bean.getType();
        String commit = bean.getIsCommit();
        if (!commit.equals("1")) {
            if (number.equals("0")){
                if (type.equals("1")) {  //每天
                    bean.setNumber("1");
                    bean.setIsCommit("1");
                    DBHelper.update(bean,bean.getTime());
                    Log.e("aaaaa","----每天----不在响了");
                    EventBus.getDefault().postSticky(bean);
                }

                if (type.equals("2")){  // 一次
                    bean.setNumber("1");
                    bean.setIsCommit("1");
                    bean.setOpen("0");
                    DBHelper.update(bean,bean.getTime());
                    Log.e("aaaaa","----一次----关闭");
                    EventBus.getDefault().postSticky(bean);
                }

                if (type.equals("3")){ // 周一至周五
                    String s = TimeUtil.StringData();
                    if (s.equals("周一") ||
                            s.equals("周二") ||
                            s.equals("周三") ||
                            s.equals("周四") ||
                            s.equals("周五")){
                        bean.setNumber("1");
                        bean.setIsCommit("1");
                        DBHelper.update(bean,bean.getTime());
                        Log.e("aaaaa","----周一至周五----不在响了");
                    }
                }
            }

        }

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        myThread = new MyThread();
        myThread.start();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public class MyThread extends Thread{
        @Override
        public void run() {
            sendBroadcastReceiver();
            super.run();
        }
    }

    private void sendBroadcastReceiver() {
        sqList = DBHelper.getClockDataAll();
        for (int i=0;i<sqList.size();i++){
            AlarmClockBean bean = sqList.get(i);
            String open = bean.getOpen();
            String time = bean.getTime();
            String sy_time = TimeUtil.getTimeString("HH:mm");
            if (open.equals("1")){
                if (time.equals(sy_time)){
                    Message obtain = Message.obtain();
                    obtain.obj = bean;
                    obtain.what = 1;
                    mHandler.sendMessageDelayed(obtain,1000);
                }
            }
        }
        mHandler.sendEmptyMessageDelayed(0,1000);
    }


}
