package com.flb.sample.fzw.alarm;


import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.adapter.AlarmClockAdapter;
import com.flb.sample.fzw.common.DBHelper;
import com.flb.sample.fzw.model.AlarmClockBean;
import com.flb.sample.fzw.service.AlarmClockService;
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
        startService(new Intent(this, AlarmClockService.class));
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

    private int getData() {
        if (list.size() > 0){
            list.clear();
        }
        List<AlarmClockBean> sqList = DBHelper.getClockDataAll();
        list.addAll(sqList);
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }else {
            adapter = new AlarmClockAdapter(this, list);
            alarm_rv.setAdapter(adapter);
            adapter.setOnChangImageListener(this);
        }

        return sqList.size();
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
                        SetAlarmClickActivity.Intent(getBaseContext(),true,null);
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
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    //申请的权限全部允许
                    SetAlarmClickActivity.Intent(AlarmClockActivity.this,false,bean);
                } else {
                    //只要有一个权限被拒绝，就会执行
                    Toast.makeText(AlarmClockActivity.this, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onOpenN(int position) {
        AlarmClockBean bean = list.get(position);
        DBHelper.isOpen(false, bean.getTime());
        getData();
        Toast.makeText(this,"闹钟已关闭",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOpenY(int position) {
        AlarmClockBean bean = list.get(position);
        DBHelper.isOpen(true, bean.getTime());
        getData();
        Toast.makeText(this,"闹钟已开启",Toast.LENGTH_SHORT).show();
    }
}
