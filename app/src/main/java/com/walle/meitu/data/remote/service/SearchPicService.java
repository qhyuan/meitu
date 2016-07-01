package com.walle.meitu.data.remote.service;

import com.walle.meitu.data.remote.model.SearchPic;

import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by void on 16/6/27.
 */
public interface SearchPicService {
    @Headers("apikey:fa15a16083c8774c92b971c64300f9c7")
    @GET("showapi_open_bus/pic/pic_search")
    Observable<SearchPic> searchPic(@Query("type") int type, @Query("page") int page);
}
