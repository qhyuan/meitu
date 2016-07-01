package com.walle.meitu.data.remote.service;

import com.walle.meitu.data.remote.model.PicType;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import rx.Observable;

/**
 * Created by void on 16/6/30
 */
public interface PicTypeService {
    @Headers("apikey: fa15a16083c8774c92b971c64300f9c7")
    @GET("showapi_open_bus/pic/pic_type")
    Observable<PicType> getPicTypes();
}
