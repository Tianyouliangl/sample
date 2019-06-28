package com.flb.sample.main;

import android.Manifest;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.adapter.MainRecyclerViewAdapter;
import com.flb.sample.alarm.AlarmClockActivity;
import com.flb.sample.bezierCurve.BezierCurveActivity;
import com.flb.sample.cloudvideo.CloudVideoActivity;
import com.flb.sample.douyin.DouYinActivity;
import com.flb.sample.dynamic.DynamicActivity;
import com.flb.sample.file.FileActivity;
import com.flb.sample.gallery.GalleryActivity;
import com.flb.sample.imageprogress.ImageProgressActivity;
import com.flb.sample.keyBoard.KeyBoardActivity;
import com.flb.sample.securityCode.SecurityCodeActivity;
import com.flb.sample.statusLayoutManager.StatusLayoutManagerActivity;
import com.flb.sample.suspend.SuspendActivity;
import com.flb.sample.widgets.SwipeItemLayout;
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
        mList.add("自定义TabLayout");
        mList.add("闹钟");
        mList.add("图片加载进度(没写完)");
        mList.add("贝塞尔曲线");
        mList.add("悬浮(根布局)");
        mList.add("RecyclerView(画廊效果)");
        mList.add("腾讯云视频Demo");
        setAdapter();
    }

    @Override
    public void initView() {
        createPermissions();
        mRecyclerView = findViewById(R.id.sample_rv);
        mLayoutManage = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManage);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    private void createPermissions() {
            RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (aBoolean) {

                        } else {
                            //只要有一个权限被拒绝，就会执行
                            finish();
                        }
                    }
                });
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
                mIntent = new Intent(MainActivity.this, FileActivity.class);
                startActivity(mIntent);
                break;
            case 7:
                mIntent = new Intent(this, AlarmClockActivity.class);
                startActivity(mIntent);
                break;
            case 8:
                mIntent = new Intent(this, ImageProgressActivity.class);
                startActivity(mIntent);
                break;
            case 9:
                mIntent = new Intent(this, BezierCurveActivity.class);
                startActivity(mIntent);
                break;
            case 10:
                mIntent = new Intent(this, SuspendActivity.class);
                startActivity(mIntent);
                break;
            case 11:
                mIntent = new Intent(this, GalleryActivity.class);
                startActivity(mIntent);
                break;
            case 12:
                mIntent = new Intent(this, CloudVideoActivity.class);
                startActivity(mIntent);
                break;
            default:
                break;
        }

    }

    @Override
    public void onDelete(int position) {
        mList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
