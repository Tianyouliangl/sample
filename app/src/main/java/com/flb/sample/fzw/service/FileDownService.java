package com.flb.sample.fzw.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.flb.sample.fzw.BaseApplication;
import com.flb.sample.fzw.model.AlarmClockBean;
import com.flb.sample.fzw.model.FileDownBean;
import com.flb.sample.fzw.widgets.LogUtil;
import com.luck.picture.lib.tools.StringUtils;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.transfer.COSXMLDownloadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * author : fengzhangwei
 * date : 2019/7/5
 */
public class FileDownService extends Service implements CosXmlResultListener, CosXmlProgressListener {

    private List<FileDownBean> mList;
    private int TypeAwait = 0; // 等待 // 上传
    private int TypeStart = 1; // 正在上传/下载
    private int TypeStop = 2; // 暂停
    private int TypeCancel = 3; // 取消
    private int TypeEndSuc = 4; // 完成

    private int downPosition = 0; // 下载的下标
    private TransferManager transferManager;
    private TransferConfig transferConfig;
    private COSXMLDownloadTask download;
    private IBinder mBind = new FileBind();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBind;
    }

    @Override
    public void onCreate() {
        initData();
        super.onCreate();
    }

    private void initData() {
        LogUtil.i("FileDownService--------initData()");
        mList = new ArrayList<>();
        transferConfig = new TransferConfig.Builder().build();
        transferManager = new TransferManager(BaseApplication.Companion.getCosXmlService(), transferConfig);
    }

    public void setData(FileDownBean bean) {
        if (bean != null) {
            delete(bean);
            LogUtil.i("setData downPosition == " + downPosition);
            mList.add(bean);
            startFile();
        }
    }

    public void delete(FileDownBean bean) {
        if (mList.size() > 0 && mList != null) {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getBucketName().equals(bean.getBucketName())) {
                    if (mList.get(i).getDownName().equals(bean.getDownName())) {
                        mList.remove(i);
                    }
                }
            }
        }
    }


    public void startFile() {
        if (mList.size() > 0) {
            FileDownBean downBean = mList.get(downPosition);
            int type = downBean.getType();
            if (type == TypeStart) {
                LogUtil.i("Start DownFile");
                downFile(downBean);
            }
            if (type == TypeAwait) {
                LogUtil.i("Start uploadFile");
                uploadFile(downBean);
            }
        }

    }

    private void uploadFile(FileDownBean downBean) {
        if (downBean.getUploadType() == TypeAwait) {
            String bucket = downBean.getBucketName(); //存储桶，格式：BucketName-APPID
            String cosPath = downBean.getCosPath(); //对象位于存储桶中的位置标识符，即对象键。如 cosPath = "text.txt";
            String srcPath = downBean.getPath();//"本地文件的绝对路径";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, cosPath, srcPath);
            BaseApplication.Companion.getCosXmlService().putObjectAsync(putObjectRequest, this);
            putObjectRequest.setProgressListener(this);
        }
    }

    private void downFile(FileDownBean downBean) {
        if (downBean.getDownType() == TypeAwait) {
            Context applicationContext = this; // getApplicationContext()
            String bucket = downBean.getBucketName(); //对象所在的存储桶
            String cosPath = downBean.getCosPath(); //即对象在 COS 上的绝对路径,格式如 cosPath = "text.txt";
            String savedDirPath = downBean.getPath();
            String savedFileName = (downBean.getDownName());
            download = transferManager.download(applicationContext, bucket, cosPath, savedDirPath, savedFileName);
            download.setCosXmlProgressListener(this);
            download.setCosXmlResultListener(this);
        }
    }

    @Override
    public void onSuccess(CosXmlRequest request, CosXmlResult result) {
        downPosition++;
        if (downPosition < mList.size()) {
            startFile();
        }
        EventBus.getDefault().postSticky("onSuccess");
    }

    @Override
    public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {

    }


    @Override
    public void onProgress(long complete, long target) {
        if (mList.size() > 0) {
            FileDownBean bean = mList.get(downPosition);
            int type = bean.getType();
            int progress = (int) (complete * 100L / target);
            if (type == TypeAwait) { // 上传进度
                bean.setUploadType(TypeStart);
                LogUtil.i("上传进度----" + progress + "%");
            }
            if (type == TypeStart) { // 下载进度
                bean.setDownType(TypeStart);
                LogUtil.i("下载进度----" + progress + "%");
            }
            bean.setPb(progress);
            if (progress >= 100){
                if (type == TypeAwait) {
                    bean.setUploadType(TypeEndSuc);
                    LogUtil.i("上传完成!");
                }
                if (type == TypeStart) {
                    bean.setDownType(TypeEndSuc);
                    LogUtil.i("下载完成!");
                }
            }
        }
    }

    public FileDownBean getFileDownBean() {
        if (downPosition < mList.size()) {
            return mList.get(downPosition);
        } else {
            return null;
        }

    }


    /**
     * 未下载完成个数
     *
     * @return
     */
    public int getDownFileSize() {
        if (mList != null) {
            int size = 0;
            for (int i = 0; i < mList.size(); i++) {
                FileDownBean bean = mList.get(i);
                if (bean.getType() == TypeAwait) {
                    if (bean.getUploadType() != 4) {
                        size += 1;
                    }
                }
                if (bean.getType() == TypeStart) {
                    if (bean.getDownType() != 4) {
                        size += 1;
                    }
                }
            }
            return size;
        } else {
            return 0;
        }
    }

    public List<FileDownBean> getList() {
        return mList;
    }

    public class FileBind extends Binder {
        public FileDownService getService() {
            return FileDownService.this;
        }
    }

    @Override
    public void onDestroy() {
        LogUtil.i("FileDownService--------onDestroy()");
        super.onDestroy();
    }
}
