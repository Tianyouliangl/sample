package com.flb.sample.fzw.contract

import com.flb.sample.fzw.act.IPresenterContract
import com.flb.sample.fzw.act.IViewContract

/**
 * author : 冯张伟
 * date : 2019/5/16
 */
interface VideoContract {

    interface IView : IViewContract {

    }

    interface IPresenter : IPresenterContract {
        fun getMd5(md5:String)
    }
}