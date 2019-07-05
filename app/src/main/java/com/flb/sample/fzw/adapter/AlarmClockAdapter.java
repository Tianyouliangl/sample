package com.flb.sample.fzw.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.flb.sample.fzw.R;
import com.flb.sample.fzw.model.AlarmClockBean;

import java.util.List;

/**
 * author : 冯张伟
 * date : 2019/5/9
 */
public class AlarmClockAdapter extends RecyclerView.Adapter<AlarmClockAdapter.AlarmClockViewHolder>{
    private Context mContext;
    private List<AlarmClockBean> mList;
    private onItemInterface mChangImage;

    public AlarmClockAdapter(Context mContext, List<AlarmClockBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public AlarmClockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_alarm_item,parent,false);
        return new AlarmClockViewHolder(view);
    }


    @Override
    public void onBindViewHolder(AlarmClockViewHolder holder, int position) {

        String m = null;
        String type = mList.get(position).getType();
        String open = mList.get(position).getOpen();
        String time = mList.get(position).getTime();
        String name = mList.get(position).getNAME();
        String remark = mList.get(position).getAlarm_remark();
        holder.time.setText(time);
        holder.title.setText(name);
        if (type.equals("1")){
            m = "每天";
        }
        if (type.equals("2")){
            m = "响铃一次";
        }
        if (type.equals("3")){
            m = "周一至周五";
        }
        if (open.equals("0")){
            holder.iv_open.setImageResource(R.mipmap.open_n);
            holder.time.setTextColor(Color.rgb(200,200,200));
            holder.title.setTextColor(Color.rgb(200,200,200));
            holder.msg.setTextColor(Color.rgb(200,200,200));
        }
        if (open.equals("1")){
            holder.iv_open.setImageResource(R.mipmap.open_y);
            holder.time.setTextColor(Color.rgb(21,151,220));
            holder.title.setTextColor(Color.rgb(21,151,220));
            holder.msg.setTextColor(Color.rgb(21,151,220));
        }
        if (remark.isEmpty()){
            holder.msg.setText(m);
        }else {
            holder.msg.setText(m + " | " + remark);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChangImage != null){
                    mChangImage.onClickItem(position);
                }
            }
        });
        holder.iv_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mChangImage != null){
                    if (open.equals("0")){
                        mChangImage.onOpenY(position);
                    }
                    if (open.equals("1")){
                        mChangImage.onOpenN(position);
                    }
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public class AlarmClockViewHolder extends RecyclerView.ViewHolder{

        public TextView time;
        public TextView title;
        public TextView msg;
        public ImageView iv_open;

        public AlarmClockViewHolder(View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.alarm_item_time);
            title = itemView.findViewById(R.id.alarm_item_title);
            msg = itemView.findViewById(R.id.alarm_item_msg);
            iv_open = itemView.findViewById(R.id.alarm_item_iv_open);
        }
    }

    public interface onItemInterface {
        void onClickItem(int position);
        void onOpenN(int position);
        void onOpenY(int position);
    }

    public void setOnChangImageListener(onItemInterface itemInterface){
        this.mChangImage = itemInterface;
    }
}
