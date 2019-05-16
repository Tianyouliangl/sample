package com.flb.sample.presenter

import com.flb.sample.act.BaseRxLifePresenter
import com.flb.sample.common.ClientInfo
import com.flb.sample.common.HtttpInterfaceProtocol
import com.flb.sample.contract.VideoContract
import com.senyint.hrapp.common.ex.subscribeEx
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import net.ljb.kt.client.HttpFactory
import net.ljb.kt.utils.NetLog
import java.util.*

/**
 * author : 冯张伟
 * date : 2019/5/16
 */
class VideoPresenter : BaseRxLifePresenter<VideoContract.IView>(), VideoContract.IPresenter {

    var map = HashMap<String,Any>()

    override fun getMd5(md5: String) {

    map.put("clientInfo", ClientInfo.getClientInfo())
    map.put("properties", ClientInfo.getProperties())
    map.put("protocol", ClientInfo.protocol)
    map.put("protocolType", ClientInfo.protocolType)
    map.put("serviceInfo", ClientInfo.getServiceInfo())

        HttpFactory.getProtocol(HtttpInterfaceProtocol::class.java)
                .Md5("d50a9ddcceb0f2afae9bc4b7bd05ba4e",map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeEx(onNext = {
                   NetLog.d(it.toString())
                },onError ={

                },onComplete = {
                }).bindRxLifeEx(RxLife.ON_DESTROY)
    }

}
