package com.flb.sample.fzw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flb.sample.fzw.R;
import com.flb.sample.fzw.douyin.MyVideo;

import java.util.List;

import cn.jzvd.Jzvd;


/**
 * author : 冯张伟
 * date : 2019/4/9
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoHolder>{


    private List<String> urlList;
    private Context mContext;

    public VideoAdapter(List<String> urlList, Context mContext) {
        this.urlList = urlList;
        this.mContext = mContext;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
        return new VideoHolder(view);
    }

    @Override
    public void onBindViewHolder(final VideoHolder holder, final int position) {

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;

        holder.mSurfaceView.setUp(urlList.get(position),"", Jzvd.CURRENT_STATE_NORMAL);
        if (position == 0) {
            holder.mSurfaceView.startVideo();
        }


    }



    @Override
    public int getItemCount() {
        return urlList.size();
    }


    public class VideoHolder extends RecyclerView.ViewHolder {


        public MyVideo mSurfaceView;

        public VideoHolder(View itemView) {
            super(itemView);
            mSurfaceView = itemView.findViewById(R.id.mSurfaceView);
        }
    }

}
