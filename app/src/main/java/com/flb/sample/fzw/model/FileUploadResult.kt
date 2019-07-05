package com.flb.sample.fzw.model


/**
 * Author:Ljb
 * Time:2018/9/19
 * There is a lot of misery in life
 **/
data class FileUploadResult(val header: HeaderBean, val content: ContentBean)

data class HeaderBean(val status: Int, val message: String)

data class ContentBean(val list: ArrayList<UploadResult>)

data class UploadResult(val msg: String?, val code: String?, val compressUrl: String?, val url: String?)