package com.flb.sample.main;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.flb.sample.BaseActivity;
import com.flb.sample.alarm.AlarmClockActivity;
import com.flb.sample.R;
import com.flb.sample.adapter.MainRecyclerViewAdapter;
import com.flb.sample.douyin.DouYinActivity;
import com.flb.sample.dynamic.DynamicActivity;
import com.flb.sample.file.FileActivity;
import com.flb.sample.keyBoard.KeyBoardActivity;
import com.flb.sample.securityCode.SecurityCodeActivity;
import com.flb.sample.statusLayoutManager.StatusLayoutManagerActivity;
import com.flb.sample.zXing.ZXingActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements MainRecyclerViewAdapter.onClickItem {

    private RecyclerView mRecyclerView;
    private List<String> mList;
    private MainRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManage;
    Intent mIntent = null;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        mList.add("StatusLayoutManager");
        mList.add("SecurityCode");
        mList.add("KeyBoardStatus");
        mList.add("ThinkChange");
        mList.add("仿抖音上下滑动");
        mList.add("上传图片(多张)");
        mList.add("查看本地文件");
        mList.add("闹钟");
        setAdapter();
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.sample_rv);
        mLayoutManage = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManage);
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new MainRecyclerViewAdapter(this, mList);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnClickItemListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onClickItem(int position) {

        switch (position) {
            case 0:
                mIntent = new Intent(this, StatusLayoutManagerActivity.class);
                startActivity(mIntent);
                break;
            case 1:
                mIntent = new Intent(this, SecurityCodeActivity.class);
                startActivity(mIntent);
                break;
            case 2:
                mIntent = new Intent(this, KeyBoardActivity.class);
                startActivity(mIntent);
                break;
            case 3:
                mIntent = new Intent(this, ZXingActivity.class);
                startActivity(mIntent);
                break;
            case 4:
                mIntent = new Intent(this, DouYinActivity.class);
                startActivity(mIntent);
                break;
            case 5:
                mIntent = new Intent(this, DynamicActivity.class);
                startActivity(mIntent);
                break;
            case 6:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {
                            //申请的权限全部允许
                            mIntent = new Intent(MainActivity.this, FileActivity.class);
                            startActivity(mIntent);
                        } else {
                            //只要有一个权限被拒绝，就会执行
                            Toast.makeText(MainActivity.this, "未授权权限，部分功能不能使用", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case 7:
                mIntent = new Intent(this, AlarmClockActivity.class);
                startActivity(mIntent);
                break;
            default:
                break;
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
