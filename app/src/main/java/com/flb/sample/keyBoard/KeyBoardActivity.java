package com.flb.sample.keyBoard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.widgets.KeyboardStatusDetector;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class KeyBoardActivity extends BaseActivity implements View.OnClickListener {

    private EditText ed_input_one;        //  输入框
    private boolean isShow;              //  是否获取焦点
    private RelativeLayout rl;          //  当前的根布局
    private View popWindowView;        // "显示在软键盘之上的View"
    private int inputHeight;          //  软键盘的高度
    private boolean keyBord = false; //  判断软键盘是否弹出

    @Override
    public int getContentView() {
        return R.layout.activity_key_board;
    }

    @Override
    public void initView() {
        ed_input_one = findViewById(R.id.ed_input_one);
        rl = findViewById(R.id.rl);
        ed_input_one.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isShow = hasFocus;
                boolean b = findChildView();
                Log.i("AAA","--是否获取焦点---" + hasFocus);
                Log.i("AAA","--是否弹出键盘---" + keyBord);
                Log.i("AAA","--是否包含View---" + b);
                if (hasFocus){
                    if (keyBord && inputHeight != 0){
                        if (!b){
                            addView();
                            changeLocation(inputHeight);
                        }else {
                            removeView();
                            addView();
                            changeLocation(inputHeight);
                        }
                    }
                }else {
                    if (b){
                        removeView();
                    }
                }
            }
        });
        popWindowView = LayoutInflater.from(this).inflate(R.layout.item_popwindow_input,null);
        ImageView iv_add_pic = popWindowView.findViewById(R.id.iv_add_pic);
        iv_add_pic.setOnClickListener(this);
    }

    @Override
    public void initData() {

        final KeyboardStatusDetector detector = new KeyboardStatusDetector();
        detector.registerActivity(this);
        detector.setVisibilityListener(new KeyboardStatusDetector.KeyboardVisibilityListener() {
            @Override
            public void onVisibilityChanged(boolean keyboardVisible,int height) {
                inputHeight = height;
                keyBord = keyboardVisible;
                boolean b = findChildView();
                Log.i("AAA","---键盘监听----高度---" + height);
                Log.i("AAA","---键盘监听----是否包含View----" + b);
                if (keyboardVisible){
                    if (isShow){
                        if (!b){
                            addView();
                            changeLocation(height);
                        }
                    }
                }else {
                    removeView();
                }
            }
        });
    }


    private boolean findChildView() {
        for (int i=0;i<rl.getChildCount();i++){
            View view = rl.getChildAt(i);
            if (view == popWindowView){
                return true;
            }
        }
        return false;
    }

    private void removeView() {
        rl.removeView(popWindowView);
    }

    private void addView() {
        rl.addView(popWindowView);
    }

    private void changeLocation(int height) {
        // 屏幕高度 除去虚拟按键
        int wHeight = getWindowManager().getDefaultDisplay().getHeight();
        Log.i("AAA","----屏幕高度----" + wHeight);
        boolean b = checkDeviceHasNavigationBar(this);
        Toast.makeText(this,"虚拟按键:"+b,Toast.LENGTH_SHORT).show();
        // 最终topMargin的高度
        int mHeight = wHeight - height - dp2px(32);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = mHeight;
        popWindowView.setLayoutParams(lp);
    }

    public  int dp2px(float dpValue){
        final float scale=getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_add_pic){
            Toast.makeText(this,"添加图片",Toast.LENGTH_SHORT).show();
        }
    }

    public  boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {

        }
        return hasNavigationBar;

    }

}
