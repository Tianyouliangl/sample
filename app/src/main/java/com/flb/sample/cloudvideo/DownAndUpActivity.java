package com.flb.sample.cloudvideo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.flb.sample.BaseActivity;
import com.flb.sample.BaseApplication;
import com.flb.sample.R;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.bucket.GetBucketRequest;
import com.tencent.cos.xml.model.bucket.GetBucketResult;
import com.tencent.cos.xml.model.tag.ListBucket;

import java.util.List;

public class DownAndUpActivity extends BaseActivity {


    @Override
    public int getContentView() {
        return R.layout.activity_down_and_up;
    }

    @Override
    public void initView() {
        initToolbar(getIntentString("name"), true);
    }

    @Override
    public void initData() {
        refresh();
    }

    private void refresh() {
        getManagers();
    }

    private void getManagers() {
       // String bucket = "examplebucket-1250000000"; //格式：BucketName-APPID;
        GetBucketRequest getBucketRequest = new GetBucketRequest(getIntentString("name"));

       //前缀匹配，用来规定返回的对象前缀地址
        getBucketRequest.setPrefix("prefix");

       //单次返回最大的条目数量，默认 1000
        getBucketRequest.setMaxKeys(100);

       //定界符为一个符号，如果有 Prefix，
       //则将 Prefix 到 delimiter 之间的相同路径归为一类，定义为 Common Prefix，
       //然后列出所有 Common Prefix。如果没有 Prefix，则从路径起点开始
        getBucketRequest.setDelimiter('/');

      //发送请求
        BaseApplication.Companion.getCosXmlService().getBucketAsync(getBucketRequest, new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {
                GetBucketResult getBucketResult = (GetBucketResult) result;
                List<ListBucket.CommonPrefixes> contentsList = getBucketResult.listBucket.commonPrefixesList;
                Log.e("fzw","---请求成功----");
            }

            @Override
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException) {

            }
        });
    }
}
