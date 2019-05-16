package com.flb.sample.act

/**
 * author : 冯张伟
 * date : 2019/5/16
 */
interface IPresenterContract {
    fun onCreate()

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

    fun registerMvpView(mvpView: IViewContract)
}