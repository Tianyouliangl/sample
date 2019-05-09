package com.flb.sample.alarm;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.common.DBHelper;
import com.flb.sample.model.AlarmClockBean;

public class SetAlarmClickActivity extends BaseActivity {


    @Override
    public int getContentView() {
        return R.layout.activity_set_alarm_click;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
//        public  String NAME; // 闹钟标题
//        public  String time; // 响铃时间
//        public  String type; // 每天  一次  周一至周五  1   2   3
//        public  String number; // 响了几次
//        public  String interval_time = "5"; // 响铃间隔时间
//        public  String alarm_remark;  // 备注
//        public  String open;  // 是否开启
//        public  String isdelete = "0";  // 是否响铃后删除(仅限于响铃一次)  0 不删除  1  删除
//        public  String isShake =  "1";  // 是否震动   0  no  1  yes
//        public  String isCommit = "0";  // 是否完毕   0  no  1  yes
        AlarmClockBean bean = new AlarmClockBean();
        bean.setNAME("上班");
        bean.setTime("8:00");
        bean.setType("1");
        bean.setNumber("0");
        bean.setAlarm_remark("有重要的会议");
        bean.setOpen("1");
        DBHelper.insetClockData(bean);
    }
}
