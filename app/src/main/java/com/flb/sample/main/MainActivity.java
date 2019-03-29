package com.flb.sample.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.main.MainRecyclerViewAdapter;
import com.flb.sample.statusLayoutManager.StatusLayoutManagerActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements MainRecyclerViewAdapter.onClickItem {

    private RecyclerView mRecyclerView;
    private List<String> mList;
    private MainRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManage;

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        mList.add("StatusLayoutManager");
        setAdapter();
    }

    @Override
    public void initView() {
        mRecyclerView = findViewById(R.id.sample_rv);
        mLayoutManage = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManage);
    }

    private void setAdapter() {
        if (mAdapter == null){
            mAdapter = new MainRecyclerViewAdapter(this,mList);
        }else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnClickItemListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onClickItem(int position) {
        Intent mIntent = null;
        switch (position){
            case 0:
                mIntent = new Intent(this, StatusLayoutManagerActivity.class);
                break;
            default:
                break;
        }
        startActivity(mIntent);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
