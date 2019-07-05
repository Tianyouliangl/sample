package com.flb.sample.fzw.douyin;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.flb.sample.fzw.R;

import cn.jzvd.JZDataSource;
import cn.jzvd.JZMediaInterface;
import cn.jzvd.JZUtils;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

/**
 * author : 冯张伟
 * date : 2019/4/10
 */
public class MyVideo extends JzvdStd {

    public MyVideo(Context context) {
        super(context);
    }

    public MyVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getLayoutId() {
        return R.layout.jz_layout_std;
    }

    @Override
    public void init(Context context) {
        super.init(context);
        findViewById(R.id.layout_top).setVisibility(GONE);
        findViewById(R.id.layout_bottom).setVisibility(GONE);
    }



    @Override
    public void setUp(JZDataSource jzDataSource, int screen, JZMediaInterface jzMediaInterface) {
        super.setUp(jzDataSource, screen, jzMediaInterface);
    }

    @Override
    public void setUp(String url, String title, int screen) {
        super.setUp(url, title, screen);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int i = v.getId();
        if (i == cn.jzvd.R.id.surface_container) {
            bottomContainer.setVisibility(View.GONE);
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i(TAG, "onClick start [" + this.hashCode() + "] ");
                    if (jzDataSource == null || jzDataSource.urlsMap.isEmpty() || jzDataSource.getCurrentUrl() == null) {
                        Toast.makeText(getContext(), getResources().getString(cn.jzvd.R.string.no_url), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                    if (currentState == CURRENT_STATE_NORMAL) {
                        if (!jzDataSource.getCurrentUrl().toString().startsWith("file") && !
                                jzDataSource.getCurrentUrl().toString().startsWith("/") &&
                                !JZUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {//这个可以放到std中
                            showWifiDialog();
                            return true;
                        }
                        startVideo();
                    } else if (currentState == CURRENT_STATE_PLAYING) {
                        Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
                        mediaInterface.pause();
                        currentState = CURRENT_STATE_PAUSE;
                    } else if (currentState == CURRENT_STATE_PAUSE) {
                        mediaInterface.start();
                        currentState = CURRENT_STATE_PLAYING;
                    } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
                        startVideo();
                    }
                    break;
            }

        }
        return super.onTouch(v, event);
    }
}
