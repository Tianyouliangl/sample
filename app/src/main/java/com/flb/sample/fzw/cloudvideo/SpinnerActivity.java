package com.flb.sample.fzw.cloudvideo;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.adapter.SpinnerAdapter;
import com.flb.sample.fzw.main.MainActivity;
import com.flb.sample.fzw.model.FileDownBean;
import com.flb.sample.fzw.service.FileDownService;
import com.flb.sample.fzw.widgets.LogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class SpinnerActivity extends BaseActivity implements OnStatusChildClickListener {

    private RecyclerView rv_spinner;
    private SpinnerAdapter adapter;
    private StatusLayoutManager statusLayoutManager;

    @Override
    public int getContentView() {
        return R.layout.activity_spinner;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (what == 0){
                FileDownBean bean = MainActivity.getProgress();
                if (bean != null){
                    if (adapter != null){
                        adapter.upItem(bean);
                    }
                }
                mHandler.sendEmptyMessageDelayed(0, 500);
            }
        }
    };

    @Override
    public void initView() {
        EventBus.getDefault().register(this);
        initToolbar("任务列表", true);
        rv_spinner = findViewById(R.id.rv_spinner);
        rv_spinner.setLayoutManager(new LinearLayoutManager(this));
        statusLayoutManager = new StatusLayoutManager.Builder(rv_spinner).setOnStatusChildClickListener(this).build();
    }

    @Override
    public void initData() {
        statusLayoutManager.showCustomLayout(R.layout.layout_status_loading_spinkit);
        getSpinner();
        mHandler.sendEmptyMessageDelayed(0, 500);
    }

    private void getSpinner() {
        List<FileDownBean> list = MainActivity.getSpinnerList();
        if (list.size() > 0){
            statusLayoutManager.showSuccessLayout();
        }else {
            statusLayoutManager.showCustomLayout(R.layout.layout_status_no_spinner,R.id.rl_add_task);
        }
        if (adapter == null) {
            adapter = new SpinnerAdapter(this, list);
            rv_spinner.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onGetMessage(String message) {
        if (message.equals("onSuccess")){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        mHandler.removeMessages(0);
        super.onDestroy();
    }

    @Override
    public void onEmptyChildClick(View view) {

    }

    @Override
    public void onErrorChildClick(View view) {

    }

    @Override
    public void onCustomerChildClick(View view) {
        if (view.getId() == R.id.rl_add_task){
            this.finish();
        }
    }
}
