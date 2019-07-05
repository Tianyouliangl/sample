package com.flb.sample.fzw.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.flb.sample.fzw.imageprogress.ImageProgressFragment;

import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/5/27
 */
public class ImageProgressAdapter extends FragmentPagerAdapter {

    private List<String> mList;

    public ImageProgressAdapter(FragmentManager fm,List<String> list) {
        super(fm);
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Fragment getItem(int position) {
        ImageProgressFragment fragment = new ImageProgressFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url",mList.get(position));
        fragment.setArguments(bundle);
        return fragment;
    }
}
