package com.flb.sample.suspend;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;

public class SuspendActivity extends BaseActivity {

    private NestedScrollView sv;
    private RelativeLayout linear_top;


    @Override
    public int getContentView() {
        return R.layout.activity_suspend;
    }

    @Override
    public void initView() {
        initToolbar("悬浮",true);
        sv = findViewById(R.id.sv);

    }
    @Override
    public void initData() {
        linear_top = findViewById(R.id.linear_top);
        sv.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int height = dip2px(this, 65);

            if (scrollY >= height) {
                linear_top.setVisibility(View.VISIBLE);
            } else {
                linear_top.setVisibility(View.GONE);
            }
        });
    }

    public  int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
