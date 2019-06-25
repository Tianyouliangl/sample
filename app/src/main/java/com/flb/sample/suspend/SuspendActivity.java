package com.flb.sample.suspend;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.flb.sample.R;

public class SuspendActivity extends AppCompatActivity implements View.OnClickListener {

    private NestedScrollView sv;
    private RelativeLayout linear_top;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suspend);
        initView();
    }

    private void initView() {
        sv = findViewById(R.id.sv);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back){
            finish();
        }
    }
}
