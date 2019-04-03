package com.flb.sample.keyBoard;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.flb.sample.BaseFragment;
import com.flb.sample.R;

import java.lang.reflect.Method;

/**
 * author : 冯张伟
 * date : 2019/4/3
 */
public class OneFragment extends BaseFragment implements View.OnClickListener {

    private boolean mIsSoftKeyBoardShowing = false;
    private PopupWindow mSoftKeyboardTopPopupWindow;
    private EditText mInput_one;
    private EditText mInput_two;
    private int screenHeight;
    private int keyboardHeight;
    private boolean isHasFous;
    private RelativeLayout mLinear;

    @Override
    public int getLayoutId() {
        return R.layout.layout_fragment_one;
    }

    @Override
    public void initView(View GroupView) {
        mLinear = GroupView.findViewById(R.id.mLinear);
        mInput_one = GroupView.findViewById(R.id.mInput_one);
        mInput_two = GroupView.findViewById(R.id.mInput_two);


    }

    @Override
    public void initData() {
        getActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new KeyboardOnGlobalChangeListener());
        mInput_two.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.i("AAA","----焦点---" + hasFocus + "----键盘----" + mIsSoftKeyBoardShowing );
                isHasFous = hasFocus;
                if (hasFocus){  // 获取焦点
                    if (mIsSoftKeyBoardShowing){   // 键盘弹出
                        if (mSoftKeyboardTopPopupWindow == null){
                            showKeyboardTopPopupWindow(screenHeight/2,keyboardHeight);
                        }
                    }

                }else {
                    closePopWindow();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_add_pic){
            Toast.makeText(getContext(),"添加图片",Toast.LENGTH_SHORT).show();
        }
    }

    private class KeyboardOnGlobalChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {

        private int getScreenHeight() {
            return ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getHeight();
        }

        private int getScreenWidth() {
            return ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getWidth();
        }

        @Override
        public void onGlobalLayout() {
            Rect rect = new Rect();
            // 获取当前页面窗口的显示范围
            getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            screenHeight = getScreenHeight();
            // 输入法的高度
            keyboardHeight = screenHeight - rect.bottom;
            boolean preShowing = mIsSoftKeyBoardShowing;
            if (Math.abs(keyboardHeight) > screenHeight / 5) {
                mIsSoftKeyBoardShowing = true; // 超过屏幕五分之一则表示弹出了输入法
                Log.i("AAA","---软键盘弹出----" + "-----高度-----" + keyboardHeight);
                if (isHasFous){
                    showKeyboardTopPopupWindow(screenHeight /2, keyboardHeight);
                }
            } else {
                if (preShowing) {
                    Log.i("AAA","---软键盘关闭----" + "-----高度-----" + keyboardHeight);
                    closePopWindow();
                }
                mIsSoftKeyBoardShowing = false;
            }
        }
    }

    private void closePopWindow() {
        if (mSoftKeyboardTopPopupWindow != null && mSoftKeyboardTopPopupWindow.isShowing()) {
                mSoftKeyboardTopPopupWindow.dismiss();
                mSoftKeyboardTopPopupWindow = null;
            }
    }

    private void showKeyboardTopPopupWindow(int x, int y) {
        if (mSoftKeyboardTopPopupWindow != null && mSoftKeyboardTopPopupWindow.isShowing()) {
            updateKeyboardTopPopupWindow(x, y); //可能是输入法切换了输入模式，高度会变化（比如切换为语音输入）
            return;
        }

        View popupView = getLayoutInflater().inflate(R.layout.item_popwindow_input, null);


        mSoftKeyboardTopPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mSoftKeyboardTopPopupWindow.setTouchable(true);
        mSoftKeyboardTopPopupWindow.setOutsideTouchable(false);
        mSoftKeyboardTopPopupWindow.setFocusable(false);
        mSoftKeyboardTopPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED); //解决遮盖输入法
        mSoftKeyboardTopPopupWindow.showAtLocation(mLinear, Gravity.BOTTOM, x, y);
    }

    private void updateKeyboardTopPopupWindow(int x, int y) {
        if (mSoftKeyboardTopPopupWindow != null && mSoftKeyboardTopPopupWindow.isShowing()) {
            mSoftKeyboardTopPopupWindow.update(x, y, mSoftKeyboardTopPopupWindow.getWidth(), mSoftKeyboardTopPopupWindow.getHeight());
        }
    }

}
