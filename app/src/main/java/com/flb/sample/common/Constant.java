package com.flb.sample.common;

/**
 * author : 冯张伟
 * date : 2019/4/19
 */
public class Constant {

    public static class type{

        public static final String HTTP_HOST = "http://62.234.173.137:8100/app/";

        // 数据库表名
        public static final String TABLE_NAME = "AlarmClockData";
        // 数据库版本号
        public static final int DB_VERSION = 1;
        public static final String NAME = "name"; // 闹钟标题
        public static final String time = "time"; // 响铃时间
        public static final String type = "type"; // 每天  一次  周一至周五  1   2   3
        public static final String number = "number"; // 响了几次
        public static final String interval_time = "interval_time"; // 响铃间隔时间  5分钟一次 3 次
        public static final String alarm_remark = "alarm_remark";  // 备注
        public static final String open = "alarm_open";  // 是否开启闹铃   0 no 1 yes
        public static final String isdelete = "alarm_delete";  // 是否响铃后删除(仅限于响铃一次)  0 不删除  1  删除
        public static final String isShake = "alarm_shake";  // 是否震动   0  no  1  yes
        public static final String isCommit = "alarm_commit";  // 是否完毕   0  no  1  yes
        public static String FILE_ALL = "全部文件";
        public static String FILE_MUSIC = "音乐";
        public static String FILE_VIDOE = "视频";
        public static String FILE_IMAGE = "图片";
        public static String FILE_RESTS = "其他文件";
        public static int    FILE_TYPE_ALL = 0;
        public static int    FILE_TYPE_MUSIC = 1;
        public static int    FILE_TYPE_VIDEO = 2;
        public static int    FILE_TYPE_IMAGE = 3;
        public static int    FILE_TYPE_RESTS = 4;
    }



}
