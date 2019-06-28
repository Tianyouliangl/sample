package com.flb.sample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flb.sample.R;
import com.tencent.cos.xml.model.tag.ListAllMyBuckets;

import java.util.List;

/**
 * author : fengzhangwei
 * date : 2019/6/26
 */
public class BucketAdapter extends RecyclerView.Adapter<BucketAdapter.BucketHolder>{

    List<ListAllMyBuckets.Bucket> buckets;
    Context mContext;
    private BucketInterface mItemBucket;

    public interface BucketInterface{
        void onClickItem(ListAllMyBuckets.Bucket bean);
    }

    public BucketAdapter(List<ListAllMyBuckets.Bucket> buckets, Context mContext) {
        this.buckets = buckets;
        this.mContext = mContext;
    }

    @Override
    public BucketHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_cloud_video_item,parent, false);
        return new BucketHolder(view);
    }

    @Override
    public void onBindViewHolder(BucketHolder holder, int position) {
        holder.name.setText("储存桶名称 :"+buckets.get(position).name);
        holder.location.setText("所属地域 :"+buckets.get(position).location);
        holder.createDate.setText("创建时间 :"+buckets.get(position).createDate);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemBucket != null){
                    mItemBucket.onClickItem(buckets.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return buckets.size();
    }

    class BucketHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView location;
        public TextView createDate;

        public BucketHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_bucket_name);
            location = itemView.findViewById(R.id.tv_bucket_location);
            createDate = itemView.findViewById(R.id.tv_bucket_createDate);
        }
    }

    public void setOnClickItemBucket(BucketInterface itemBucket){
        mItemBucket = itemBucket;
    }

}
