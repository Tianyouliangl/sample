package com.flb.sample.fzw.cloudvideo;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.widgets.VideoUtils;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class AVConversationActivity extends BaseActivity implements TenXunVideoHelper.TenXunImCallBack, OnStatusChildClickListener, View.OnClickListener {


    private FrameLayout frame_video;
    private TenXunVideoHelper tenXunVideoHelper;
    private StatusLayoutManager layoutManager;
    private ImageView iv_hang_up;
    private RelativeLayout rl_user_exit;
    private int VIDEO_CODE = 0; // 0 正在加入/创建房间 1  加入/创建房间成功 2等待呼叫 3正在视频  4  对方退出

    @Override
    public int getContentView() {
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_avconversation;
    }

    @Override
    public void initView() {
        frame_video = findViewById(R.id.frame_video);
        rl_user_exit = findViewById(R.id.rl_user_exit);
        iv_hang_up = findViewById(R.id.iv_hang_up);
        showAndHide(true);
    }

    private void showAndHide(boolean b) {
        frame_video.setVisibility(b == true ? View.VISIBLE : View.GONE);
        rl_user_exit.setVisibility(b == true ? View.GONE : View.VISIBLE);
    }

    @Override
    public void initData() {
        layoutManager = new StatusLayoutManager.Builder(frame_video).setOnStatusChildClickListener(this).build();
        layoutManager.showCustomLayout(R.layout.layout_video_loading);
        String userId = VideoUtils.getUserId();
        tenXunVideoHelper = new TenXunVideoHelper(this, this);
        tenXunVideoHelper.initData(userId, VideoUtils.getUserSign(userId), Integer.valueOf(getIntentString("roomId")));
        View view = tenXunVideoHelper.initView(frame_video, false);
        frame_video.addView(view);
        iv_hang_up.setOnClickListener(this);
    }

    @Override
    public void onError(String msg, int code) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onFinishRoom() {
        finish();
    }

    @Override
    public void onJoinSuccess(String roomId) {
        VIDEO_CODE = 1;
        View view = LayoutInflater.from(this).inflate(R.layout.layout_video_loading2, null, false);
        TextView tv_room = view.findViewById(R.id.tv_roomId);
        tv_room.setText(roomId);
        layoutManager.showCustomLayout(view, R.id.iv_loading_up);
    }

    @Override
    public void onNewUserEnterRoom(String userName, String roomId) {
        VIDEO_CODE = 2;
        View view = LayoutInflater.from(this).inflate(R.layout.layout_status_video_answer, null, false);
        TextView roomName = view.findViewById(R.id.tv_room);
        TextView msg = view.findViewById(R.id.tv_msg);
        roomName.setText(roomId);
        msg.setText(userName + "邀请您进行视频通话...");
        layoutManager.showCustomLayout(view, R.id.iv_answer_hang_up, R.id.iv_answer_answer);
    }

    @Override
    public void onUserExit() {
        if (VIDEO_CODE == 2) {
            Toast.makeText(this,"对方已取消",Toast.LENGTH_SHORT).show();
            tenXunVideoHelper.exitRoom();
        } else {
            showAndHide(false);
        }


    }

    @Override
    public void onUserAnewJoin() {
        showAndHide(true);
    }

    @Override
    public void onEmptyChildClick(View view) {

    }

    @Override
    public void onErrorChildClick(View view) {

    }

    @Override
    public void onCustomerChildClick(View view) {
        if (view.getId() == R.id.iv_loading_up) {
            tenXunVideoHelper.exitRoom();
        }
        if (view.getId() == R.id.iv_answer_hang_up) {
            tenXunVideoHelper.exitRoom();
        }
        if (view.getId() == R.id.iv_answer_answer) {
            tenXunVideoHelper.Answer();
            VIDEO_CODE = 3;
            layoutManager.showSuccessLayout();
            showAndHide(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_hang_up) {
            tenXunVideoHelper.exitRoom();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            tenXunVideoHelper.exitRoom();
        }
        if (keyCode == KeyEvent.KEYCODE_HOME){
            tenXunVideoHelper.stopLocal();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tenXunVideoHelper.exitRoom();
    }
}
