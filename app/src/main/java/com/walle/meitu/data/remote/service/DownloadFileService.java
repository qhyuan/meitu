package com.walle.meitu.data.remote.service;

import java.io.File;
import retrofit2.Call;

import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by yqh on 2016/7/20
 */
public interface DownloadFileService {
    @POST()
    Observable<File> downloadFile(@Url String url);
}
