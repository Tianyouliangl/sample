package com.flb.sample.fzw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flb.sample.fzw.R;
import com.flb.sample.fzw.model.DialogBean;

import java.util.List;

/**
 * author : fengzhangwei
 * date : 2019/7/9
 */
public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.DialogViewHolder> {

    Context mContext;
    List<DialogBean> mList;

    public DialogAdapter(Context mContext, List<DialogBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public DialogViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_dialog_item, parent, false);
        return new DialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DialogViewHolder holder, int position) {
        holder.type.setText(mList.get(position).getType());
        holder.number.setText(String.valueOf(mList.get(position).getNumber())+"次");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class DialogViewHolder extends RecyclerView.ViewHolder {

        public TextView type;
        public TextView number;

        public DialogViewHolder(View itemView) {
            super(itemView);
            type = itemView.findViewById(R.id.tv_dialog_type);
            number = itemView.findViewById(R.id.tv_dialog_number);
        }
    }
}
