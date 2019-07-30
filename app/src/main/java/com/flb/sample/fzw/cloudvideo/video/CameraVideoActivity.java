package com.flb.sample.fzw.cloudvideo.video;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.flb.sample.fzw.R;
import com.flb.sample.fzw.cloudvideo.capture.AvcEncoder;
import com.flb.sample.fzw.cloudvideo.capture.RtpSenderWrapper;

import java.io.IOException;

public class CameraVideoActivity extends Activity implements SurfaceHolder.Callback, Camera.PreviewCallback {

    private static final String TAG = "Camera";

    AvcEncoder avcCodec;
    public Camera m_camera;
    SurfaceView   m_prevewview;
    SurfaceHolder m_surfaceHolder;
    //屏幕分辨率，每个机型不一样，机器连上adb后输入wm size可获取
    int width = 800;
    int height = 480;
    int framerate = 15;//每秒帧率
    int bitrate = 125000;//编码比特率，
    private RtpSenderWrapper mRtpSenderWrapper;

    byte[] h264 = new byte[width*height*3];

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 不知道什么用
//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectAll()   // or .detectAll() for all detectable problems
//                .penaltyLog()
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .penaltyDeath()
//                .build());
        setContentView(R.layout.activity_camera_video);
        initView();
        initData();
    }


    public void initView() {
        m_prevewview = findViewById(R.id.SurfaceViewPlay);
        m_surfaceHolder = m_prevewview.getHolder(); // 绑定SurfaceView，取得SurfaceHolder对象
        m_surfaceHolder.setFixedSize(width, height); // 预览大小設置
        m_surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        m_surfaceHolder.addCallback(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void initData() {
//创建rtp并填写需要发送数据流的地址，直播中需要动态获取客户主动请求的地址
        mRtpSenderWrapper = new RtpSenderWrapper("192.168.253.15", 5004, false);
        avcCodec = new AvcEncoder(width,height,framerate,bitrate);
    }

    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.v(TAG, "MainActivity+surfaceCreated");
        m_camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        try {
            m_camera.setPreviewDisplay(m_surfaceHolder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        m_camera.setDisplayOrientation(90);
        Camera.Size previewSize = m_camera.getParameters().getPreviewSize();
        final int width = previewSize.width;
        final int height = previewSize.height;
        System.out.println(width + ":" + height);
        m_camera.setPreviewCallback(this);
        m_camera.startPreview();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.v(TAG, "MainActivity+surfaceDestroyed");
        m_camera.setPreviewCallback(null);  //！！这个必须在前，不然退出出错
        m_camera.release();
        m_camera = null;
        avcCodec.close();
    }

    @Override
    public void onPreviewFrame(byte[] bytes, Camera camera) {
        Log.e(TAG, "MainActivity+h264 start");
        int ret = avcCodec.offerEncoder(bytes, h264);
        if(ret > 0){
            //实时发送数据流
            mRtpSenderWrapper.sendAvcPacket(h264, 0, ret, 0);
        }
        Log.e(TAG, "MainActivity+h264 end");
        Log.e(TAG, "-----------------------------------------------------------------------");
    }
}
