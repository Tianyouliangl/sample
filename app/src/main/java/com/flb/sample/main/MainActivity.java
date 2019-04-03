package com.flb.sample.main;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.adapter.MainRecyclerViewAdapter;
import com.flb.sample.keyBoard.KeyBoardActivity;
import com.flb.sample.securityCode.SecurityCodeActivity;
import com.flb.sample.statusLayoutManager.StatusLayoutManagerActivity;
import com.flb.sample.zXing.ZXingActivity;

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
        mList.add("SecurityCode");
        mList.add("KeyBoardStatus");
        mList.add("ThinkChange");
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
            case 1:
                mIntent = new Intent(this, SecurityCodeActivity.class);
                break;
            case 2:
                mIntent = new Intent(this, KeyBoardActivity.class);
                break;
            case 3:
                mIntent = new Intent(this, ZXingActivity.class);
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
