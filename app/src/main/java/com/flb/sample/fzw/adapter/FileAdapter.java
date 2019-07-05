package com.flb.sample.fzw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flb.sample.fzw.R;
import com.tencent.cos.xml.model.tag.ListAllMyBuckets;
import com.tencent.cos.xml.model.tag.ListBucket;

import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/4/19
 */
public class FileAdapter extends RecyclerView.Adapter<FileAdapter.FileViewHolder> {

    List<ListBucket.Contents> contentsList;
    Context mContext;
    private fileInterface mOnClick;

    public interface fileInterface {
        void onClickItem(ListBucket.Contents contents);

        void onClickItemDown(ListBucket.Contents contents,View view);
    }

    public FileAdapter(List<ListBucket.Contents> contentsList, Context mContext) {
        this.contentsList = contentsList;
        this.mContext = mContext;
    }

    @Override
    public FileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_file_item, parent, false);
        return new FileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FileViewHolder holder, int position) {
        ListBucket.Contents contents = contentsList.get(position);
        holder.tv_title.setText(contents.key);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClick != null){
                    mOnClick.onClickItem(contents);
                }
            }
        });
        holder.iv_file_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnClick != null){
                    mOnClick.onClickItemDown(contents,holder.iv_file_down);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return contentsList.size();
    }

    public class FileViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_title;
        public ImageView iv_file_down;

        public FileViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_file_title);
            iv_file_down = itemView.findViewById(R.id.iv_file_down);
        }
    }

    public void setOnClickFileItem(fileInterface fileInterface) {
        this.mOnClick = fileInterface;
    }
}
