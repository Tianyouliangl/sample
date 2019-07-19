package com.flb.sample.fzw.cloudvideo;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Environment;
import android.os.IBinder;
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
import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.BaseApplication;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.adapter.FileAdapter;
import com.flb.sample.fzw.main.MainActivity;
import com.flb.sample.fzw.model.FileBean;
import com.flb.sample.fzw.model.FileDownBean;
import com.flb.sample.fzw.service.FileDownService;
import com.flb.sample.fzw.widgets.FileUtils;
import com.flb.sample.fzw.widgets.LogUtil;
import com.luck.picture.lib.permissions.RxPermissions;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.bucket.GetBucketRequest;
import com.tencent.cos.xml.model.bucket.GetBucketResult;
import com.tencent.cos.xml.model.tag.ListBucket;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

public class DownAndUpActivity extends BaseActivity implements OnStatusChildClickListener, FileAdapter.fileInterface, Animator.AnimatorListener, View.OnClickListener{


    private RecyclerView rv_file;
    private StatusLayoutManager statusLayoutManager;
    private List<FileBean> contentsList = new ArrayList<>();
    private FileAdapter fileAdapter;
    private TextView tv_file_number;
    private RelativeLayout rl_file;
    private RelativeLayout group_rl;
    private ImageView iv;
    private long firstTime;
    private String bucketName;
    private String mFilePath;


    @Override
    public int getContentView() {
        return R.layout.activity_down_and_up;
    }

    @Override
    public void initView() {
        bucketName = getIntentString("name");
        initToolbar(bucketName, true, R.mipmap.upload_file, this);
        rv_file = findViewById(R.id.rv_file);
        tv_file_number = findViewById(R.id.tv_file_number);
        rl_file = findViewById(R.id.rl_file);
        group_rl = findViewById(R.id.group_rl);
        statusLayoutManager = new StatusLayoutManager.Builder(rv_file).setOnStatusChildClickListener(this).build();
        mFilePath = Environment.getExternalStorageDirectory() + "/sample/" + bucketName;
    }

    @Override
    public void initData() {
        rv_file.setLayoutManager(new LinearLayoutManager(this));
        fileAdapter = new FileAdapter(contentsList, this);
        rv_file.setAdapter(fileAdapter);
        fileAdapter.setOnClickFileItem(this);
        rl_file.setOnClickListener(this);
    }

    private void refresh() {
        statusLayoutManager.showCustomLayout(R.layout.layout_status_loading_spinkit);
        if (contentsList != null) {
            contentsList.clear();
        }
        int size = MainActivity.getSize();
        setNumber(size);
        getManagers();
    }

    private void getManagers() {

        GetBucketRequest getBucketRequest = new GetBucketRequest(getIntentString("name"));
        BaseApplication.Companion.getCosXmlService().getBucketAsync(getBucketRequest, new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                GetBucketResult getBucketResult = (GetBucketResult) result;
                List<ListBucket.Contents> contentsLis = getBucketResult.listBucket.contentsList;
                for (int i = 0; i < contentsLis.size(); i++) {
                    ListBucket.Contents contents = contentsLis.get(i);
                    boolean fileB = FileUtils.isFileExists(new File(mFilePath + "/" + contents.key));
                    FileBean bean = new FileBean();
                    bean.seteTag(contents.eTag);
                    bean.setKey(contents.key);
                    bean.setLastModified(contents.lastModified);
                    bean.setOwner(contents.owner);
                    bean.setSize(contents.size);
                    bean.setStorageClass(contents.storageClass);
                    if (fileB) {
                        bean.setType(1);
                    }
                    if (!fileB) {
                        bean.setType(0);
                    }
                    contentsList.add(bean);
                }
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
        if (view.getId() == R.id.rl_null) {
            checkedFile();
        }
    }

    private void checkedFile() {

    }

    @Override
    public void onClickItem(FileBean contents) {

    }

    @Override
    public void onClickItemDown(FileBean contents, View v) {
        getPermission(contents, v);
    }

    @Override
    public void onClickItemDelete() {

    }

    private void getPermission(FileBean contents, View v) {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    startDownFile(contents, v);
                } else {
                    LogUtil.i("请打开权限,否则无法下载!");
                }
            }
        });
    }

    private void startDownFile(FileBean contents, View v) {
        boolean fileB = FileUtils.isFileExists(new File(mFilePath + "/" + contents.key));
        if (fileB) {
            LogUtil.i("文件已存在!");
        } else {
            Boolean b = changeGroupUI(350);
            if (!b) {
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
                group_rl.addView(iv, lp);
                //横向移动
                ObjectAnimator oaX = ObjectAnimator.ofFloat(iv, "translationX", cartLocation[0] - addLocation[0] + v.getWidth() / 2);
                //纵向
                ObjectAnimator oaY = ObjectAnimator.ofFloat(iv, "translationY", cartLocation[1] - addLocation[1]);
                oaX.setInterpolator(new LinearInterpolator());
                oaY.setInterpolator(new AccelerateInterpolator());
                AnimatorSet set = new AnimatorSet();
                set.play(oaX).with(oaY);
                set.setDuration(300).start();
                set.addListener(DownAndUpActivity.this);
            }
        }
    }

    /**
     * 下载信息
     * @param contents
     */
    private void downFile(FileBean contents) {
        File file = new File(mFilePath);
        Boolean b = FileUtils.createOrExistsDir(file);
        if (b) {
            FileDownBean downBean = new FileDownBean();
            downBean.setBucketName(bucketName);
            downBean.setType(1);
            downBean.setCosPath(contents.key);
            downBean.setDownName(contents.key);
            downBean.setPath(file.getAbsolutePath());
            MainActivity.setData(downBean);
        } else {
            LogUtil.i("文件创建失败!");
        }
    }

    /**
     * 获取左下角图片布局的位置
     *
     * @return
     */
    public int[] getCartLocation() {
        int[] cartLocation = new int[2];
        rl_file.getLocationInWindow(cartLocation);
        return cartLocation;
    }

    /**
     * 点击之后再多长时间之内不能再点击
     *
     * @param timeInterval 间隔时间
     * @return
     */
    Boolean changeGroupUI(long timeInterval) {
        // 第一次肯定会进入到if判断里面，然后把firstTime重新赋值当前的系统时间
        // 然后点击第二次的时候，当点击间隔时间小于2s
        if (System.currentTimeMillis() - firstTime >= timeInterval) {
            firstTime = System.currentTimeMillis();
            return false;
        } else {
            return true;
        }
    }

    void setNumber(int number) {
        if (number > 0) {
            if (number > 0 && number <= 99) {
                tv_file_number.setText(String.valueOf(number));
            } else {
                tv_file_number.setText("99+");
            }
            tv_file_number.setVisibility(View.VISIBLE);
        } else {
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
        int size = MainActivity.getSize();
        setNumber(size);
    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    @Override
    protected void onResume() {
        refresh();
        super.onResume();
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_file) {
            goActivity(SpinnerActivity.class);
        }
        if (v.getId() == R.id.iv_up) {
            checkedFile();
        }
    }
}
