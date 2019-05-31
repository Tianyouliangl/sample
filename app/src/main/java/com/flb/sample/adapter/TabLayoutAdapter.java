package com.flb.sample.adapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.flb.sample.file.FileFragment;

import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/4/19
 */
public class TabLayoutAdapter extends FragmentPagerAdapter{

    private List<String> mList;

    public TabLayoutAdapter(FragmentManager fm, List<String> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        FileFragment fragment = new FileFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type",mList.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
