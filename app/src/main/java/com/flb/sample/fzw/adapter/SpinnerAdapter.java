package com.flb.sample.fzw.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.model.FileDownBean;
import com.flb.sample.fzw.widgets.LogUtil;

import java.util.List;

/**
 * author : fengzhangwei
 * date : 2019/7/15
 */
public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.SpinnerViewHolder> {

    private Context mContext;
    private List<FileDownBean> mList;

    public SpinnerAdapter(Context mContext, List<FileDownBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public SpinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_spinner_item, parent, false);
        return new SpinnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpinnerViewHolder holder, int position) {
        FileDownBean downBean = mList.get(position);
        String downName = downBean.getDownName();
        int pb = downBean.getPb();
        int type = downBean.getType();
        int downType = downBean.getDownType();
        int uploadType = downBean.getUploadType();
        if (type == 1) {
            if (downType == 1) {
                holder.tv_spinner_title.setText("正在下载" + downName);
            }
            if (downType == 4) {
                holder.tv_spinner_title.setText("下载完成");
            }
        } else if (type == 0) {
            if (uploadType == 1) {
                holder.tv_spinner_title.setText("正在上传" + downName);
            }
            if (uploadType == 4) {
                holder.tv_spinner_title.setText("上传完成");
            }
        }else {
            holder.tv_spinner_title.setText(downName);
        }
        holder.pb_spinner.setProgress(pb);
        holder.tv_spinner_title.setText(downName);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class SpinnerViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_spinner_title;
        public NumberProgressBar pb_spinner;

        public SpinnerViewHolder(View itemView) {
            super(itemView);
            tv_spinner_title = itemView.findViewById(R.id.tv_spinner_title);
            pb_spinner = itemView.findViewById(R.id.pb_spinner);
        }
    }

    public void upItem(FileDownBean bean) {
        if (bean == null) return;
        String downName = bean.getDownName();
        String bucketName = bean.getBucketName();
        int pb = bean.getPb();
        for (int i = 0; i < mList.size(); i++) {
            if (downName.equals(mList.get(i).getDownName())&& bucketName.equals(mList.get(i).getBucketName())) {
                mList.get(i).setPb(pb);
                notifyDataSetChanged();
                break;
            }
        }
    }
}
