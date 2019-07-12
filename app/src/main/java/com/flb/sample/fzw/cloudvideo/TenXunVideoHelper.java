package com.flb.sample.fzw.cloudvideo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flb.sample.fzw.R;
import com.flb.sample.fzw.common.Constant;
import com.flb.sample.fzw.widgets.TRTVideoRelativeLayout;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.cos.xml.utils.StringUtils;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * author : fengzhangwei
 * date : 2019/7/10
 * onEnterRoom   创建/加入房间成功  关闭等待页面
 * onUserEnter    有新用户进入房间 (切换到接听页面)   对方必须推流才能监测到
 * onUserExit    有用户离开房间,进行页面提示
 * onUserVideoAvailable    远程页面变化(可以理解为对方离开(false)重新进入(true))
 */
public class TenXunVideoHelper implements View.OnClickListener {

    private final String TAG = "txVideo";
    private final int sdkAppId = Constant.Video.SdkAppId;
    private final Context mContext;
    private final int FILL = TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FILL;
    private List<String> videoUserList;  // 房间人数
    private final TenXunImCallBack mImCallBack;
    private final TRTCCloudDef.TRTCParams mVideoParams;
    private TRTCCloudListenerImpl mTrTcListener;
    private String mUserId;
    private String mUserSign;
    private String mNewUserId;
    private int mRoomId;
    private View mView;
    private TXCloudVideoView tvv_big;
    private TXCloudVideoView tvv_small;
    private Boolean CAMERA = true;  // true：前置摄像头；false：后置摄像头。
    private Boolean TORCH = true;   // true：开启；false：关闭。 闪光灯
    private Boolean MEBig = true;   // 大画面是否是自己
    private TRTCCloud trtcCloud;
    private ImageView iv_video_hang_up; // 挂断

    public interface TenXunImCallBack {
        void onError(String msg, int code);

        void onPermissionError(String msg);

        void onFinishRoom();

        void onJoinSuccess(String roomId);

        void onNewUserEnterRoom(String user, String roomId);

        void onUserExit();

        void onUserAnewJoin();
    }

    public TenXunVideoHelper(Context context, TenXunImCallBack back) {
        this.mContext = context;
        this.mImCallBack = back;
        mTrTcListener = new TRTCCloudListenerImpl();
        mVideoParams = new TRTCCloudDef.TRTCParams();
    }

