package com.flb.sample.keyBoard;

import android.annotation.SuppressLint;
import android.app.Service;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flb.sample.BaseActivity;
import com.flb.sample.R;
import com.flb.sample.main.MainActivity;
import com.flb.sample.widgets.KeyboardStatusDetector;

import java.net.URLEncoder;

public class KeyBoardActivity extends BaseActivity implements View.OnClickListener {

    private PopupWindow popWindow;
    private EditText ed_input_one;
    private EditText ed_input_two;
    private boolean isShow;
    private boolean isKey ;
    private LinearLayout mla;
    private int mHeight;
    private ImageView iv_bottom;
    private Button btn_add;
    private RelativeLayout rl;

    @Override
    public int getContentView() {
        return R.layout.activity_key_board;
    }

    @Override
    public void initView() {
        ed_input_one = findViewById(R.id.ed_input_one);
        ed_input_two = findViewById(R.id.ed_input_two);
        iv_bottom = findViewById(R.id.iv_bottom);
        btn_add = findViewById(R.id.btn_add);
        rl = findViewById(R.id.rl);
        btn_add.setOnClickListener(this);
        ed_input_one.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                isShow = hasFocus;
            }
        });
    }

    @Override
    public void initData() {

        final KeyboardStatusDetector detector = new KeyboardStatusDetector();
        detector.registerActivity(this);
        detector.setVisibilityListener(new KeyboardStatusDetector.KeyboardVisibilityListener() {
            @Override
            public void onVisibilityChanged(boolean keyboardVisible,int height) {
                if (keyboardVisible){
                    Log.i("AAA","----软键盘弹出----高度---" + height);
                    if (isShow){
                        changeLocation(20);
                    }

                }else {
                    changeLocation(0);
                    Log.i("AAA","----软键盘收起----高度---" + height);
                }
            }
        });
    }

    private void changeLocation(int height) {
        Log.i("AAA","-----高度---" + height);
//        RelativeLayout.LayoutParams margin = new RelativeLayout.LayoutParams(iv_bottom.getLayoutParams());
//        int dpBottom =  height;
//        int dpLeft=dp2px( 6);
//
//        margin.setMargins(dpLeft, 0, 0, dpBottom);
//        iv_bottom.setLayoutParams(margin);
    }

    @SuppressLint("WrongConstant")
    private void createPopWindow(final int height, final boolean isShow){
         View view = LayoutInflater.from(this).inflate(R.layout.item_popwindow_input,null);
        mla = view.findViewById(R.id.mla);
        mla.setVisibility(isShow == true ? GridView.VISIBLE:GridView.GONE);
        popWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
         popWindow.setTouchable(true);
         popWindow.setTouchInterceptor(new View.OnTouchListener() {
             @Override
             public boolean onTouch(View v, MotionEvent event) {
                 return false;
             }
         });
         popWindow.setContentView(view);
         popWindow.setFocusable(false);
         popWindow.setOutsideTouchable(true);
         popWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
         Log.i("AAA","----偏移Y---" + (height-80));
         mHeight = height-80;
         popWindow.showAtLocation(this.getCurrentFocus(),Gravity.BOTTOM,0,height-80);
         popWindow.update();
         popWindowShowInput();
         popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
             @Override
             public void onDismiss() {
                 popWindowHideInput();
             }
         });

    }

    private void popWindowShowInput(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
        // 有焦点打开
//        imm.showSoftInput(getCurrentFocus(), 0);
        //无焦点打开
//        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 10086);
        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
    }

    private void popWindowHideInput(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Service.INPUT_METHOD_SERVICE);
        //有焦点关闭
//        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        // 无焦点关闭
//        InputMethodManager.HIDE_IMPLICIT_ONLY
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public  int dp2px(float dpValue){
        final float scale=getResources().getDisplayMetrics().density;

        return (int)(dpValue*scale+0.5f);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_add){
            View view = LayoutInflater.from(this).inflate(R.layout.item_popwindow_input,null);
            rl.addView(view);
        }
    }
}
