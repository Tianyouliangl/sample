package com.flb.sample.act

/**
 * author : 冯张伟
 * date : 2019/5/16
 */
interface IBasePresenter<out V : IViewContract> {
    fun getMvpView(): V
}