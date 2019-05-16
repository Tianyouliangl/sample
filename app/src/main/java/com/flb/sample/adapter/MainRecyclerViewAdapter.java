package com.flb.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flb.sample.R;

import java.util.List;

/**
 * author : 冯张伟
 * date : On{DATE}
 */
public class MainRecyclerViewAdapter extends RecyclerView.Adapter<MainRecyclerViewAdapter.MainRecyclerViewHolder>{

    private Context mContext;
    private List<String> mList;
    private onClickItem mListener;

    public interface onClickItem{
       void   onClickItem(int position);
    }

    public MainRecyclerViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public MainRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_recyclerview, parent, false);
        return new MainRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainRecyclerViewHolder holder, final int position) {
        holder.tv_title.setText(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null){
                    mListener.onClickItem(position);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MainRecyclerViewHolder extends  RecyclerView.ViewHolder{

        public TextView tv_title;

        public MainRecyclerViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
    public void setOnClickItemListener(onClickItem item){
         mListener = item;
    }
}
