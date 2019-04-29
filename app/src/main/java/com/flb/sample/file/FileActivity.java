package com.flb.sample.file;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.adapter.TablayoutAdapter;
import com.flb.sample.common.Constant;

import java.util.ArrayList;
import java.util.List;

public class FileActivity extends BaseActivity{

    private TabLayout file_tab;
    private ViewPager file_vp;
    private List<String> mList = new ArrayList<>();
    private TablayoutAdapter tabAdapter;

    @Override
    public int getContentView() {
        return R.layout.activity_file;
    }

    @Override
    public void initView() {
        file_tab = findViewById(R.id.file_tab);
        file_vp = findViewById(R.id.file_vp);
    }

    @Override
    public void initData() {
        mList.add(Constant.type.FILE_ALL);
        mList.add(Constant.type.FILE_IMAGE);
        mList.add(Constant.type.FILE_MUSIC);
        mList.add(Constant.type.FILE_VIDOE);
        mList.add(Constant.type.FILE_RESTS);
        tabAdapter = new TablayoutAdapter(getSupportFragmentManager(),mList);
        file_vp.setAdapter(tabAdapter);
        file_tab.setupWithViewPager(file_vp);
    }
}
