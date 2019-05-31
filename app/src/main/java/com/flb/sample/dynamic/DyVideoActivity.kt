package com.flb.sample.dynamic

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import cn.jzvd.Jzvd
import com.flb.sample.R
import com.flb.sample.contract.VideoContract
import com.flb.sample.presenter.VideoPresenter
import com.flb.sample.widgets.UpLoadingFileManager
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.senyint.hrapp.common.act.BaseMvpActivity
import kotlinx.android.synthetic.main.activity_dy_video.*
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager
import uk.co.barbuzz.beerprogressview.BeerProgressView

class DyVideoActivity : BaseMvpActivity<VideoContract.IPresenter>(), VideoContract.IView, OnStatusChildClickListener , UpLoadingFileManager.CallBack{


    override fun getLayoutId() = R.layout.activity_dy_video

    override fun registerPresenter() = VideoPresenter::class.java

    private var selectList: List<LocalMedia>? = null
    private var video_group: RelativeLayout? = null
    private var statusLayoutManager: StatusLayoutManager? = null
    private var upLoadingManager : UpLoadingFileManager ? = null
    private var loadingView : View ? = null
    private var beerProgressView : BeerProgressView ? = null
    private var mVideoPlayUrl : String ? = null
    private var mTvProgress : TextView ? = null
    private val mHandler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            val w = msg.what
            val current = msg.arg1
            when(w){
                1 ->{
                    statusLayoutManager!!.showCustomLayout(R.layout.video_status_error)
                }
                2 ->{
                    statusLayoutManager!!.showSuccessLayout()
                    upLoadingSuccess()
                }
                3 -> {
                    beerProgressView!!.visibility = View.VISIBLE
                    mTvProgress!!.visibility = View.VISIBLE
                    beerProgressView!!.beerProgress = current
                    mTvProgress!!.setText("$current%")
                }
            }
        }
    }


    override fun initView() {
        video_group = findViewById(R.id.video_group)
    }

    override fun initData() {
        statusLayoutManager = StatusLayoutManager.Builder(video_group!!).setOnStatusChildClickListener(this).build()
        statusLayoutManager!!.showCustomLayout(R.layout.video_status_add, R.id.video_add_viewGroup)
        loadingView = LayoutInflater.from(this).inflate(R.layout.video_status_loading,null)
        beerProgressView = loadingView!!.findViewById(R.id.beerProgressView)
        mTvProgress = loadingView!!.findViewById<TextView>(R.id.mTvProgress)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == Activity.RESULT_OK) {
                if (upLoadingManager == null){
                    upLoadingManager = UpLoadingFileManager()
                }
                when (requestCode) {
                    PictureConfig.CHOOSE_REQUEST -> {
                        selectList = PictureSelector.obtainMultipleResult(data)
                        if (selectList!!.size > 0) {
                            upLoadingManager!!.UpLoadingFileMp4(selectList!!.get(0).path,this)
                        }
                    }
                }
            }
        } else {
            statusLayoutManager!!.showCustomLayout(R.layout.video_status_add, R.id.video_add_viewGroup)
        }

    }


    override fun onEmptyChildClick(view: View) {

    }

    override fun onErrorChildClick(view: View) {

    }

    override fun onCustomerChildClick(view: View) {
        if (view.id == R.id.video_add_viewGroup) {
            statusLayoutManager!!.showCustomLayout(loadingView!!)
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofVideo())
                    .maxSelectNum(1)
                    .minSelectNum(1)
                    .imageSpanCount(6)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .previewImage(true)
                    .isCamera(true)
                    .compress(true)
                    .forResult(PictureConfig.CHOOSE_REQUEST)
        }
    }

    override fun onProgressBar(p: Long) {
        sendMessage(3, p)
    }

    override fun onError(msg: String) {
        sendMessage(1, 0)
    }

    override fun onSuccess(mList: MutableList<String>) {
        mVideoPlayUrl = mList.get(0)
        sendMessage(2, 100)
    }

    fun sendMessage(what: Int, current: Long) {
        val message = Message.obtain()
        message.what = what
        message.arg1 = current.toInt()
        mHandler.sendMessage(message)
    }

    private fun upLoadingSuccess() {
        mVideoPlayUrl?.let {
            custom_video.setUp(mVideoPlayUrl, "", Jzvd.CURRENT_STATE_NORMAL)
        }
    }
}
