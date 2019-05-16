package com.flb.sample.model;

import java.io.Serializable;

/**
 * author : 冯张伟
 * date : 2019/5/9
 */
public class AlarmClockBean implements Serializable{
    private Long id;
    public  String NAME; // 闹钟名字
    public  String type; // 每天  一次  周一至周五  1   2   3
    public  String number = "0"; // 响了几次
    public  String interval_time = "5"; // 响铃间隔时间  0 不开启延时提醒   (暂时用不着)
    public  String alarm_remark;  // 备注
    public  String open;  // 是否开启闹钟  0  关闭  1  打开
    public  String isDelete = "0";  // 是否响铃后删除(仅限于响铃一次)  0 不删除  1  删除
    public  String isShake =  "1";  // 是否震动   0  no  1  yes
    public  String isCommit = "0";  // 是否完毕   0  no  1  yes
    public  String time; // 响铃时间

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInterval_time() {
        return interval_time;
    }

    public void setInterval_time(String interval_time) {
        this.interval_time = interval_time;
    }

    public String getAlarm_remark() {
        return alarm_remark;
    }

    public void setAlarm_remark(String alarm_remark) {
        this.alarm_remark = alarm_remark;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsShake() {
        return isShake;
    }

    public void setIsShake(String isShake) {
        this.isShake = isShake;
    }

    public String getIsCommit() {
        return isCommit;
    }

    public void setIsCommit(String isCommit) {
        this.isCommit = isCommit;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
