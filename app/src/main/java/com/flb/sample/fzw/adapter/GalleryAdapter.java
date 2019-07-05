package com.flb.sample.fzw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.flb.sample.fzw.R;

import java.util.List;

/**
 * author : fengzhangwei
 * date : 2019/6/24
 */
public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.GalleryHolder> {

    private Context mContext;
    private List<String> mList;

    public GalleryAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.gallery_item, parent, false);
        // https://www.jianshu.com/p/b28d4554c968
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        int width = mContext.getResources().getDisplayMetrics().widthPixels;
        params.width = (int) (width * 0.85);
        view.setLayoutParams(params);
        return new GalleryHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryHolder holder, int position) {
        Glide.with(mContext).load(mList.get(position)).into(holder.iv_gallery);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class GalleryHolder extends RecyclerView.ViewHolder {

        public ImageView iv_gallery;

        public GalleryHolder(View itemView) {
            super(itemView);
            iv_gallery = itemView.findViewById(R.id.iv_gallery);
        }
    }
}
