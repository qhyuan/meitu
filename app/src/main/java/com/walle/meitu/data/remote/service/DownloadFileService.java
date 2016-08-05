package com.walle.meitu.data.remote.service;

import java.io.File;

import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yqh on 2016/7/20
 */
public interface DownloadFileService {
    @POST("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/{png}")
    Observable<File> downloadFile(@Query("png") String url );
}
