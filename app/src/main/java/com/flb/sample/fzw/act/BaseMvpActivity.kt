package com.senyint.hrapp.common.act

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import com.flb.sample.fzw.act.IPresenterContract
import com.flb.sample.fzw.act.MvpActivity

/**
 * Author:Ljb
 * Time:2018/12/28
 * There is a lot of misery in life
 **/
abstract class BaseMvpActivity<out P : IPresenterContract> : MvpActivity<P>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init(savedInstanceState)
        initView()
        initData()
    }

    protected abstract fun getLayoutId(): Int

    protected open fun init(savedInstanceState: Bundle?) {}

    protected open fun initView() {}

    protected open fun initData() {}

    override fun getResources(): Resources {
        val res = super.getResources()
        if (res.configuration.fontScale != 1.0f) {
            val newConfig = Configuration()
            newConfig.setToDefaults()
            res.updateConfiguration(newConfig, res.displayMetrics)
        }
        return res
    }
}