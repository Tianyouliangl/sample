package com.flb.sample.fzw.cloudvideo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.cloudvideo.video.AVConversationActivity;
import com.tencent.cos.xml.utils.StringUtils;

public class AVActivity extends BaseActivity implements View.OnClickListener {

    private EditText roomId;
    private EditText name;
    private Button start;

    @Override
    public int getContentView() {
        return R.layout.activity_av;
    }

    @Override
    public void initView() {
        initToolbar("腾讯云视频","音视频",true,false,true,this);
        roomId = findViewById(R.id.ed_roomId);
        name = findViewById(R.id.ed_name);
        start = findViewById(R.id.btn_start);
    }

    @Override
    public void initData() {
        initToolbar("音视频",true);
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_start){
            String room = roomId.getText().toString().trim();
            String n = name.getText().toString().trim();
            if (StringUtils.isEmpty(room)){
                Toast.makeText(this,"请输入房间号",Toast.LENGTH_SHORT).show();
                return;
            }

            if (StringUtils.isEmpty(n)){
                Toast.makeText(this,"请输入名字",Toast.LENGTH_SHORT).show();
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("roomId",room);
            bundle.putString("name",n);
            goActivity(AVConversationActivity.class,bundle);
            finish();
        }
    }
}
