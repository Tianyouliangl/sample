package com.flb.sample.fzw.common;

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
        public static String DATE_QI = "七日";
        public static String DATE_SHIWU = "十五日";
        public static String DATE_SANSHI = "三十日";
        public static int    FILE_TYPE_ALL = 0;
        public static int    FILE_TYPE_MUSIC = 1;
        public static int    FILE_TYPE_VIDEO = 2;
        public static int    FILE_TYPE_IMAGE = 3;
        public static int    FILE_TYPE_RESTS = 4;
        public static String json_chart = "  { \"thirtyDay\":[\n" +
                "        {\n" +
                "            \"date\":\"2019-05-26 00:00:00\",\n" +
                "                \"playCnt\":1170118,\n" +
                "                \"totalIncome\":479313.67,\n" +
                "                \"readCnt\":14206414,\n" +
                "                \"newIncome\":5127.72,\n" +
                "                \"fansCnt\":8020\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-31 00:00:00\",\n" +
                "                \"playCnt\":1465070,\n" +
                "                \"totalIncome\":497242.46,\n" +
                "                \"readCnt\":7828086,\n" +
                "                \"newIncome\":2659.95,\n" +
                "                \"fansCnt\":9859\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-15 00:00:00\",\n" +
                "                \"playCnt\":1785437,\n" +
                "                \"totalIncome\":443963.19,\n" +
                "                \"readCnt\":9956831,\n" +
                "                \"newIncome\":3408.33,\n" +
                "                \"fansCnt\":14708\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-13 00:00:00\",\n" +
                "                \"playCnt\":1473224,\n" +
                "                \"totalIncome\":436890.49,\n" +
                "                \"readCnt\":5654422,\n" +
                "                \"newIncome\":2770.19,\n" +
                "                \"fansCnt\":9174\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-09 00:00:00\",\n" +
                "                \"playCnt\":1600970,\n" +
                "                \"totalIncome\":424092.71,\n" +
                "                \"readCnt\":6146701,\n" +
                "                \"newIncome\":2877.31,\n" +
                "                \"fansCnt\":12446\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-17 00:00:00\",\n" +
                "                \"playCnt\":2193083,\n" +
                "                \"totalIncome\":453243.74,\n" +
                "                \"readCnt\":11496560,\n" +
                "                \"newIncome\":4725.11,\n" +
                "                \"fansCnt\":10575\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-08 00:00:00\",\n" +
                "                \"playCnt\":1863279,\n" +
                "                \"totalIncome\":420457.18,\n" +
                "                \"readCnt\":5955512,\n" +
                "                \"newIncome\":2216.34,\n" +
                "                \"fansCnt\":9024\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-11 00:00:00\",\n" +
                "                \"playCnt\":2346380,\n" +
                "                \"totalIncome\":431491.66,\n" +
                "                \"readCnt\":5507891,\n" +
                "                \"newIncome\":3826.95,\n" +
                "                \"fansCnt\":11941\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-28 00:00:00\",\n" +
                "                \"playCnt\":1250428,\n" +
                "                \"totalIncome\":486405.24,\n" +
                "                \"readCnt\":6081406,\n" +
                "                \"newIncome\":2880.32,\n" +
                "                \"fansCnt\":17535\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-27 00:00:00\",\n" +
                "                \"playCnt\":1497586,\n" +
                "                \"totalIncome\":485596.41,\n" +
                "                \"readCnt\":6954255,\n" +
                "                \"newIncome\":4319.4,\n" +
                "                \"fansCnt\":11266\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-19 00:00:00\",\n" +
                "                \"playCnt\":1793015,\n" +
                "                \"totalIncome\":457319.24,\n" +
                "                \"readCnt\":7435383,\n" +
                "                \"newIncome\":3437.5,\n" +
                "\t\t\t\t\"fansCnt\":25389\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-06-04 00:00:00\",\n" +
                "                \"playCnt\":381376,\n" +
                "                \"totalIncome\":504006.41,\n" +
                "                \"readCnt\":4799156,\n" +
                "                \"newIncome\":1757.84,\n" +
                "                \"fansCnt\":61163\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-06-01 00:00:00\",\n" +
                "                \"playCnt\":1079648,\n" +
                "                \"totalIncome\":500116.21,\n" +
                "                \"readCnt\":6095305,\n" +
                "                \"newIncome\":2912.68,\n" +
                "                \"fansCnt\":25389\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-12 00:00:00\",\n" +
                "                \"playCnt\":2219516,\n" +
                "                \"totalIncome\":434261.85,\n" +
                "                \"readCnt\":6481272,\n" +
                "                \"newIncome\":3572,\n" +
                "                \"fansCnt\":11668\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-07 00:00:00\",\n" +
                "                \"playCnt\":1572755,\n" +
                "                \"totalIncome\":417579.87,\n" +
                "                \"readCnt\":4071910,\n" +
                "                \"newIncome\":2205.72,\n" +
                "                \"fansCnt\":7260\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-10 00:00:00\",\n" +
                "                \"playCnt\":2286837,\n" +
                "                \"totalIncome\":427919.66,\n" +
                "                \"readCnt\":5497951,\n" +
                "                \"newIncome\":3635.53,\n" +
                "                \"fansCnt\":10808\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-14 00:00:00\",\n" +
                "                \"playCnt\":1302266,\n" +
                "                \"totalIncome\":440298.82,\n" +
                "                 \"readCnt\":5605811,\n" +
                "                \"newIncome\":2628.64,\n" +
                "                \"fansCnt\":9346\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-06 00:00:00\",\n" +
                "                \"playCnt\":1339229,\n" +
                "                \"totalIncome\":415363.53,\n" +
                "                \"readCnt\":3812790,\n" +
                "                \"newIncome\":2147.49,\n" +
                "                \"fansCnt\":7363\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-25 00:00:00\",\n" +
                "                \"playCnt\":1333317,\n" +
                "                \"totalIncome\":471074.33,\n" +
                "                \"readCnt\":10516144,\n" +
                "                \"newIncome\":3167.79,\n" +
                "                \"fansCnt\":13388\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-16 00:00:00\",\n" +
                "                \"playCnt\":1572658,\n" +
                "                \"totalIncome\":448688.3,\n" +
                "                \"readCnt\":10194260,\n" +
                "                \"newIncome\":3664.37,\n" +
                "                \"fansCnt\":13266\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-23 00:00:00\",\n" +
                "                \"playCnt\":1209684,\n" +
                "                \"totalIncome\":470101.18,\n" +
                "                \"readCnt\":13563017,\n" +
                "                \"newIncome\":2644.27,\n" +
                "                \"fansCnt\":13992\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-24 00:00:00\",\n" +
                "                \"playCnt\":1099437,\n" +
                "                \"totalIncome\":471074.33,\n" +
                "                \"readCnt\":13376314,\n" +
                "                \"newIncome\":3275.19,\n" +
                "                \"fansCnt\":15707\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-06-03 00:00:00\",\n" +
                "                \"playCnt\":1011311,\n" +
                "                \"totalIncome\":504006.41,\n" +
                "                \"readCnt\":5200672,\n" +
                "                \"newIncome\":1999.09,\n" +
                "                \"fansCnt\":10511\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-29 00:00:00\",\n" +
                "                \"playCnt\":1192837,\n" +
                "                \"totalIncome\":491761.58,\n" +
                "                \"readCnt\":8312052,\n" +
                "                \"newIncome\":3022.75,\n" +
                "                \"fansCnt\":625177\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-18 00:00:00\",\n" +
                "                \"playCnt\":2185706,\n" +
                "                \"totalIncome\":456681.24,\n" +
                "                \"readCnt\":9112987,\n" +
                "                \"newIncome\":4555.44,\n" +
                "                \"fansCnt\":16260\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-20 00:00:00\",\n" +
                "                \"playCnt\":1534471,\n" +
                "                \"totalIncome\":457730.88,\n" +
                "                \"readCnt\":7296420,\n" +
                "                \"newIncome\":2603.38,\n" +
                "                \"fansCnt\":11694\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-06-02 00:00:00\",\n" +
                "                \"playCnt\":1604568,\n" +
                "                \"totalIncome\":502107.52,\n" +
                "                \"readCnt\":5841968,\n" +
                "                \"newIncome\":2886.22,\n" +
                "                \"fansCnt\":483033\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-30 00:00:00\",\n" +
                "                \"playCnt\":1029141,\n" +
                "                \"totalIncome\":494398.73,\n" +
                "                \"readCnt\":7447680,\n" +
                "                \"newIncome\":3171.47,\n" +
                "                \"fansCnt\":7771\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-21 00:00:00\",\n" +
                "                \"playCnt\":1254780,\n" +
                "                \"totalIncome\":458513.08,\n" +
                "                \"readCnt\":4681893,\n" +
                "                \"newIncome\":2121.54,\n" +
                "                \"fansCnt\":151320\n" +
                "        },\n" +
                "        {\n" +
                "            \"date\":\"2019-05-22 00:00:00\",\n" +
                "                \"playCnt\":1368605,\n" +
                "                \"totalIncome\":459515.43,\n" +
                "                \"readCnt\":10244363,\n" +
                "                \"newIncome\":2775.56,\n" +
                "                \"fansCnt\":14816\n" +
                "        }\n" +
                "\t\t\t]\n" +
                "\t\t\t}\n";
    }


    public static class Video{

        public static final String PrivateKey = "-----BEGIN PRIVATE KEY-----\n" +
                "MIGHAgEAMBMGByqGSM49AgEGCCqGSM49AwEHBG0wawIBAQQgSJXSAZGSe3VNL9gK\n" +
                "Im4PViJhiPAS5cGgJsGDBFFgpaqhRANCAAR10X/1Jf4UXPgrbyjwUJVZlEs4x8wG\n" +
                "wA/JuICKcM3KyKrulcXIOQiOkBoMJ0lCy4aehs41e5YlOnFSkgCynVeP\n" +
                "-----END PRIVATE KEY-----\n";
        public static final int SdkAppId  = 1400226057;


    }



}
