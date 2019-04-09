package com.flb.sample.douyin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.adapter.VideoAdapter;

import java.util.ArrayList;
import java.util.List;

public class DouYinActivity extends BaseActivity {

    private RecyclerView dy_rv;
    private List<String> urlList;
    private PagerSnapHelper snapHelper;
    private LinearLayoutManager layoutManager;

    @Override
    public int getContentView() {
        return R.layout.activity_dou_yin;
    }

    @Override
    public void initView() {

        dy_rv = findViewById(R.id.dy_rv);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        dy_rv.setLayoutManager(layoutManager);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(dy_rv);
    }

    @Override
    public void initData() {
        urlList = new ArrayList<>();
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        urlList.add("https://lmp4.vjshi.com/2019-04-08/9ff0602ba8eca8e3aef1043c39afd1bf.mp4");
        VideoAdapter adapter = new VideoAdapter(urlList, this);
        dy_rv.setAdapter(adapter);
    }
}
