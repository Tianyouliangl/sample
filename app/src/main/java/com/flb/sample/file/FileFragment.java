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

    private TextView tv_file_hint;

    @Override
    protected int setContentView() {
        return R.layout.fragment_file;
    }

    @Override
    protected void init(View view) {
        tv_file_hint = view.findViewById(R.id.tv_file_hint);
    }

    @Override
    protected void lazyLoad() {
        String type = getArguments().getString("type");
        tv_file_hint.setText(type);
    }


}
