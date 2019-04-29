package com.flb.sample.Logutils.http;
import com.flb.sample.Logutils.bean.Kuaidi;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface ApiService {

    @FormUrlEncoded
    @POST(Api.PartUrl.QUERY)
    Observable<Kuaidi> query(@Field("key") String key, @Field("dtype") String dType, @Field("q") String name);


    @Multipart                          //这里用Multipart
    @POST(Api.PartUrl.QUERY)                //请求方法为POST，里面为你要上传的url
    Call<Kuaidi> myUpload(@Part List<MultipartBody.Part> partLis);

}
