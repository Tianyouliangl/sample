package com.flb.sample.fzw.act

/**
 * author : 冯张伟
 * date : 2019/5/16
 */
interface IBasePresenter<out V : IViewContract> {
    fun getMvpView(): V
}