package com.flb.sample.alarm;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.model.AlarmClockBean;

import java.io.IOException;

public class ClockRemindActivity extends BaseActivity {

    private MediaPlayer mMediaPlayer;

    @Override
    public int getContentView() {
        return R.layout.activity_clock_remind;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        AlarmClockBean bean = (AlarmClockBean) getIntent().getSerializableExtra("bean");
        startAlarm();
    }

    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_ALARM);
    }

    private void startAlarm() {
        mMediaPlayer = MediaPlayer.create(this, getSystemDefultRingtoneUri());
        mMediaPlayer.setLooping(true);
        try {
            mMediaPlayer.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    @Override
    protected void onDestroy() {
        mMediaPlayer.stop();
        mMediaPlayer.reset();
        super.onDestroy();
    }
}
