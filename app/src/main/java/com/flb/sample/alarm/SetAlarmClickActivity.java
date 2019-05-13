package com.flb.sample.alarm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.common.DBHelper;
import com.flb.sample.model.AlarmClockBean;
import com.flb.sample.widgets.TimeUtil;
import com.flb.sample.widgets.loopView.LoopView;
import com.flb.sample.widgets.loopView.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class SetAlarmClickActivity extends BaseActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    private ImageView cancel_clock;
    private ImageView save_clock;
    private LoopView loop_h;
    private LoopView loop_m;
    private List<String> list_h = new ArrayList<>();
    private List<String> list_m = new ArrayList<>();
    private static String SAVE = "save";  // 新加还是编辑
    private static String BEAN = "bean";
    private String sp_h;
    private String sp_m;
    private String selectH;
    private String selectM;
    private String selectType = "1";
    private String selectDelete = "0";
    private String selectShake = "1";
    private boolean save;
    private AlarmClockBean historyBean;
    private EditText ed_clock_name;
    private RadioGroup rg_group;
    private RadioButton rb_btn_t;
    private RadioButton rb_btn_x;
    private RadioButton rb_btn_y;
    private Button btn_delete_clock;
    private ImageView iv_delete;
    private RelativeLayout rl_one;
    private View lin_delete;
    private EditText ed_clock_remark;
    private ImageView iv_shake;

    public static void Intent(Context conn,Boolean b,AlarmClockBean bean){
        Intent intent = new Intent(conn, SetAlarmClickActivity.class);
        intent.putExtra(SAVE,b);
        intent.putExtra(BEAN,bean);
        conn.startActivity(intent);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_set_alarm_click;
    }

    @Override
    public void initView() {
        loop_h = findViewById(R.id.loop_h);
        loop_m = findViewById(R.id.loop_m);
        cancel_clock = findViewById(R.id.iv_cancel_clock);
        save_clock = findViewById(R.id.iv_save_clock);
        ed_clock_name = findViewById(R.id.ed_clock_name);
        btn_delete_clock = findViewById(R.id.btn_delete_clock);
        rg_group = findViewById(R.id.rg_group);
        rb_btn_t = findViewById(R.id.rb_btn_t);
        rb_btn_x = findViewById(R.id.rb_btn_x);
        rb_btn_y = findViewById(R.id.rb_btn_y);
        rl_one = findViewById(R.id.rl_one);
        iv_delete = findViewById(R.id.iv_delete);
        lin_delete = findViewById(R.id.lin_delete);
        ed_clock_remark = findViewById(R.id.ed_clock_remark);
        iv_shake = findViewById(R.id.iv_shake);
    }

    @Override
    public void initData() {
        cancel_clock.setOnClickListener(this);
        save_clock.setOnClickListener(this);
        btn_delete_clock.setOnClickListener(this);
        rg_group.setOnCheckedChangeListener(this);
        iv_delete.setOnClickListener(this);
        iv_shake.setOnClickListener(this);
        setData();
        initEvent();
    }

    private void setData() {
        save = getIntent().getBooleanExtra(SAVE, false);
        rl_one.setVisibility(View.GONE);
        lin_delete.setVisibility(View.GONE);
        if (!save){
            btn_delete_clock.setVisibility(View.VISIBLE);
            historyBean = (AlarmClockBean) getIntent().getSerializableExtra(BEAN);
            String time = historyBean.getTime();
            String type = historyBean.getType();
            String isShake = historyBean.getIsShake();
            String isDelete = historyBean.getIsDelete();
            String[] split = time.split(":");
            sp_h = split[0];
            sp_m = split[1];
            ed_clock_name.setText(historyBean.getNAME());
            ed_clock_remark.setText(historyBean.getAlarm_remark());
            if (type.equals("1")){
                rb_btn_t.setChecked(true);
                rb_btn_x.setChecked(false);
                rb_btn_y.setChecked(false);
            }
            if (type.equals("2")){
                rb_btn_t.setChecked(false);
                rb_btn_x.setChecked(false);
                rb_btn_y.setChecked(true);
                rl_one.setVisibility(View.VISIBLE);
                lin_delete.setVisibility(View.VISIBLE);
            }
            if (type.equals("3")){
                rb_btn_t.setChecked(false);
                rb_btn_x.setChecked(true);
                rb_btn_y.setChecked(false);
            }
            if (isDelete.equals("0")){
                iv_delete.setImageResource(R.mipmap.open_n);
            }
            if (isDelete.equals("1")){
                iv_delete.setImageResource(R.mipmap.open_y);
            }
            if (isShake.equals("0")){
                iv_shake.setImageResource(R.mipmap.open_n);
            }
            if (isShake.equals("1")){
                iv_shake.setImageResource(R.mipmap.open_y);
            }
            selectType = type;
            selectDelete = isDelete;
            selectShake = isShake;
        }
        for (int i = 1 ; i<25 ; i++){
            if (i < 10){
                list_h.add("0"+i);
            }else {
                list_h.add(i+"");
            }
        }
        for (int i = 0 ; i<60 ; i++){
            if (i < 10){
                list_m.add("0"+i);
            }else {
                list_m.add(i+"");
            }
        }
        loop_h.setItems(list_h);
        loop_m.setItems(list_m);

        for (int i = 0; i< list_h.size(); i++){
            if (save){
                if (list_h.get(i).equals(getH())){
                    selectH = getH();
                    loop_h.setCurrentPosition(i);
                }
            }
            if (!save){
                if (list_h.get(i).equals(sp_h)){
                    selectH = sp_h;
                    loop_h.setCurrentPosition(i);
                }
            }

        }

        for (int i = 0; i< list_m.size(); i++){
            if (save){
                if (list_m.get(i).equals(getM())){
                    selectM = getM();
                    loop_m.setCurrentPosition(i);
                }
            }
            if (!save){
                if (list_m.get(i).equals(sp_m)){
                    selectM = sp_m;
                    loop_m.setCurrentPosition(i);
                }
            }
        }




    }

    private void initEvent() {
        //滚动监听
        loop_h.setListener(new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                selectH = list_h.get(index);
            }
        });

        loop_m.setListener(new OnItemSelectedListener() {
            public void onItemSelected(int index) {
                selectM = list_m.get(index);
            }
        });
    }

    /**
     *
     * @param name   // 闹钟标题
     * @param time   // 响铃时间
     * @param type   // 每天  一次  周一至周五  1   2   3
     * @param delete // 是否删除
     * @param alarm_remark // 备注
     * @param open  // 是否开启
     */
    private void saveAlarmClock(String name, String time, String type, String delete, String alarm_remark, String open,String shake) {
        Boolean exist = DBHelper.isExist(time);
        if (exist){
            Toast.makeText(this,"闹钟已存在",Toast.LENGTH_SHORT).show();
        }
        if (!exist){
            DBHelper.insetClockData(getAlarmClockBean(name,time,type,delete,alarm_remark,open,shake));
        }
    }

    private AlarmClockBean getAlarmClockBean(String name, String time, String type, String delete, String alarm_remark, String open,String shake) {
        AlarmClockBean bean = new AlarmClockBean();
        bean.setNAME(name);
        bean.setTime(time);
        bean.setType(type);
        bean.setIsDelete(delete);
        bean.setAlarm_remark(alarm_remark);
        bean.setOpen(open);
        bean.setIsShake(shake);
        return bean;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_cancel_clock){
            if (save){
                finish();
            }
            if (!save){
                String sumTime = selectH + ":" + selectM;
                String ed_name = ed_clock_name.getText().toString();
                String re = ed_clock_remark.getText().toString();
                if (!sumTime.equals(historyBean.getTime()) ||
                        !selectType.equals(historyBean.getType()) ||
                        !ed_name.equals(historyBean.getNAME()) ||
                         !selectDelete.equals(historyBean.getIsDelete()) ||
                         !re.equals(historyBean.getAlarm_remark())){
                    Log.e("aaaa","----提示是否放弃编辑----");
                }else {
                    finish();
                }

            }
        }
        if (v.getId() == R.id.iv_save_clock){
            String sumTime = selectH + ":" + selectM;
            if (!selectType.equals("2")){ // 响完即删仅限于 方式为一次!
                selectDelete = "0";
            }
            if (save){
                String name = ed_clock_name.getText().toString();
                if (name.isEmpty()){
                    name = "闹钟" +(DBHelper.getClockDataAll().size() + 1);
                }
                 saveAlarmClock(name,sumTime,selectType,selectDelete,ed_clock_remark.getText().toString(),"1",selectShake);
                 finish();


            }
            if (!save){
                    AlarmClockBean bean = getAlarmClockBean(ed_clock_name.getText().toString(), sumTime, selectType, selectDelete, ed_clock_remark.getText().toString(),"1",selectShake);
                    int update = DBHelper.update(bean, historyBean.getTime());
                    if (update != -1){
                        finish();
                    }

            }

        }

        if (v.getId() == R.id.btn_delete_clock){
            int i = DBHelper.deleteClockData(historyBean.getTime());
            if (i != -1){
                Toast.makeText(this,"闹钟已删除",Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        if (v.getId() == R.id.iv_delete){
            if (selectDelete.equals("0")){
                selectDelete = "1";
                iv_delete.setImageResource(R.mipmap.open_y);
                return;
            }
            if (selectDelete.equals("1")){
                selectDelete = "0";
                iv_delete.setImageResource(R.mipmap.open_n);
                return;
            }
        }

        if (v.getId() == R.id.iv_shake){
            if (selectShake.equals("0")){
                selectShake = "1";
                iv_shake.setImageResource(R.mipmap.open_y);
                return;
            }
            if (selectShake.equals("1")){
                selectShake = "0";
                iv_shake.setImageResource(R.mipmap.open_n);
                return;
            }
        }
    }

    /**
     * 获得当前时间的分钟
     */
    public String getM() {
        String mm = TimeUtil.getTimeString("mm");
        return mm;
    }

    /**
     * 获得当前时间的小时
     */
    public String getH() {
        String hh = TimeUtil.getTimeString("HH");
        return hh;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.rb_btn_t){
            selectType = "1";
            rl_one.setVisibility(View.GONE);
            lin_delete.setVisibility(View.GONE);
        }
        if (checkedId == R.id.rb_btn_x){
            selectType = "3";
            rl_one.setVisibility(View.GONE);
            lin_delete.setVisibility(View.GONE);
        }
        if (checkedId == R.id.rb_btn_y){
            selectType = "2";
            rl_one.setVisibility(View.VISIBLE);
            lin_delete.setVisibility(View.VISIBLE);
        }
    }
}
