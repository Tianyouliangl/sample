package com.flb.sample.dynamic

import android.app.Activity
import android.content.Intent
import android.view.View
import android.widget.FrameLayout
import com.flb.sample.R
import com.flb.sample.contract.VideoContract
import com.flb.sample.presenter.VideoPresenter
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.senyint.hrapp.common.act.BaseMvpActivity
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager

class DyVideoActivity : BaseMvpActivity<VideoContract.IPresenter>(), VideoContract.IView, OnStatusChildClickListener {

    override fun getLayoutId() = R.layout.activity_dy_video

    override fun registerPresenter() = VideoPresenter::class.java

    private var selectList: List<LocalMedia>? = null
    private var video_group: FrameLayout? = null
    private var statusLayoutManager: StatusLayoutManager? = null


    override fun initView() {
        video_group = findViewById(R.id.video_group)
    }

    override fun initData() {
        statusLayoutManager = StatusLayoutManager.Builder(video_group!!).setOnStatusChildClickListener(this).build()
        statusLayoutManager!!.showCustomLayout(R.layout.video_status_add, R.id.video_add_viewGroup)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (resultCode == Activity.RESULT_OK) {
                when (requestCode) {
                    PictureConfig.CHOOSE_REQUEST -> {
                        selectList = PictureSelector.obtainMultipleResult(data)
                        if (selectList!!.size > 0) {
                            statusLayoutManager!!.showCustomLayout(R.layout.video_status_uoloading)
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
            statusLayoutManager!!.showCustomLayout(R.layout.video_status_loading)
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
}
