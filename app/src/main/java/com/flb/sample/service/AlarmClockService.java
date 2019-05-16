package com.flb.sample.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

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
        EventBus.getDefault().postSticky(bean);
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
