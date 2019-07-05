package com.flb.sample.fzw

import android.app.Application
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import com.flb.sample.fzw.alarm.ClockRemindActivity
import com.flb.sample.fzw.common.Constant
import com.flb.sample.fzw.common.DBHelper
import com.flb.sample.fzw.model.AlarmClockBean
import com.flb.sample.fzw.widgets.LogUtil
import com.tencent.cos.xml.CosXmlService
import com.tencent.cos.xml.CosXmlServiceConfig
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider
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

        private  var cosXmlService: CosXmlService? = null

        fun getCosXmlService () : CosXmlService{
            return cosXmlService!!
        }

        val appId = "1258274484"

     }

    val region = "ap-beijing"
    val secretId = "AKIDDmMvYCB86zzyX5dG7xpt3ITaekpzlZPN" //永久密钥 secretId
    val secretKey = "VvmgCGPqKmG727dC5W8nTDxjKyadkP7m" //永久密钥 secretKey




    override fun onCreate() {
        super.onCreate()
        helper = DBHelper(this)
        sqLiteDatabase = helper!!.writableDatabase
        LogUtil.init(true)
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }
        initCos()
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
            if (commit == "0") {
                val i = Intent(this, ClockRemindActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                i.putExtra("bean", bean)
                startActivity(i)
            }
        }

    }

    fun initCos(){
        val serviceConfig = CosXmlServiceConfig.Builder()
                .setRegion(region)
                .isHttps(true) // 使用 https 请求, 默认 http 请求
                .setDebuggable(true)
                .builder()
        /**
         * 初始化 {@link QCloudCredentialProvider} 对象，来给 SDK 提供临时密钥。
         */
        val credentialProvider = ShortTimeCredentialProvider(secretId,
                secretKey, 300)
        cosXmlService = CosXmlService(this, serviceConfig, credentialProvider)
    }

}
