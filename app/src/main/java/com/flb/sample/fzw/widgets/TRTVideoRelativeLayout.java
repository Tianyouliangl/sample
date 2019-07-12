package com.flb.sample.fzw.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.flb.sample.fzw.R;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * author : fengzhangwei
 * date : 2019/7/12
 */
public class TRTVideoRelativeLayout extends RelativeLayout implements View.OnTouchListener {

    private final int ViewId = R.layout.trt_video_group;
    private RelativeLayout rl_big;
    private RelativeLayout rl_small;
    private final int mWidth = 140;
    private final int mHeight = 180;
    private final int mMarginTop = 20;
    private final int mMarginRight = 15;
    private Boolean isBig = false;
    private TXCloudVideoView tvv_big;
    private TXCloudVideoView tvv_small;

    public TRTVideoRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(ViewId, this, true);
        rl_big = view.findViewById(R.id.rl_big);
        rl_small = view.findViewById(R.id.rl_small);
        tvv_big = view.findViewById(R.id.tvv_big);
        tvv_small = view.findViewById(R.id.tvv_small);
        rl_big.setOnTouchListener(this);
        rl_small.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (v.getId() == R.id.rl_big) {
                    if (isBig) {
                        isBig = false;
                        zoomOpera(rl_big, rl_small);
                    }
                    return true;
                }
                if (v.getId() == R.id.rl_small) {
                    if (isBig == false) {
                        isBig = true;
                        zoomOpera(rl_small, rl_big);
                    }
                    return true;
                }

                break;
            default:

                break;
        }
        return false;
    }

    /**
     * @param sourcView 大画面
     * @param detView   小画面
     */
    private void zoomOpera(RelativeLayout sourcView, RelativeLayout detView) {

        RelativeLayout paretview = (RelativeLayout) sourcView.getParent();
        //设置本地小视图RelativeLayout 的属性
        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(dip2px(mWidth), dip2px(mHeight));
        params1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params1.topMargin = dip2px(mMarginTop);
        params1.rightMargin = dip2px(mMarginRight);
        detView.setLayoutParams(params1);
        //设置远程大视图RelativeLayout 的属性
        params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        sourcView.setLayoutParams(params1);
        paretview.bringChildToFront(detView);  //  置顶:解决小画面放大后之前的View(大画面)被遮盖问题

    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public TXCloudVideoView getSmallView() {
        return tvv_small;
    }

    public TXCloudVideoView getBigView() {
        return tvv_big;
    }
}
