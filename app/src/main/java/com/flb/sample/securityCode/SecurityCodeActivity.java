package com.flb.sample.securityCode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.widgets.VerificationCodeView;

public class SecurityCodeActivity extends BaseActivity implements View.OnClickListener, VerificationCodeView.input {

    private VerificationCodeView view_verification;
    private Button clear;
    private TextView tv_content;

    @Override
    public int getContentView() {
        return R.layout.activity_security_code;
    }

    @Override
    public void initView() {
        view_verification = findViewById(R.id.view_verification);
        clear = findViewById(R.id.btn_clear);
        tv_content = findViewById(R.id.tv_content);
        clear.setOnClickListener(this);
        view_verification.setOnInputFinishListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_clear){
            view_verification.clear();
        }
    }

    @Override
    public void onInputFinish() {
        Log.i("AAA","-----触发输入完成----");
    }

    @Override
    public void onInputChange() {
        String content = view_verification.getContent();
        tv_content.setText(content);
    }
}
