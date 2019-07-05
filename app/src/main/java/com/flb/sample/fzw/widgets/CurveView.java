package com.flb.sample.fzw.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * author : fengzhangwei
 * date : 2019/6/12
 */
public class CurveView extends View {

    private Paint paint;
    private int centerX;
    private int centerY;
    private float mCircleX;
    private float mCircleY;
    private PointF start, end, control;
    private String TAG = "CurveView";
    private int mBaseY;
    private final int mCirclexRadius = 50;  // 圆的半径
    private Boolean isDrawBezier = false;
    private int mBaseX;

    public CurveView(Context context) {
        super(context);
        initData();
    }

    private void initData() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(8);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(60);

        start = new PointF(0, 0); // 开始点
        end = new PointF(0, 0);   // 结束点
        control = new PointF(0, 0); // 控制点

        mCircleX = 0;
        mCircleY = 0;
    }

    public CurveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    public CurveView(Context context, AttributeSet paramAttributeSet) {
        super(context, paramAttributeSet);
        initData();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBaseY = h;
        mBaseX = w;
        centerX = w / 2; // 宽的一半
        centerY = h - (h / 3);
        mCircleX = 20;
        mCircleY = h / 2;

        // 初始化数据点和控制点的位置
        start.x = centerX - ((centerX / 4) * 3); // 起始点X等于宽的一半减去200 中间靠左
        start.y = centerY;     // 起始点Y等于高的一半
        end.x = centerX + ((centerX / 4) * 3);   // 结束点X等于宽的一半加上200 中间靠右
        end.y = centerY;       // 结束点Y等于高的一半
        control.x = centerX;   // 控制点X等于宽的一半
        control.y = centerY; // 控制点Y等于高的一半减去100 中间靠上

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        start.x = centerX - ((centerX / 4) * 3);
        start.y = centerY;
        end.x = centerX + ((centerX / 4) * 3);
        end.y = centerY;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                isDrawBezier = true;
                float y = event.getY() +(event.getY()-start.y) + 100;
                float xL = start.x + 100;
                float xR = end.x - 100;
                if (y > start.y) {
                    control.x = event.getX();
                    control.y = y;
                    mCircleX = event.getX();
                    mCircleY = event.getY();
                }
                if (event.getY() < start.y){
                    control.x = centerX;   // 控制点X等于宽的一半
                    control.y = centerY;  // 控制点Y等于高的一半减去100 中间靠上
                    mCircleX = event.getX();
                    mCircleY = event.getY();
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                control.x = centerX;   // 控制点X等于宽的一半
                control.y = centerY;  // 控制点Y等于高的一半减去100 中间靠上
                mCircleY = mBaseY / 2;
                if (event.getX() < centerX) {  // 在左边
                    mCircleX = 20;
                } else {                       // 在右边
                    mCircleX = mBaseX;
                }
                invalidate();
                break;
        }
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isDrawBezier) {
            // 绘制数据点和控制点
            paint.setColor(Color.GRAY);
            paint.setStrokeWidth(20);
            canvas.drawPoint(start.x, start.y, paint);  // 开始
            canvas.drawPoint(end.x, end.y, paint);      // 结束
            canvas.drawPoint(control.x, control.y, paint);
            // 绘制贝塞尔曲线
            paint.setColor(Color.BLUE);
            paint.setStrokeWidth(8);

            Path path = new Path();
            path.moveTo(start.x, start.y);
            path.quadTo(control.x, control.y, end.x, end.y);
            canvas.drawPath(path, paint);
        }

        paint.setColor(Color.RED);
        canvas.drawCircle(mCircleX, mCircleY, mCirclexRadius, paint);
    }
}
