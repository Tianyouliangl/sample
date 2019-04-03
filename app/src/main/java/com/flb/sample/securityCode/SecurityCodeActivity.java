package com.flb.sample.securityCode;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.widgets.VerificationCodeView;

public class SecurityCodeActivity extends BaseActivity implements View.OnClickListener, VerificationCodeView.OnCodeFinishListener {

    private Button clear;
    private TextView tv_content;

    @Override
    public int getContentView() {
        return R.layout.activity_security_code;
    }

    @Override
    public void initView() {
        VerificationCodeView view_verification = findViewById(R.id.view_verification);
        clear = findViewById(R.id.btn_clear);
        tv_content = findViewById(R.id.tv_content);
        clear.setOnClickListener(this);
        view_verification.setOnCodeFinishListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_clear){
        }
    }

    @Override
    public void onComplete(String content) {
        tv_content.setText(content);
    }
}
