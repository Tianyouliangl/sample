package com.flb.sample.fzw.widgets;

import com.flb.sample.fzw.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * author : 冯张伟
 * date : 2019/5/10
 */
public class TimeUtil {

    public static int[] bgColor = {
            R.drawable.shape_gray, // 0
            R.drawable.shape_green,// 1
            R.drawable.shape_azaury,// 2
            R.drawable.shape_red,  // 3
            R.drawable.shape_green,// 4
            R.drawable.shape_azaury, // 5
            R.drawable.shape_red,   // 6
            R.drawable.shape_green, // 7
            R.drawable.shape_azaury, // 8
            R.drawable.shape_red, // 9
            R.drawable.shape_green,// 10
            R.drawable.shape_azaury, // 11
            R.drawable.shape_red, // 12
            R.drawable.shape_gray, // 13
            R.drawable.shape_gray, // 14
            R.drawable.shape_red, //15
            R.drawable.shape_green,// 16
            R.drawable.shape_azaury, //17
            R.drawable.shape_red, // 18
            R.drawable.shape_green, // 19
            R.drawable.shape_azaury, //20
            R.drawable.shape_red, // 21
            R.drawable.shape_green, // 22
            R.drawable.shape_azaury, //23
            R.drawable.shape_red, // 24
            R.drawable.shape_green, // 25
            R.drawable.shape_azaury, // 26
            R.drawable.shape_gray // 27
    };

    public static String[] bgColorName = {
            "灰色", // 0
            "绿色", // 1
            "蓝色", // 2
            "红色", // 3
            "绿色", // 4
            "蓝色", // 5
            "红色", // 6
            "绿色", // 7
            "蓝色", // 8
            "红色", // 9
            "绿色", // 10
            "蓝色", // 11
            "红色", // 12
            "灰色", // 13
            "灰色", // 14
            "红色", // 15
            "绿色", // 16
            "蓝色", // 17
            "红色", // 18
            "绿色", // 19
            "蓝色", // 20
            "红色", // 21
            "绿色", // 22
            "蓝色", // 23
            "红色", // 24
            "绿色", // 25
            "蓝色", // 26
            "灰色"  // 27
    };

    /**
     * 获取当前时间的毫秒数
     */
    public static long getTimeLong() {
        return System.currentTimeMillis();
    }

    /**
     * 获取当时时间的年，月，日，时分秒
     * 这里获得的时int类型的数据，要输入对应的格式
     * 比如要获得现在时间的天数值，
     * 使用getTime（“MM”）,如果现在是2016年5月20日，取得的数字是5；
     */
    public static int getTimeInt(String filter) {
        //
        SimpleDateFormat format = new SimpleDateFormat(filter);
        String time = format.format(new Date());
        return Integer.parseInt(time);
    }


    /**
     * 获取指定时间的年，月，日，时分秒
     * 这里获得的时int类型的数据，要输入完整时间的字符串和对应的格式
     * 比如要获得时间2016-12-12 14：12：10的小时的数值，
     * 使用getTime（“2016-12-12 14：12：10”，“HH”）；得到14
     */
    public static int getTimeInt(String StringTime, String filter) {
        //
        SimpleDateFormat format = new SimpleDateFormat(filter);
        String time = format.format(new Date(getTimeLong("yyyy-MM-dd HH:mm:ss", StringTime)));
        return Integer.parseInt(time);
    }


    /**
     * 获取当前时间的完整显示字符串
     */
    public static final String getTimeString() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(getTimeLong()));
    }

    /**
     * 获得某个时间的完整格式的字符串
     * 输入的是某个时间的毫秒数，
     * 有些时候文件保存的时间是毫秒数，这时就需要转换来查看时间了！
     */
    public static final String getTimeString(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(time));
    }

    /**
     * 获得自定义格式的时间字符串
     * 输入的是某个时间的毫秒数，显示的可以是时间字符串的某一部分
     * 比如想要获得某一个时间的月和日，getTimeString(1111111111111,"MM-dd");
     */
    public static final String getTimeString(long time, String filter) {
        SimpleDateFormat format = new SimpleDateFormat(filter);
        return format.format(new Date(time));
    }

    /**
     * 获得自定义格式的当前的时间的字符串
     * 比如当前时间2016年8月8日12时8分21秒，getTimeString("yyyy-MM-dd"),可以得到 2016-8-12
     */
    public static final String getTimeString(String filter) {
        SimpleDateFormat format = new SimpleDateFormat(filter);
        return format.format(new Date(getTimeLong()));
    }

    /**
     * 获取某个时间的毫秒数，
     * 一般作用于时间先后的对比
     * 第一个参数是时间的格式，第二个参数是时间的具体值
     * 比如需要知道2016-6-20的毫秒数（小时和分钟默认为零），
     * getTimeLong("yyyy-MM-dd","2016-6-20")
     * 有时只有月日也是可以的，比如  getTimeLong("MM-dd","6-20") ，一般这个用来比较时间先后
     * 记住获得的毫秒数越大，时间就越近你
     */
    public static Long getTimeLong(String filter, String date) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(filter);
            Date dateTime = format.parse(date);
            return dateTime.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0L;
    }


    /**
     * 获得某一个时间字符串中的局部字符串
     * 比如：String data= "2016-5-20 12：12：10"，要获得后面的时间：5-20或 12：10
     * 使用：getTimeLocalString("yyyy-MM-dd HH:mm:ss",data,"MM-dd")   ，可以获得5-20
     * 如果是data="2016-5-20"，要获得后面的5-20，
     * 也是一样的用法getTimeLocalString("yyyy-MM-dd ",data,"MM-dd")！
     */
    public static String getTimeLocalString(String filter, String data, String filterInside) {
        Long timeLong = getTimeLong(filter, data);
        return getTimeString(timeLong, filterInside);
    }

    public static String StringData(){
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
       String mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
        if("1".equals(mWay)){
            mWay ="日";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }
        return "周"+mWay;
    }

}
