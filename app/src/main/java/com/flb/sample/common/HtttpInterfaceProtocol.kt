package com.flb.sample.common

import com.flb.sample.model.VideoMd5Bean
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * author : 冯张伟
 * date : 2019/5/16
 */
interface HtttpInterfaceProtocol {




    @POST("checkVideoByMd5")
    fun Md5(@Query("md5") md5:String, @Body map:HashMap<String, Any> ): Observable<VideoMd5Bean>



}