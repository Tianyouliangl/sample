package com.flb.sample.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * author : 冯张伟
 * date : 2019/4/11
 */
public class DisplayChildGridView extends GridView{
    public DisplayChildGridView(Context context) {
        super(context);
    }

    public DisplayChildGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DisplayChildGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = View.MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
