package com.flb.sample.Logutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.flb.sample.Logutils.bean.Kuaidi;
import com.flb.sample.Logutils.http.Api;
import com.flb.sample.Logutils.http.ApiService;
import com.flb.sample.Logutils.http.RetrofitManager;
import com.flb.sample.Logutils.utils.LogUtil;
import com.flb.sample.R;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LogUtilsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_utils);
       // init();
    }

    private void init() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.baseUrl)
                .build();
        ApiService service = retrofit.create(ApiService.class);
        //1.创建MultipartBody.Builder对象
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);//表单类型

//2.获取图片，创建请求体
        File file=new File("路径");
        RequestBody body = RequestBody.create(MediaType.parse("multipart/form-data"),file);//表单类型

//3.调用MultipartBody.Builder的addFormDataPart()方法添加表单数据
        builder.addFormDataPart("key", "华为云上传对象的唯一标识");//传入服务器需要的key，和相应value值
        builder.addFormDataPart("AccessKeyId", "接口返回的参数accessKeyId");
        builder.addFormDataPart("policy", "接口返回的参数policy");
        builder.addFormDataPart("signature", "接口返回的参数signature");
        builder.addFormDataPart("file",file.getName(),body); //添加数据，body创建的请求体

//4.创建List<MultipartBody.Part> 集合，
//  调用MultipartBody.Builder的build()方法会返回一个新创建的MultipartBody
//  再调用MultipartBody的parts()方法返回MultipartBody.Part集合
        List<MultipartBody.Part> parts=builder.build().parts();

//5.最后进行HTTP请求，传入parts即可
        Call<Kuaidi> call = service.myUpload(parts);
        call.enqueue(new Callback<Kuaidi>() {
            @Override
            public void onResponse(Call<Kuaidi> call, Response<Kuaidi> response) {

            }

            @Override
            public void onFailure(Call<Kuaidi> call, Throwable t) {

            }
        });
    }
}
