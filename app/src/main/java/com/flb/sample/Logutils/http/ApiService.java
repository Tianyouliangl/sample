package com.flb.sample.Logutils.http;
import com.flb.sample.Logutils.bean.Kuaidi;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {

    @FormUrlEncoded
    @POST(Api.PartUrl.QUERY)
    Observable<Kuaidi> query(@Field("key") String key, @Field("dtype") String dType, @Field("q") String name);


}
