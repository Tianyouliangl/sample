package com.flb.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/4/19
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder>{


    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class FileViewHolder  extends RecyclerView.ViewHolder{

        public FileViewHolder(View itemView) {
            super(itemView);
        }
    }
}
