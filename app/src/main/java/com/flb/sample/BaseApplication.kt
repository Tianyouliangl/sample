package com.flb.sample

import android.app.Application
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.flb.sample.alarm.ClockRemindActivity
import com.flb.sample.common.Constant
import com.flb.sample.common.DBHelper
import com.flb.sample.model.AlarmClockBean
import com.flb.sample.widgets.LogUtil
import net.ljb.kt.HttpConfig
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * author : 冯张伟
 * date : 2019/5/9
 */
class BaseApplication : Application() {

    companion object {

        private var helper: DBHelper? = null
        var sqLiteDatabase: SQLiteDatabase? = null
            private set
        var remindVisible: Boolean = false

        var context : Context ? = null

     }





    override fun onCreate() {
        super.onCreate()
        helper = DBHelper(this)
        sqLiteDatabase = helper!!.writableDatabase
        LogUtil.init(true)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        context = applicationContext
        val headers = mapOf<String, String>();
        val params = mapOf<String, String>()
        HttpConfig.init(Constant.type.HTTP_HOST!!, headers, params, BuildConfig.DEBUG)

    }


    override fun onTerminate() {
        // 程序终止的时候执行
        EventBus.getDefault().unregister(this)
        super.onTerminate()
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onMessageEvent(bean: AlarmClockBean) {
        if (!remindVisible) {
            val commit = bean.getIsCommit()
            Log.e("fzw", "----时间----" + bean.getTime() + "----是否提醒----" + bean.getIsCommit())
            if (commit == "0") {
                val i = Intent(this, ClockRemindActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                i.putExtra("bean", bean)
                startActivity(i)
                Log.e("fzw", "----提醒----")
            }
        }

    }


}
