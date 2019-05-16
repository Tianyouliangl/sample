package com.flb.sample.act

/**
 * author : 冯张伟
 * date : 2019/5/16
 */
interface IBaseView<out P : IPresenterContract> {
    fun registerPresenter(): Class<out P >
}