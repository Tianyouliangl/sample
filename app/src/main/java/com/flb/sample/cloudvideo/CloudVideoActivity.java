package com.flb.sample.cloudvideo;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.flb.sample.BaseActivity;
import com.flb.sample.BaseApplication;
import com.flb.sample.R;
import com.flb.sample.adapter.BucketAdapter;
import com.tencent.cos.xml.CosXmlService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.listener.CosXmlResultListener;
import com.tencent.cos.xml.model.CosXmlRequest;
import com.tencent.cos.xml.model.CosXmlResult;
import com.tencent.cos.xml.model.service.GetServiceRequest;
import com.tencent.cos.xml.model.service.GetServiceResult;
import com.tencent.cos.xml.model.tag.ListAllMyBuckets;

import java.util.ArrayList;
import java.util.List;

import me.bakumon.statuslayoutmanager.library.OnStatusChildClickListener;
import me.bakumon.statuslayoutmanager.library.StatusLayoutManager;

/**
 * https://cloud.tencent.com/document/product/436/12159
 */

public class CloudVideoActivity extends BaseActivity implements View.OnClickListener, BucketAdapter.BucketInterface, OnStatusChildClickListener {


    private RecyclerView rv_video;
    private List<ListAllMyBuckets.Bucket> buckets = new ArrayList<>();
    private BucketAdapter adapter;
    private StatusLayoutManager statusLayoutManager;
    private GetServiceRequest getServiceRequest;

    @Override
    public int getContentView() {
        return R.layout.activity_cloud_video;
    }

    @Override
    public void initView() {
        rv_video = findViewById(R.id.rv_video);
        initToolbar("腾讯云视频","音视频",true,false,true,this);
    }

    @Override
    public void initData() {
        statusLayoutManager = new StatusLayoutManager.Builder(rv_video).setOnStatusChildClickListener(this).build();
        rv_video.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BucketAdapter(buckets, this);
        adapter.setOnClickItemBucket(this);
        rv_video.setAdapter(adapter);
    }

    private void refresh() {
        if (buckets != null || buckets.size() > 0){
            buckets.clear();
        }
        // 加载中
        statusLayoutManager.showLoadingLayout();
        getBuckets();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void getBuckets() {
        if (getServiceRequest == null){
            getServiceRequest = new GetServiceRequest();
        }
        //发送请求
        BaseApplication.Companion.getCosXmlService().getServiceAsync(getServiceRequest, new CosXmlResultListener() {
            @Override
            public void onSuccess(CosXmlRequest request, CosXmlResult result) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        statusLayoutManager.showSuccessLayout();
                        GetServiceResult getServiceResult = (GetServiceResult)result;
                        buckets.addAll(getServiceResult.listAllMyBuckets.buckets);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
            @Override
            public void onFail(CosXmlRequest cosXmlRequest, CosXmlClientException clientException, CosXmlServiceException serviceException)  {

            }
        });
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
               case R.id.iv_back:
                  finish();
               break;
               case R.id.tv_right_confirm:
                   goActivity(AVActivity.class);
                   finish();
               break;
               default:
               break;
        }
    }

    @Override
    public void onClickItem(ListAllMyBuckets.Bucket bean) {
        Bundle bundle = new Bundle();
        bundle.putString("name",bean.name);
        bundle.putString("location",bean.location);
        bundle.putString("createDate",bean.createDate);
        goActivity(DownAndUpActivity.class,bundle);
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
}
