package com.flb.sample.fzw.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.widget.ImageView;

/**
 * author : fengzhangwei
 * date : 2019/7/5
 */
@SuppressLint("AppCompatCustomView")
public class MoveImageView extends ImageView {
    public MoveImageView(Context context) {
        super(context);
    }

    public void setMPointF(PointF pointF) {
        setX(pointF.x);
        setY(pointF.y);
    }
}
