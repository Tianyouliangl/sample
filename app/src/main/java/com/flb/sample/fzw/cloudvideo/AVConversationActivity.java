package com.flb.sample.fzw.cloudvideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.common.Constant;
import com.flb.sample.fzw.widgets.VideoUtils;
import com.tencent.liteav.TXLiteAVCode;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import com.tls.tls_sigature.tls_sigature;

import java.lang.ref.WeakReference;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class AVConversationActivity extends BaseActivity implements View.OnClickListener {

    private String TAG = "Video";
    private long firstTime;// 记录点击返回时第一次的时间毫秒值
    private TRTCCloudListenerImpl trtcListener;
    private TRTCCloud trtcCloud;
    private TXCloudVideoView view_group;
    private TXCloudVideoView view_child;
    private ImageView iv_hangUp;
    private RelativeLayout video_rl;
    private StatusLayoutManager statusLayoutManager;
    private Boolean isChild = true;    // 画面切换
    private Boolean isTorch = false;  // 闪光灯
    private Boolean isAudio = true;  // 声音
    private Boolean isShowGroupUI = true; // 画面按钮
    private String mUserId;
    private ImageView iv_torch;
    private ImageView iv_audio;
    private ImageView iv_camera;

    @Override
    public int getContentView() {
        return R.layout.activity_avconversation;
    }

    @Override
    public void initView() {
        view_group = findViewById(R.id.fl_group);
        view_child = findViewById(R.id.fl_child);
        iv_hangUp = findViewById(R.id.iv_hangUp);
        video_rl = findViewById(R.id.video_rl);
        iv_torch = findViewById(R.id.iv_torch);
        iv_audio = findViewById(R.id.iv_audio);
        iv_camera = findViewById(R.id.iv_camera);
        statusLayoutManager = new StatusLayoutManager.Builder(video_rl).build();
        // 加载中
        statusLayoutManager.showCustomLayout(R.layout.status_video_loading);
    }

    @Override
    public void initData() {
        TRTCCloudDef.TRTCParams params = new TRTCCloudDef.TRTCParams();
        params.sdkAppId = Constant.Video.SdkAppId;
        params.userId = VideoUtils.getUserId();
        params.userSig = VideoUtils.getUserSign(params.userId);
        params.roomId = Integer.valueOf(getIntentString("roomId"));
        trtcListener = new TRTCCloudListenerImpl();
        trtcCloud = TRTCCloud.sharedInstance(this);
        trtcCloud.setListener(trtcListener);
        trtcCloud.enterRoom(params, 1);
        iv_hangUp.setOnClickListener(this);
        view_child.setOnClickListener(this);
        iv_torch.setOnClickListener(this);
        iv_audio.setOnClickListener(this);
        iv_camera.setOnClickListener(this);
        view_group.setOnClickListener(this);
        Log.d(TAG, "sdk RoomId ---" + params.roomId + "---UserId----" + params.userId + "---sdkAppId---" + params.sdkAppId + "----sign-----" + params.userSig);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_hangUp) {
            trtcCloud.exitRoom();
            finish();
        }
        if (v.getId() == R.id.fl_child) {
            changeVideoView();
        }
        if (v.getId() == R.id.iv_torch) {
            if (isTorch) {
                trtcCloud.enableTorch(false);
                iv_torch.setImageResource(R.mipmap.torch_n);
                isTorch = false;
                return;
            }
            if (!isTorch) {
                trtcCloud.enableTorch(true);
                iv_torch.setImageResource(R.mipmap.torch_y);
                isTorch = true;
                return;
            }
        }
        if (v.getId() == R.id.iv_audio) {
            if (isAudio) {
                trtcCloud.stopLocalAudio();
                iv_audio.setImageResource(R.mipmap.audio_n);
                isAudio = false;
                return;
            }
            if (!isAudio) {
                trtcCloud.startLocalAudio();
                iv_audio.setImageResource(R.mipmap.audio_y);
                isAudio = true;
                return;
            }
        }
        if (v.getId() == R.id.iv_camera) {
            trtcCloud.switchCamera();
        }

        if (v.getId() == R.id.fl_group) {
            Boolean aBoolean = changeGroupUI(600);
            if (aBoolean) {
                if (isShowGroupUI) {
                    hideAndShow(false);
                    isShowGroupUI = false;
                    return;
                }
                if (!isShowGroupUI) {
                    hideAndShow(true);
                    isShowGroupUI = true;
                    return;
                }
            }
        }
    }


    void hideAndShow(Boolean b) {
        iv_torch.setVisibility(b == true ? View.VISIBLE : View.GONE);
        iv_audio.setVisibility(b == true ? View.VISIBLE : View.GONE);
        iv_hangUp.setVisibility(b == true ? View.VISIBLE : View.GONE);
        iv_camera.setVisibility(b == true ? View.VISIBLE : View.GONE);
    }


    void changeVideoView() {
        new Thread(){
            @Override
            public void run() {
                trtcCloud.stopLocalPreview();
                trtcCloud.stopRemoteView(mUserId);
                if (isChild) {
                    startLocalPreview(true, view_child);
                    startRemotePreview(mUserId, view_group);
                    isChild = false;
                    return;
                }
                if (!isChild) {
                    startLocalPreview(true, view_group);
                    startRemotePreview(mUserId, view_child);
                    isChild = true;
                    return;
                }
            }
        }.start();

    }

    class TRTCCloudListenerImpl extends TRTCCloudListener {

        // 错误通知是要监听的，错误通知意味着 SDK 不能继续运行了
        @Override
        public void onError(int errCode, String errMsg, Bundle extraInfo) {
            Log.d(TAG, "sdk callback onError --- " + errMsg + "-----code-----" + errCode);
            Toast.makeText(AVConversationActivity.this, "onError: " + errMsg + "[" + errCode + "]", Toast.LENGTH_SHORT).show();
            if (errCode == TXLiteAVCode.ERR_ROOM_ENTER_FAIL) {
                finish();
            }
        }

        @Override
        public void onEnterRoom(long l) {
            Log.d(TAG, "sdk callback Success ---  创建/加入房间成功");
            statusLayoutManager.showCustomLayout(R.layout.status_video_user_loading);
            startLocalPreview(true, view_group);
            trtcCloud.startLocalAudio();
        }

        // 有新用户加入了房间
        @Override
        public void onUserEnter(String userId) {
            mUserId = userId;
            Log.d(TAG, "sdk onUserEnter --- " + "---用户加入---");
            statusLayoutManager.showSuccessLayout();
            startRemotePreview(userId, view_child);
        }

        @Override
        public void onUserAudioAvailable(String userId, boolean b) {
            Log.d(TAG, "sdk onUserEnter --- ");
        }

        @Override
        public void onExitRoom(int i) {
            Log.d(TAG, "sdk onExitRoom --- ");
        }
    }

    /**
     * 打开远程画面
     *
     * @param mUserId
     * @param view
     */

    void startRemotePreview(String mUserId, TXCloudVideoView view) {
        trtcCloud.setRemoteViewFillMode(mUserId, TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL);
        trtcCloud.startRemoteView(mUserId, view);
    }


    /**
     * 打开本地摄像头预览画面
     */
    void startLocalPreview(boolean frontCamera, TXCloudVideoView localVideoView) {
        trtcCloud.setLocalViewFillMode(TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FIT);
        trtcCloud.startLocalPreview(frontCamera, localVideoView);
    }

    Boolean changeGroupUI(long timeInterval) {
        // 第一次肯定会进入到if判断里面，然后把firstTime重新赋值当前的系统时间
        // 然后点击第二次的时候，当点击间隔时间小于2s，那么退出应用；反之不退出应用
        if (System.currentTimeMillis() - firstTime >= timeInterval) {
            firstTime = System.currentTimeMillis();
            return false;
        } else {
            return true;
        }
    }


    // 销毁 trtcCloud 实例，在不再使用SDK能力时，销毁单例，节省开销3
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁 trtc 实例
        if (trtcCloud != null) {
            trtcCloud.setListener(null);
        }
        trtcCloud = null;
        TRTCCloud.destroySharedInstance();
    }
}
