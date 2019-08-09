package com.flb.sample.fzw.main;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.flb.sample.fzw.BaseActivity;
import com.flb.sample.fzw.R;
import com.flb.sample.fzw.adapter.MainRecyclerViewAdapter;
import com.flb.sample.fzw.alarm.AlarmClockActivity;
import com.flb.sample.fzw.bezierCurve.BezierCurveActivity;
import com.flb.sample.fzw.cloudvideo.CloudVideoActivity;
import com.flb.sample.fzw.cloudvideo.video.CameraVideoActivity;
import com.flb.sample.fzw.douyin.DouYinActivity;
import com.flb.sample.fzw.dynamic.DynamicActivity;
import com.flb.sample.fzw.file.FileActivity;
import com.flb.sample.fzw.gallery.GalleryActivity;
import com.flb.sample.fzw.imageprogress.ImageProgressActivity;
import com.flb.sample.fzw.jND2B.JND2BActivity;
import com.flb.sample.fzw.keyBoard.KeyBoardActivity;
import com.flb.sample.fzw.model.FileDownBean;
import com.flb.sample.fzw.securityCode.SecurityCodeActivity;
import com.flb.sample.fzw.service.FileDownService;
import com.flb.sample.fzw.statusLayoutManager.StatusLayoutManagerActivity;
import com.flb.sample.fzw.suspend.SuspendActivity;
import com.flb.sample.fzw.thirdparty.SelectorActivity;
import com.flb.sample.fzw.widgets.LogUtil;
import com.flb.sample.fzw.widgets.SwipeItemLayout;
import com.flb.sample.fzw.zXing.ZXingActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity implements MainRecyclerViewAdapter.onClickItem {

    private RecyclerView mRecyclerView;
    private List<String> mList;
    private MainRecyclerViewAdapter mAdapter;
    private LinearLayoutManager mLayoutManage;
    private static FileDownService service;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            FileDownService.FileBind myBinder = (FileDownService.FileBind) binder;
            service = myBinder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name)  {
            service = null;
        }
    };

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mList = new ArrayList<>();
        mList.add("StatusLayoutManager");
        mList.add("SecurityCode");
        mList.add("KeyBoardStatus");
        mList.add("ThinkChange");
        mList.add("仿抖音上下滑动");
        mList.add("上传图片(多张)");
        mList.add("自定义TabLayout");
        mList.add("闹钟");
        mList.add("图片加载进度(没写完)");
        mList.add("表情键盘");
        mList.add("悬浮(根布局)");
        mList.add("RecyclerView(画廊效果)");
        mList.add("腾讯云视频Demo");
        mList.add("加拿大幸运2B");
        mList.add("第三方");
        mList.add("渐变");
        setAdapter();
    }

    @Override
    public void initView() {
        createPermissions();
        mRecyclerView = findViewById(R.id.sample_rv);
        mLayoutManage = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManage);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    private void createPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    Intent intent = new Intent(MainActivity.this, FileDownService.class);
                    bindService(intent, conn, BIND_AUTO_CREATE);
                } else {
                    //只要有一个权限被拒绝，就会执行
                   MainActivity.this.finish();
                }
            }
        });
    }

    private void setAdapter() {
        if (mAdapter == null) {
            mAdapter = new MainRecyclerViewAdapter(this, mList);
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mAdapter.setOnClickItemListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public void onClickItem(int position) {
        String s = mList.get(position);
        switch (s) {
            case "StatusLayoutManager":
                goActivity(StatusLayoutManagerActivity.class);
                break;
            case "SecurityCode":
                goActivity(SecurityCodeActivity.class);
                break;
            case "KeyBoardStatus":
                goActivity(KeyBoardActivity.class);
                break;
            case "ThinkChange":
                goActivity(ZXingActivity.class);
                break;
            case "仿抖音上下滑动":
                goActivity(DouYinActivity.class);
                break;
            case "上传图片(多张)":
                goActivity(DynamicActivity.class);
                break;
            case "自定义TabLayout":
                goActivity(FileActivity.class);
                break;
            case "闹钟":
                goActivity(AlarmClockActivity.class);
                break;
            case "图片加载进度(没写完)":
                goActivity(ImageProgressActivity.class);
                break;
            case "表情键盘":
                goActivity(BezierCurveActivity.class);
                break;
            case "悬浮(根布局)":
                goActivity(SuspendActivity.class);
                break;
            case "RecyclerView(画廊效果)":
                goActivity(GalleryActivity.class);
                break;
            case "腾讯云视频Demo":
                goActivity(CloudVideoActivity.class);
                break;
            case "加拿大幸运2B":
                goActivity(JND2BActivity.class);
                break;
            case "第三方":
                goActivity(SelectorActivity.class);
                break;
            case "渐变":
                goActivity(CameraVideoActivity.class);
                break;
            default:
                break;
        }

    }

    @Override
    public void onDelete(int position) {
        mList.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * 获取进度
     * @return
     */
    public static FileDownBean getProgress() {
        if (service != null) {
            FileDownBean downBean = service.getFileDownBean();
            if (downBean != null) {
                LogUtil.i("------获取进度-----" + downBean.getPb());
                return downBean;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 添加下载任务
     * @param bean
     */
    public static void setData(FileDownBean bean){
        if (service != null){
            service.setData(bean);
        }
    }

    /**
     * 获取未下载成功个数
     */
    public static int getSize(){
        if (service != null){
            int fileSize = service.getDownFileSize();
            LogUtil.i("------获取未下载成功个数-----" + fileSize);
            return fileSize;
        }else {
            return 0;
        }
    }

    /**
     * 获取任务列表
     */
    public static List<FileDownBean> getSpinnerList(){
        if (service != null){
            List<FileDownBean> list = service.getList();
            LogUtil.i("------获取任务列表-----" + list.size());
            return list;
        }else {
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }
}
