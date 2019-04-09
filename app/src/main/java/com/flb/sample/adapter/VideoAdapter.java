package com.flb.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.flb.sample.R;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

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
    public void onBindViewHolder(VideoHolder holder, int position) {

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;


        holder.jz_video.setUp(urlList.get(position),JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "");
        Glide.with(mContext).load(urlList.get(position)).into(holder.jz_video.thumbImageView);

    }

    @Override
    public int getItemCount() {
        return urlList.size();
    }


    class VideoHolder extends RecyclerView.ViewHolder {

        public JZVideoPlayerStandard jz_video;

        public VideoHolder(View itemView) {
            super(itemView);
            jz_video = itemView.findViewById(R.id.jz_video);
        }
    }
}
