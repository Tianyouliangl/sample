package com.flb.sample.fzw;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.cos.xml.utils.StringUtils;

/**
 * author : 冯张伟
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.
                FLAG_FULLSCREEN, WindowManager.LayoutParams.
                FLAG_FULLSCREEN);
        setContentView(getContentView());
        initView();
        initData();
    }

    public abstract int getContentView();

    public abstract void initView();

    public abstract void initData();


    protected void goActivity(Class<?> cls) {
        goActivity(cls, null);
    }

    protected void goActivity(Class<?> cls, @org.jetbrains.annotations.Nullable Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    protected String getIntentString(String key) {
        if (StringUtils.isEmpty(key)) {
            return "null";
        } else {
            String extra = getIntent().getStringExtra(key);
            return extra;
        }
    }

    protected void initToolbar(String title, Boolean ivBack) {
        ImageView back = findViewById(R.id.iv_back);
        back.setVisibility(ivBack == true ? View.VISIBLE : View.GONE);
        TextView title_bar = findViewById(R.id.tv_title_bar);
        if (!StringUtils.isEmpty(title)) {
            title_bar.setText(title);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void initToolbar(String title, String tvRight, Boolean ivBack, Boolean ivUp, Boolean tvConfirm, View.OnClickListener onClickListener) {
        ImageView back = findViewById(R.id.iv_back);
        ImageView up = findViewById(R.id.iv_up);
        TextView tv_Confirm = findViewById(R.id.tv_right_confirm);
        back.setVisibility(ivBack == true ? View.VISIBLE : View.GONE);
        up.setVisibility(ivUp == true ? View.VISIBLE : View.GONE);
        tv_Confirm.setVisibility(tvConfirm == true ? View.VISIBLE : View.GONE);
        TextView title_bar = findViewById(R.id.tv_title_bar);
        if (!StringUtils.isEmpty(title)) {
            title_bar.setText(title);
        }
        if (!StringUtils.isEmpty(tvRight)) {
            tv_Confirm.setText(tvRight);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_Confirm.setOnClickListener(onClickListener);
    }

    protected void setBarRightMsg(String msg) {
        TextView tv_right_confirm = findViewById(R.id.tv_right_confirm);
        if (!StringUtils.isEmpty(msg)) {
            tv_right_confirm.setText(msg);
        }
    }

    protected void setBarRightMsg(String msg, View.OnClickListener onClickListener) {
        TextView tv_right_confirm = findViewById(R.id.tv_right_confirm);
        tv_right_confirm.setVisibility(View.VISIBLE);
        if (!StringUtils.isEmpty(msg)) {
            tv_right_confirm.setText(msg);
        }
        tv_right_confirm.setOnClickListener(onClickListener);
    }
}
