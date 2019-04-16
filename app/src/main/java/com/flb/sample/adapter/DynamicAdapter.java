package com.flb.sample.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flb.sample.R;

import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/4/11
 */
public class DynamicAdapter extends BaseAdapter{

    private Context mContext;
    private List<String> mList;
    private onItemInterface mChangImage;

    public DynamicAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_img_section, (ViewGroup) convertView, false);

        ImageView iv_img = view.findViewById(R.id.iv_img);
        View fl_remove = view.findViewById(R.id.fl_remove);
        fl_remove.setVisibility(View.GONE);
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.deng)				//加载成功之前占位图
                .error(R.mipmap.error);


        fl_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mList.remove(position);
                notifyDataSetChanged();
            }
        });

        if (position == getCount()-1){
            iv_img.setImageResource(R.mipmap.icon_add_img);
            fl_remove.setVisibility(View.GONE);
            iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mChangImage != null){
                        mChangImage.onAddImage();
                    }
                }
            });
        }else {
            fl_remove.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(mList.get(position))
                    .apply(options)
                    .into(iv_img);
            iv_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mChangImage != null){
                        mChangImage.onExamine(position);
                    }
                }
            });
        }
        return view;
    }

    public interface onItemInterface {
        void onExamine(Integer position);
        void onAddImage();
    }

    public void setOnChangImageListener(onItemInterface itemInterface){
        this.mChangImage = itemInterface;
    }
}
