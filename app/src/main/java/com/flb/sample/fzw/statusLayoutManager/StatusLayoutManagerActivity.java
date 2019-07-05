package com.flb.sample.fzw.statusLayoutManager;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.adapter.MainRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class StatusLayoutManagerActivity extends BaseActivity implements OnStatusChildClickListener {


    private RecyclerView status_rv;
    private List<String> mList;
    private MainRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManage;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public int getContentView() {
        return R.layout.activity_status_layout_manager;
    }

    @Override
    public void initData() {
        data();
    }

    private void data() {
        // 模拟请求数据
        new Thread(){
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mList = new ArrayList<>();
                for (int i=0 ; i< 10 ; i++){
                    mList.add("咱也不知道,咱也不敢问!(给老子问^.^)/咱也不知道,咱也不敢问!(给老子问^.^)/" +
                            "咱也不知道,咱也不敢问!(给老子问^.^)/咱也不知道,咱也不敢问!(给老子问^.^)/" +
                            "咱也不知道,咱也不敢问!(给老子问^.^)/咱也不知道,咱也不敢问!(给老子问^.^)/"+
                            "咱也不知道,咱也不敢问!(给老子问^.^)/咱也不知道,咱也不敢问!(给老子问^.^)/" +
                            "咱也不知道,咱也不敢问!(给老子问^.^)/咱也不知道,咱也不敢问!(给老子问^.^)/");
                }
                setAdapter();
            }
        }.start();

    }

    private void setAdapter() {
        if (mAdapter == null){
            mAdapter = new MainRecyclerViewAdapter(this,mList);
        }else {
            mAdapter.notifyDataSetChanged();
        }
        status_rv.setAdapter(mAdapter);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 加载成功，显示原布局
                statusLayoutManager.showSuccessLayout();
            }
        });
    }

    public void initView() {
        status_rv = findViewById(R.id.status_rv);
        mLayoutManage = new LinearLayoutManager(this);
        status_rv.setLayoutManager(mLayoutManage);
        statusLayoutManager = new StatusLayoutManager.Builder(status_rv).setOnStatusChildClickListener(this).build();
        // 加载中
        statusLayoutManager.showLoadingLayout();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.status_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         switch (item.getItemId()){
             case R.id.menu_loading:
                 // 加载中
                 statusLayoutManager.showLoadingLayout();
                 data();
                 break;
             case R.id.menu_empty:
                 // 空数据
                 statusLayoutManager.showEmptyLayout();
                 break;
             case R.id.menu_error:
                 // 加载失败
                 statusLayoutManager.showErrorLayout();
                 break;
             case R.id.menu_not_network:
                 // 自定义状态布局   无网络
                 statusLayoutManager.showCustomLayout(R.layout.item_status_not_network,R.id.tv_status_not_network);
                 break;
             case R.id.menu_not_permission:
                 // 自定义状态布局   无权限
                 statusLayoutManager.showCustomLayout(R.layout.item_status_not_permission,R.id.tv_status_not_permission);
                 break;
                     default:

                         break;
                 }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onEmptyChildClick(View view) {
        statusLayoutManager.showLoadingLayout();
        data();
    }

    @Override
    public void onErrorChildClick(View view) {
        statusLayoutManager.showLoadingLayout();
        data();
    }

    @Override
    public void onCustomerChildClick(View view) {
        Intent mIntent ;
        switch (view.getId()){
                     case R.id.tv_status_not_network:
                         mIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                         startActivity(mIntent);
                         break;
                     case R.id.tv_status_not_permission:
                         mIntent = new Intent(Settings.ACTION_SETTINGS);
                         startActivity(mIntent);
                         break;
                     default:
                         break;
                 }

    }
}
