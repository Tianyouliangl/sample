package com.flb.sample.fzw.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flb.sample.fzw.R;

import java.util.List;

/**
 * author : fengzhangwei
 * date : 2019/7/9
 */
public class JNDAdapter extends RecyclerView.Adapter<JNDAdapter.JNDViewHolder> {

    Context mContext;
    List<String> mList;

    public JNDAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public JNDViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_jnd_item, parent, false);
        return new JNDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(JNDViewHolder holder, int position) {
        String s = mList.get(position);
        if (s.equals("豹子")){
            holder.tv_jnd_name.setTextColor(Color.RED);
        }else {
            holder.tv_jnd_name.setTextColor(Color.BLACK);
        }
        holder.tv_jnd_name.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class JNDViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_jnd_name;

        public JNDViewHolder(View itemView) {
            super(itemView);
            tv_jnd_name = itemView.findViewById(R.id.tv_jnd_name);
        }
    }

}