    /**
     * @param userId   用户id
     * @param userSign 签名
     * @param roomId   房间号
     */
    public void initData(String userId, String userSign, int roomId) {
        this.mUserId = userId;
        this.mUserSign = userSign;
        this.mRoomId = roomId;
        requestPermissions();
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions((Activity) mContext);
        rxPermission.request(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (!aBoolean) {
                    if (mImCallBack != null) {
                        mImCallBack.onPermissionError("请授予相关的权限.");
                    }
                } else {
                    JoinRoom();
                }
            }
        });
    }

    public View initView(ViewGroup root, boolean attachToRoot) {
        if (mView == null) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.layout_tenxun_video, root, attachToRoot);
            TRTVideoRelativeLayout group = mView.findViewById(R.id.trt_video_group);
            tvv_big = group.getBigView();
            tvv_small = group.getSmallView();
            iv_video_hang_up = mView.findViewById(R.id.iv_video_hang_up);
            iv_video_hang_up.setOnClickListener(this);
        }
        return mView;
    }

    private synchronized void JoinRoom() {
        mVideoParams.sdkAppId = sdkAppId;
        mVideoParams.userSig = mUserSign;
        mVideoParams.userId = mUserId;
        mVideoParams.roomId = mRoomId;
        trtcCloud = TRTCCloud.sharedInstance(mContext);
        trtcCloud.setListener(mTrTcListener);
        trtcCloud.enterRoom(mVideoParams, TRTCCloudDef.TRTC_APP_SCENE_VIDEOCALL);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_video_hang_up){
            exitRoom();
        }
    }

    private synchronized void changUI() {
        if (!StringUtils.isEmpty(mNewUserId)) {
            stopRemoteView(mNewUserId);
            stopLocalPreview();
            if (MEBig) {
                startRemoteView(mNewUserId, tvv_small);
                startLocalPreview(CAMERA, tvv_big);
            }
            if (!MEBig) {
                startRemoteView(mNewUserId, tvv_big);
                startLocalPreview(CAMERA, tvv_small);
            }
            setRemoteViewFillMode(mNewUserId, FILL);
            setLocalViewFillMode(FILL);
        } else {
            if (mImCallBack != null) {
                mImCallBack.onError("未有用户加入", 0);
            }
        }

    }

    /**
     * 退出当前房间
     */
    public synchronized void exitRoom() {
        if (trtcCloud != null) {
            trtcCloud.exitRoom();
        }
    }

    public synchronized void stopLocal(){
        if (trtcCloud != null) {
            trtcCloud.stopLocalPreview();
        }
    }

    /**
     * 开启本地视频的预览画面。
     *
     * @param frontCamera 摄像头 前/后
     * @param view        承载画面View
     */
    private void startLocalPreview(boolean frontCamera, TXCloudVideoView view) {
        if (trtcCloud != null) {
            trtcCloud.startLocalPreview(frontCamera, view);
        }
    }

    /**
     * 开启本地音频的采集和上行
     */
    private void startLocalAudio() {
        if (trtcCloud != null) {
            trtcCloud.startLocalAudio();
        }
    }

    /**
     * 设置音频路由
     * 音频路由，即声音由哪里输出（扬声器、听筒）。
     *
     * @param route
     */
    private void setAudioRoute(int route) {
        if (trtcCloud != null) {
            trtcCloud.setAudioRoute(route);
        }
    }

    /**
     * 设置本地图像的渲染模式
     *
     * @param mode
     */
    private void setLocalViewFillMode(int mode) {
        if (trtcCloud != null) {
            trtcCloud.setLocalViewFillMode(mode);
        }
    }

    /**
     * 关闭本地音频的采集和上行。
     */
    private void stopLocalAudio() {
        if (trtcCloud != null) {
            trtcCloud.stopLocalAudio();
        }
    }

    /**
     * 停止本地视频采集及预览
     */
    private void stopLocalPreview() {
        if (trtcCloud != null) {
            trtcCloud.stopLocalPreview();
        }
    }

    /**
     * 开始显示远端视频画面。
     *
     * @param userId
     * @param view
     */
    private void startRemoteView(String userId, TXCloudVideoView view) {
        if (trtcCloud != null) {
            trtcCloud.startRemoteView(userId, view);
        }
    }

    /**
     * 设置远端图像的渲染模式
     *
     * @param userId
     * @param mode
     */
    private void setRemoteViewFillMode(String userId, int mode) {
        if (trtcCloud != null) {
            trtcCloud.setRemoteViewFillMode(userId, mode);
        }
    }

    /**
     * 停止显示远端视频画面。
     *
     * @param userId
     */
    private void stopRemoteView(String userId) {
        if (trtcCloud != null) {
            trtcCloud.stopRemoteView(userId);
        }
    }


    /**
     * 接听
     */
    public void Answer() {
        startLocalPreview(CAMERA, tvv_big);
        setLocalViewFillMode(FILL);
    }

    class TRTCCloudListenerImpl extends TRTCCloudListener {

        /**
         * 错误通知意味着 SDK 不能继续运行了
         *
         * @param i
         * @param s
         * @param bundle
         */
        @Override
        public void onError(int i, String s, Bundle bundle) {
            super.onError(i, s, bundle);
            Log.i(TAG, "----onError-----" + s + "------code-----" + i);
            if (mImCallBack != null) {
                mImCallBack.onError(s, i);
            }
        }

        /**
         * 警告回调：用于告知您一些非严重性问题，比如出现了卡顿或者可恢复的解码失败。
         *
         * @param i
         * @param s
         * @param bundle
         */
        @Override
        public void onWarning(int i, String s, Bundle bundle) {
            super.onWarning(i, s, bundle);
            Log.i(TAG, "----onWarning-----" + s + "------code-----" + i);
        }

        /**
         * SDK 跟服务器的连接断开。
         */
        @Override
        public void onConnectionLost() {
            super.onConnectionLost();
            Log.i(TAG, "----onConnectionLost()-----SDK 跟服务器的连接断开。");
        }

        /**
         * SDK 尝试重新连接到服务器。
         */
        @Override
        public void onTryToReconnect() {
            super.onTryToReconnect();
            Log.i(TAG, "----onTryToReconnect()-----SDK 尝试重新连接到服务器。");
        }

        /**
         * SDK 跟服务器的连接恢复。
         */
        @Override
        public void onConnectionRecovery() {
            super.onConnectionRecovery();
            Log.i(TAG, "----onConnectionRecovery()-----SDK 跟服务器的连接恢复。");
        }

        /**
         * 摄像头准备就绪。
         */
        @Override
        public void onCameraDidReady() {
            super.onCameraDidReady();
            Log.i(TAG, "----onCameraDidReady()-----摄像头准备就绪。");
        }

        /**
         * 麦克风准备就绪。
         */
        @Override
        public void onMicDidReady() {
            super.onMicDidReady();
            Log.i(TAG, "----onCameraDidReady()-----麦克风准备就绪。");
        }

        /**
         * 音频路由发生变化，音频路由即声音由哪里输出（扬声器、听筒）。
         *
         * @param i
         * @param i1
         */
        @Override
        public void onAudioRouteChanged(int i, int i1) {
            super.onAudioRouteChanged(i, i1);
            Log.i(TAG, "----onAudioRouteChanged()-----音频路由发生变化。");
        }

        /**
         * 自定义消息丢失回调
         * @param s
         * @param i
         * @param i1
         * @param i2
         */
        @Override
        public void onMissCustomCmdMsg(String s, int i, int i1, int i2) {
            super.onMissCustomCmdMsg(s, i, i1, i2);
            Log.i(TAG, "----onMissCustomCmdMsg()-----自定义消息丢失。");
        }

        /**
         * 收到自定义消息回调
         *
         * @param s
         * @param i
         * @param i1
         * @param bytes
         */
        @Override
        public void onRecvCustomCmdMsg(String s, int i, int i1, byte[] bytes) {
            super.onRecvCustomCmdMsg(s, i, i1, bytes);
            Log.i(TAG, "----onRecvCustomCmdMsg()-----收到自定义消息。::::::::" + s);
        }

        /**
         * 收到 SEI 消息的回调
         *
         * @param s
         * @param bytes
         */
        @Override
        public void onRecvSEIMsg(String s, byte[] bytes) {
            super.onRecvSEIMsg(s, bytes);
            Log.i(TAG, "----onRecvSEIMsg()-----收到 SEI 消息的::::::::" + s);
        }

        /**
         * 加入房间
         *
         * @param l
         */
        @Override
        public void onEnterRoom(long l) {
            super.onEnterRoom(l);
            videoUserList = new ArrayList<>();
            videoUserList.add("me");
            Log.i(TAG, "----onEnterRoom()-----加入房间");
            if (mImCallBack != null) {
                mImCallBack.onJoinSuccess(String.valueOf(mRoomId));
            }
        }

        /**
         * 有新的音视频用户加入房间
         *
         * @param s
         */
        @Override
        public void onUserEnter(String s) {
            super.onUserEnter(s);
            if (videoUserList.size() < 2) {
                videoUserList.add(s);
                mNewUserId = s;
                if (mImCallBack != null) {
                    mImCallBack.onNewUserEnterRoom(s, String.valueOf(mRoomId));
                }
                if (tvv_small.getVisibility() == View.GONE) {
                    tvv_small.setVisibility(View.VISIBLE);
                }
            }
            Log.i(TAG, "----onUserEnter()-----有新的音视频用户加入房间");
        }

        /**
         * 有用户从当前房间中离开
         *
         * @param s
         * @param i
         */
        @Override
        public void onUserExit(String s, int i) {
            super.onUserExit(s, i);
            if (mNewUserId.equals(s)) {
                if (tvv_small != null && tvv_big != null) {
                    stopRemoteView(s);
                    stopLocalPreview();
                    tvv_small.setVisibility(View.GONE);
                    tvv_big.setVisibility(View.GONE);
                }
            }
            if (mImCallBack != null) {
                mImCallBack.onUserExit();
            }
            Log.i(TAG, "----onUserExit()-----有用户从当前房间中离开:::::" + s);
        }

        /**
         * userid 对应的远端主路（即摄像头）画面的状态通知。
         *
         * @param s
         * @param b
         */
        @Override
        public void onUserVideoAvailable(String s, boolean b) {
            super.onUserVideoAvailable(s, b);
            Log.i(TAG, "----onUserVideoAvailable()-----对应的远端主路（即摄像头）画面的状态通知:::::" + s + "-----b-----" + b);
            if (b) {
                if (mNewUserId.equals(s)) {
                    if (tvv_small.getVisibility() == View.GONE) {
                        tvv_small.setVisibility(View.VISIBLE);
                    }
                    if (tvv_big.getVisibility() == View.GONE) {
                        tvv_big.setVisibility(View.VISIBLE);
                    }
                    if (mImCallBack != null) {
                        mImCallBack.onUserAnewJoin();
                    }
                    MEBig = false;
                    changUI();
                }
            }
        }

        /**
         * userid 对应的远端辅路（屏幕分享等）画面的状态通知。
         *
         * @param s
         * @param b
         */
        @Override
        public void onUserSubStreamAvailable(String s, boolean b) {
            super.onUserSubStreamAvailable(s, b);
        }

        /**
         * userid 对应的远端声音的状态通知。
         *
         * @param s
         * @param b
         */
        @Override
        public void onUserAudioAvailable(String s, boolean b) {
            super.onUserAudioAvailable(s, b);
            Log.i(TAG, "----onUserAudioAvailable()-----对应的远端声音的状态通知:::::" + s + "----b-----" + b);
        }

        /**
         * 用于提示音量大小的回调,包括每个 userId 的音量和远端总音量。
         *
         * @param arrayList
         * @param i
         */
        @Override
        public void onUserVoiceVolume(ArrayList<TRTCCloudDef.TRTCVolumeInfo> arrayList, int i) {
            super.onUserVoiceVolume(arrayList, i);
        }

        /**
         * 离开房间
         *
         * @param i
         */
        @Override
        public void onExitRoom(int i) {
            super.onExitRoom(i);
            Log.i(TAG, "----onExitRoom()-----离开房间");
            if (mImCallBack != null) {
                mImCallBack.onFinishRoom();
            }
        }

        /**
         * 首帧视频画面到达，界面此时可以结束 Loading，并开始显示视频画面。
         *
         * @param s
         * @param i
         * @param i1
         * @param i2
         */
        @Override
        public void onFirstVideoFrame(String s, int i, int i1, int i2) {
            super.onFirstVideoFrame(s, i, i1, i2);
            Log.i(TAG, "----onFirstVideoFrame()-----首帧视频画面到达");
        }

        /**
         * 首帧音频数据到达。
         *
         * @param s
         */
        @Override
        public void onFirstAudioFrame(String s) {
            super.onFirstAudioFrame(s);
            Log.i(TAG, "----onFirstAudioFrame()-----首帧音频数据到达");
        }
    }
}
