package com.flb.sample.fzw.bezierCurve;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.codebear.keyboard.CBEmoticonsKeyBoard;
import com.codebear.keyboard.data.AppFuncBean;
import com.codebear.keyboard.data.EmoticonsBean;
import com.codebear.keyboard.widget.CBAppFuncView;
import com.codebear.keyboard.widget.CBEmoticonsView;
import com.codebear.keyboard.widget.FuncLayout;
import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.main.MainActivity;
import com.tencent.rtmp.ui.TXCloudVideoView;

import java.util.ArrayList;
import java.util.List;

/**
 * 在输入框中显示小图标   需要在DefEmoticons更改
 */
public class BezierCurveActivity extends BaseActivity implements View.OnClickListener {


    private RecyclerView rcvContent;
    private CBEmoticonsKeyBoard cbEmoticonsKeyBoard;

    @Override
    public int getContentView() {
        return R.layout.activity_bezier_curve;
    }

    @Override
    public void initView() {
        rcvContent =findViewById(R.id.rcv_content);
        rcvContent.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        rcvContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (null != cbEmoticonsKeyBoard) {
                    cbEmoticonsKeyBoard.reset();
                }
                return false;
            }
        });

        initKeyBoard();
        initEmoticonsView();
        initAppFuncView();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    private void initKeyBoard() {
        cbEmoticonsKeyBoard = findViewById(R.id.ekb_emoticons_keyboard);

        cbEmoticonsKeyBoard.addOnFuncKeyBoardListener(new FuncLayout.OnFuncKeyBoardListener() {
            @Override
            public void onFuncPop(int height) {

            }

            @Override
            public void onFuncClose() {

            }
        });

//        cbEmoticonsKeyBoard.getBtnVoice().setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                return false;
//            }
//        });

        cbEmoticonsKeyBoard.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cbEmoticonsKeyBoard.getEtChat().setText("");
            }
        });
    }


    private void initEmoticonsView() {
        CBEmoticonsView cbEmoticonsView = new CBEmoticonsView(this);
        cbEmoticonsView.init(getSupportFragmentManager());
        cbEmoticonsKeyBoard.setEmoticonFuncView(cbEmoticonsView);

        cbEmoticonsView.addEmoticonsWithName(new String[]{"hospital"});

        cbEmoticonsView.setOnEmoticonClickListener(new CBEmoticonsView.OnEmoticonClickListener() {
            @Override
            public void onEmoticonClick(EmoticonsBean emoticon, boolean isDel) {
                if (isDel) {
                    Log.i("onEmoticonClick", "delete");
                    cbEmoticonsKeyBoard.delClick();
                } else {
                    List<EmoticonsBean> list = emoticon.getEmoticonsBeanList();
                    Log.i("EmoList", "-----size-----" + list.size());
                    String content = null;
                    if ("default".equals(emoticon.getParentTag())) {
                         content = emoticon.getName();
                    } else if ("hospital".equals(emoticon.getParentTag())){
                        String text = "bigEmoticon : " + " - " + emoticon.getName() + " - " + emoticon.getParentId() + " - " + emoticon.getId() + "." + emoticon.getIconType();
                        content = emoticon.getName();
                      //  Log.i("onEmoticonClick", text);
                    }
                    if (TextUtils.isEmpty(content)) {
                        return;
                    }

                    Log.i("onEmoticonClick", content + "--------em--------" + emoticon.getIconUri());
                    int index = cbEmoticonsKeyBoard.getEtChat().getSelectionStart();
                    Editable editable = cbEmoticonsKeyBoard.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        });
    }



    private void initAppFuncView() {
        CBAppFuncView cbAppFuncView = new CBAppFuncView(this);
        cbEmoticonsKeyBoard.setAppFuncView(cbAppFuncView);
        List<AppFuncBean> appFuncBeanList = new ArrayList<>();
        appFuncBeanList.add(new AppFuncBean(0,R.mipmap.ic_launcher, "图片"));
        appFuncBeanList.add(new AppFuncBean(1,R.mipmap.ic_launcher, "兼职"));
        appFuncBeanList.add(new AppFuncBean(2,R.mipmap.ic_launcher, "快捷回复"));
        appFuncBeanList.add(new AppFuncBean(3,R.mipmap.ic_launcher, "定位"));
        appFuncBeanList.add(new AppFuncBean(4,R.mipmap.ic_launcher, "图片"));
        appFuncBeanList.add(new AppFuncBean(5,R.mipmap.ic_launcher, "兼职"));
        appFuncBeanList.add(new AppFuncBean(6,R.mipmap.ic_launcher, "快捷回复"));
        appFuncBeanList.add(new AppFuncBean(7,R.mipmap.ic_launcher, "定位"));
        cbAppFuncView.setAppFuncBeanList(appFuncBeanList);
        cbAppFuncView.setOnAppFuncClickListener(new CBAppFuncView.OnAppFuncClickListener() {
            @Override
            public void onAppFunClick(AppFuncBean emoticon) {
                String text = emoticon.getTitle();
                Toast.makeText(BezierCurveActivity.this, text, Toast.LENGTH_SHORT).show();
                Log.i("onAppFunClick", text);
            }
        });
    }


}
