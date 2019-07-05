package com.flb.sample.fzw.cloudvideo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.BaseApplication;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.adapter.FileAdapter;
import com.flb.sample.fzw.model.FileDownBean;
import com.flb.sample.fzw.service.FileDownService;
import com.flb.sample.fzw.widgets.MoveImageView;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.bucket.GetBucketRequest;
import com.tencent.cos.xml.model.bucket.GetBucketResult;
import com.tencent.cos.xml.model.tag.ListBucket;

import java.util.ArrayList;
import java.util.List;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class DownAndUpActivity extends BaseActivity implements OnStatusChildClickListener, FileAdapter.fileInterface, Animator.AnimatorListener {


    private RecyclerView rv_file;
    private StatusLayoutManager statusLayoutManager;
    private List<ListBucket.Contents> contentsList = new ArrayList<>();
    private FileAdapter fileAdapter;
    private TextView tv_file_number;
    private RelativeLayout rl_file;
    private RelativeLayout group_rl;
    private ImageView iv;
    private long firstTime;
    private String bucketName;

    @Override
    public int getContentView() {
        return R.layout.activity_down_and_up;
    }

    @Override
    public void initView() {
        bucketName = getIntentString("name");
        initToolbar(bucketName, true);
        rv_file = findViewById(R.id.rv_file);
        tv_file_number = findViewById(R.id.tv_file_number);
        rl_file = findViewById(R.id.rl_file);
        group_rl = findViewById(R.id.group_rl);
        statusLayoutManager = new StatusLayoutManager.Builder(rv_file).setOnStatusChildClickListener(this).build();
    }

    @Override
    public void initData() {
        rv_file.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new FileAdapter(contentsList, this);
        rv_file.setAdapter(fileAdapter);
        fileAdapter.setOnClickFileItem(this);
        statusLayoutManager.showLoadingLayout();
        refresh();
        startService(new Intent(this, FileDownService.class));
    }

    private void refresh() {
        getManagers();
    }

    private void getManagers() {
        // String bucket = "examplebucket-1250000000"; //格式：BucketName-APPID;
        GetBucketRequest getBucketRequest = new GetBucketRequest(getIntentString("name"));

        //前缀匹配，用来规定返回的对象前缀地址
        // getBucketRequest.setPrefix("prefix");

        //单次返回最大的条目数量，默认 1000
        // getBucketRequest.setMaxKeys(100);

        //定界符为一个符号，如果有 Prefix，
        //则将 Prefix 到 delimiter 之间的相同路径归为一类，定义为 Common Prefix，
        //然后列出所有 Common Prefix。如果没有 Prefix，则从路径起点开始
        // getBucketRequest.setDelimiter('/');

        //发送请求
        BaseApplication.Companion.getCosXmlService().getBucketAsync(getBucketRequest, new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                GetBucketResult getBucketResult = (GetBucketResult) result;
                contentsList.addAll(getBucketResult.listBucket.contentsList);
                if (contentsList.size() > 0) {
                    runMain(0, "");
                } else {
                    runMain(1, "暂无文件,点击上传");
                }
            }

            @Override
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException) {

            }
        });
    }

    void runMain(int type, String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case 0:
                        if (statusLayoutManager != null) {
                            fileAdapter.notifyDataSetChanged();
                            rl_file.setVisibility(View.VISIBLE);
                            statusLayoutManager.showSuccessLayout();
                        }
                        break;
                    case 1:
                        if (statusLayoutManager != null) {
                            if (!TextUtils.isEmpty(msg)) {
                                View view = LayoutInflater.from(DownAndUpActivity.this).inflate(R.layout.status_net_null, null);
                                TextView tv_msg = view.findViewById(R.id.tv_net_msg);
                                tv_msg.setText(msg);
                                statusLayoutManager.showCustomLayout(view, R.id.rl_null);
                            } else {
                                statusLayoutManager.showCustomLayout(R.layout.status_net_null);
                            }
                        }
                        break;
                    case 2:

                        break;
                    default:

                        break;
                }

            }
        });
    }

    @Override
    public void onEmptyChildClick(View view) {

    }

    @Override
    public void onErrorChildClick(View view) {

    }

    @Override
    public void onCustomerChildClick(View view) {

    }

    @Override
    public void onClickItem(ListBucket.Contents contents) {

    }

    @Override
    public void onClickItemDown(ListBucket.Contents contents, View v) {
        Boolean aBoolean = changeGroupUI(350);
        if (!aBoolean){
            downFile(contents);
            int[] addLocation = new int[2];
            v.getLocationInWindow(addLocation);
            //得到左下角图标的坐标
            int[] cartLocation = getCartLocation();
            //添加一个imageview
            iv = new ImageView(v.getContext());
            iv.setImageResource(R.mipmap.file_down);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(v.getWidth(), v.getHeight());
            lp.leftMargin = addLocation[0];
            lp.topMargin = addLocation[1] - v.getHeight();
            group_rl.addView(iv,lp);
            //横向移动
            ObjectAnimator oaX = ObjectAnimator.ofFloat(iv, "translationX", cartLocation[0] - addLocation[0] + v.getWidth() / 2);
            //纵向
            ObjectAnimator oaY = ObjectAnimator.ofFloat(iv, "translationY", cartLocation[1] - addLocation[1]);
            oaX.setInterpolator(new LinearInterpolator());
            oaY.setInterpolator(new AccelerateInterpolator());
            AnimatorSet set = new AnimatorSet();
            set.play(oaX).with(oaY);
            set.setDuration(300).start();
            set.addListener(this);
        }

    }

    private void downFile(ListBucket.Contents contents) {
        FileDownBean downBean = new FileDownBean();
        downBean.setBucketName(bucketName);
        downBean.setType(1);
        downBean.setCosPath(contents.key);
        downBean.setPath("");
    }

    public int[] getCartLocation() {
        int[] cartLocation = new int[2];
        rl_file.getLocationInWindow(cartLocation);
        return cartLocation;
    }

    Boolean changeGroupUI(long timeInterval) {
        // 第一次肯定会进入到if判断里面，然后把firstTime重新赋值当前的系统时间
        // 然后点击第二次的时候，当点击间隔时间小于2s，那么退出应用；反之不退出应用
        if (System.currentTimeMillis() - firstTime >= timeInterval) {
            firstTime = System.currentTimeMillis();
            return false;
        } else {
            return true;
        }
    }

    void setNumber(int number){
        if (number > 0){
            if (number >0 && number <=99){
                tv_file_number.setText(String.valueOf(number));
            }else {
                tv_file_number.setText("99+");
            }
            tv_file_number.setVisibility(View.VISIBLE);
        }else {
            tv_file_number.setVisibility(View.GONE);
        }
    }



    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {
        group_rl.removeView(iv);
        Animation scaleAnim = AnimationUtils.loadAnimation(this, R.anim.shop_scale);
        rl_file.startAnimation(scaleAnim);
        setNumber(contentsList.size());
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

}
