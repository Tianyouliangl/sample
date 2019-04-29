package com.flb.sample.file;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flb.sample.BaseFragment;
import com.flb.sample.BaseLazyFragment;
import com.flb.sample.R;
import com.flb.sample.widgets.QueryFileUtils;

import java.util.ArrayList;
import java.util.Map;

import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * author : 冯张伟
 * date : 2019/4/19
 */
public class FileFragment extends BaseLazyFragment{

    private RecyclerView file_rl;
    private LinearLayoutManager layoutManager;
    private StatusLayoutManager statusLayoutManager;
    private LinearLayout file_ll;

    @Override
    protected int setContentView() {
        return R.layout.fragment_file;
    }

    @Override
    protected void init(View view) {
        Log.i("fzw","----initView---" );
        file_rl = view.findViewById(R.id.file_rl);
        file_ll = view.findViewById(R.id.file_ll);
        statusLayoutManager = new StatusLayoutManager.Builder(file_ll).build();
        layoutManager = new LinearLayoutManager(getContext());
        file_rl.setLayoutManager(layoutManager);
        statusLayoutManager.showCustomLayout(R.layout.item_status_loading);
    }

    @Override
    protected void lazyLoad() {
        Log.i("fzw","----initData---");
        String type = getArguments().getString("type");
        ArrayList<Map<String, String>> fileAsType = QueryFileUtils.getFileAsType(type);

    }


}
