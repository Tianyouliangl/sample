package com.flb.sample.fzw.thirdparty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.thirdparty.msgActivity.QQActivity;
import com.flb.sample.fzw.thirdparty.msgActivity.WXActivity;
import com.flb.sample.fzw.thirdparty.msgActivity.ZFBActivity;

public class SelectorActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_qq;
    private Button btn_zfb;
    private Button btn_wx;

    @Override
    public int getContentView() {
        return R.layout.activity_selector;
    }

    @Override
    public void initView() {
        btn_qq = findViewById(R.id.btn_qq);
        btn_zfb = findViewById(R.id.btn_zfb);
        btn_wx = findViewById(R.id.btn_wx);
    }

    @Override
    public void initData() {
        btn_qq.setOnClickListener(this);
        btn_zfb.setOnClickListener(this);
        btn_wx.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_qq){
            goActivity(QQActivity.class);
        }
        if (v.getId() == R.id.btn_zfb){
            goActivity(ZFBActivity.class);
        }
        if (v.getId() == R.id.btn_wx){
            goActivity(WXActivity.class);
        }
    }
}
