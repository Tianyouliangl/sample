package com.flb.sample.fzw.cloudvideo.video;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;

public class CameraVideoActivity extends BaseActivity implements View.OnScrollChangeListener {


    private ScrollView mScrollView;
    private LinearLayout linearTitle;

    @Override
    public int getContentView() {
        return R.layout.activity_camera_video;
    }

    @Override
    public void initView() {
        mScrollView = findViewById(R.id.sv);
        linearTitle =  findViewById(R.id.title);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initData() {
        mScrollView.setOnScrollChangeListener(this);
    }


    @Override
    public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        Log.e("fzw","-----y----" + scrollY);
        if (scrollY > 360){
            linearTitle.setVisibility(View.VISIBLE);
        }else {
            linearTitle.setVisibility(View.GONE);
        }
    }
}
