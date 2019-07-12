package com.flb.sample.fzw.widgets;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * author : fengzhangwei
 * date : 2019/7/11
 */
public class TRTCVideoView extends TXCloudVideoView {
    private static final String TAG = "TRTCCloudVideoView";
    private View.OnClickListener mClickListener;
    private GestureDetector mSimpleOnGestureListener;

    public TRTCVideoView(Context context) {
        this(context, null);
    }

    public TRTCVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSimpleOnGestureListener = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                if (mClickListener != null) {
                    mClickListener.onClick(TRTCVideoView.this);
                }
                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (!mMoveable) return false;
                ViewGroup.LayoutParams params = TRTCVideoView.this.getLayoutParams();
                // 当 TRTCVideoView 的父容器是 RelativeLayout 的时候，可以实现拖动
                if (params instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) TRTCVideoView.this.getLayoutParams();
                    int newX = (int) (layoutParams.leftMargin + (e2.getX() - e1.getX()));
                    int newY = (int) (layoutParams.topMargin + (e2.getY() - e1.getY()));

                    layoutParams.leftMargin = newX;
                    layoutParams.topMargin = newY;

                    TRTCVideoView.this.setLayoutParams(layoutParams);
                }
                return true;
            }
        });
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return mSimpleOnGestureListener.onTouchEvent(event);
            }
        });
    }

    @Override
    public void setOnClickListener(@Nullable View.OnClickListener l) {
        mClickListener = l;
    }

    private boolean mMoveable;

    public void setMoveable(boolean enable) {
        mMoveable = enable;
    }
}
