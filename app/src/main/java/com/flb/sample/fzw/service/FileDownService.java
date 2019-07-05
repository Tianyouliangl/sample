package com.flb.sample.fzw.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.flb.sample.fzw.BaseApplication;
import com.flb.sample.fzw.model.AlarmClockBean;
import com.flb.sample.fzw.model.FileDownBean;
import com.luck.picture.lib.tools.StringUtils;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlProgressListener;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.transfer.COSXMLDownloadTask;
import com.tencent.cos.xml.transfer.TransferConfig;
import com.tencent.cos.xml.transfer.TransferManager;

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
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

        }
    };
    private TransferManager transferManager;
    private TransferConfig transferConfig;
    private COSXMLDownloadTask download;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mList == null){
            mList = new ArrayList<>();
        }
        transferConfig = new TransferConfig.Builder().build();
        transferManager = new TransferManager(BaseApplication.Companion.getCosXmlService(), transferConfig);
    }

    public void setData(List<FileDownBean> list) {
        if (list != null) {
            mList.addAll(list);
            startFile();
        }
    }
    public void setData(FileDownBean bean) {
        if (bean != null) {
            mList.add(bean);
            startFile();
        }
    }

    private void startFile() {
        if (mList.size()>0){
            FileDownBean downBean = mList.get(downPosition);
            int type = downBean.getType();
            if (type == TypeAwait) {
                downFile(downBean);
            }
            if (type == TypeStart) {
                uploadFile(downBean);
            }
        }
    }

    private void uploadFile(FileDownBean downBean) {
        if (downBean.getUploadType() == TypeAwait) {

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

    }

    @Override
    public void onFail(CosXmlRequest request, CosXmlClientException exception, CosXmlServiceException serviceException) {

    }


    @Override
    public void onProgress(long complete, long target) {
        if (mList.size() > 0){
            FileDownBean bean = mList.get(downPosition);
            int type = bean.getType();
            if (type == TypeAwait){ // 上传进度
                Log.e("fzw","上传进度----" + target);
            }
            if (type == TypeStart){ // 下载进度
                Log.e("fzw","下载进度----" + target);
            }
        }
    }
}
