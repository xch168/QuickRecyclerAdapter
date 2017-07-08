package com.github.xch168.quickrecycleradapter.sample;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by XuCanHui on 2017/7/8.
 */

public interface GankService {

    @GET("data/拓展资源/{pageSize}/{pageNum}")
    Observable<GankData> listGank(@Path("pageNum") int pageNum, @Path("pageSize") int pageSize);
}
