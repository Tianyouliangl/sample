package com.flb.sample.dynamic;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

public class DyVideoActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_check_video;
    private List<LocalMedia> selectList;

    @Override
    public int getContentView() {
        return R.layout.activity_dy_video;
    }

    @Override
    public void initView() {
        btn_check_video = findViewById(R.id.btn_check_video);
        btn_check_video.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_check_video){
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofVideo())
                    .maxSelectNum(2)
                    .minSelectNum(1)
                    .imageSpanCount(6)
                    .selectionMode(PictureConfig.MULTIPLE)
                    .previewImage(true)
                    .isCamera(true)
                    .compress(true)
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:
                    selectList = PictureSelector.obtainMultipleResult(data);
                    for (int i=0;i<selectList.size();i++) {

                    }
                    break;

            }
        }
    }

    public void getSignature(){

    }

}
