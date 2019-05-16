package com.flb.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flb.sample.R;
import com.flb.sample.model.StatusBean;

import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/5/16
 */
public class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.StatusViewHolder>{

    private List<StatusBean> mList;
    private Context mContext;

    public StatusAdapter(List<StatusBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public StatusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_recyclerview, parent, false);
        return new StatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StatusViewHolder holder, int position) {

        String title = mList.get(position).getTitle();
        holder.tv_title.setText(title);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class StatusViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public StatusViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }

}
