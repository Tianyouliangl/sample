package com.flb.sample.alarm;


import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.adapter.AlarmClockAdapter;
import com.flb.sample.common.DBHelper;
import com.flb.sample.model.AlarmClockBean;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class AlarmClockActivity extends BaseActivity implements View.OnClickListener, AlarmClockAdapter.onItemInterface {

    private ImageView alarm_add;
    private RecyclerView alarm_rv;
    private LinearLayoutManager mLayoutManage;
    private List<AlarmClockBean> list = new ArrayList<>();
    private AlarmClockAdapter adapter;

    @Override
    public int getContentView() {
        return R.layout.activity_alarm_clock;
    }

    @Override
    public void initView() {
        alarm_add = findViewById(R.id.alarm_iv_add);
        alarm_rv = findViewById(R.id.alarm_rv);
        mLayoutManage = new LinearLayoutManager(this);
        alarm_rv.setLayoutManager(mLayoutManage);
    }

    @Override
    public void initData() {
        alarm_add.setOnClickListener(this);
        adapter = new AlarmClockAdapter(this, list);
        alarm_rv.setAdapter(adapter);
        adapter.setOnChangImageListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    private void getData() {
        if (list.size() > 0){
            list.clear();
        }
        list.addAll(DBHelper.getClockDataAll());
        adapter.notifyDataSetChanged();
    }
    private void getData(int position) {
        adapter.notifyItemChanged(position,1);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.alarm_iv_add){
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                @Override
                public void accept(Boolean aBoolean) throws Exception {
                    if (aBoolean) {
                        //申请的权限全部允许
                        startActivity(new Intent(AlarmClockActivity.this,SetAlarmClickActivity.class));
                    } else {
                        //只要有一个权限被拒绝，就会执行
                        Toast.makeText(AlarmClockActivity.this, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    @Override
    public void onClickItem(int position) {
        AlarmClockBean bean = list.get(position);
        int i = DBHelper.deleteClockData(bean.getNAME());
        if (i != -1){
            getData();
        }
    }

    @Override
    public void onOpenN(int position) {
        AlarmClockBean bean = list.get(position);
        DBHelper.isOpen(false,bean.time);
        getData();
        Toast.makeText(this,"闹钟已关闭",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOpenY(int position) {
        AlarmClockBean bean = list.get(position);
        DBHelper.isOpen(true,bean.time);
        getData();
        Toast.makeText(this,"闹钟已开启",Toast.LENGTH_SHORT).show();
    }
}
