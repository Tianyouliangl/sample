package com.flb.sample.alarm;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.common.DBHelper;
import com.flb.sample.model.AlarmClockBean;

public class ClockRemindActivity extends BaseActivity implements View.OnClickListener {

    private Vibrator vibrator;
    private AlarmClockBean bean;
    private Button btn_close;
    private Button later_on;
    private TextView time;
    private MediaPlayer player;

    @Override
    public int getContentView() {
        return R.layout.activity_clock_remind;
    }

    @Override
    public void initView() {
        time = findViewById(R.id.tv_clock_time);
        btn_close = findViewById(R.id.btn_close);
        later_on = findViewById(R.id.btn_later_on);
    }

    @Override
    public void initData() {
        btn_close.setOnClickListener(this);
        later_on.setOnClickListener(this);
        bean = (AlarmClockBean) getIntent().getSerializableExtra("bean");
        startAlarm();
    }


    private void startAlarm() {
        if (bean != null) {
            time.setText(bean.getTime());
            if (bean.getIsShake().equals("1")) {
                vibrator = (Vibrator) this.getSystemService(this.VIBRATOR_SERVICE);
                long[] patter = {1000, 500, 1000, 500};
                vibrator.vibrate(patter, 0);
            }
        }
        player = new MediaPlayer();
        try {
            player.setDataSource(this, getSystemDefultRingtoneUri());
        } catch (Exception e) {
            e.printStackTrace();
        }
        final AudioManager audioManager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
        if (audioManager.getStreamVolume(AudioManager.STREAM_ALARM) != 0) {
            player.setAudioStreamType(AudioManager.STREAM_ALARM);
            player.setLooping(true);
            try {
                player.prepare();
            } catch (Exception e) {
                 finish();
                e.printStackTrace();
            }
            player.start();
        }
    }

    private Uri getSystemDefultRingtoneUri() {
        return RingtoneManager.getActualDefaultRingtoneUri(this,
                RingtoneManager.TYPE_ALARM);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_close){
            updateData();
            finish();
        }
        if (v.getId() == R.id.btn_later_on){
            updateData("0");
            finish();
        }
    }

    private void updateData() {
        if (bean != null){
            bean.setIsCommit("1");
            DBHelper.update(bean,bean.getTime());
        }
    }
    private void updateData(String open) {
        if (bean != null){
            bean.setOpen(open);
            DBHelper.update(bean,bean.getTime());
        }
    }

    private void closeAll() {
        player.stop();
        player.reset();
        if (vibrator != null){
            vibrator.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        closeAll();
        super.onDestroy();
    }


}
