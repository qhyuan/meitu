package com.walle.meitu.data;

import android.util.Log;

import com.walle.meitu.data.remote.NetWorkManager;
import com.walle.meitu.data.remote.SimpleSubscribeOperation;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.data.remote.service.DownloadFileService;
import com.walle.meitu.data.remote.service.PicTypeService;
import com.walle.meitu.data.remote.service.SearchPicService;
import com.walle.meitu.utils.LogUtil;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public final class DataManager extends SimpleSubscribeOperation{

    private static Retrofit retrofit;
    private static DataManager manager;

    static {
        retrofit = NetWorkManager.getInstance().getDefaultRetrofit();
        manager = new DataManager();
    }

    public static Subscription searchPic(int type, int page,Subscriber<SearchPic > subscriber){
        Observable<SearchPic> searchPicObservable =
                retrofit.create(SearchPicService.class).searchPic(type, page);
        return toSubscribeOnIoWidthObserveOnMain(searchPicObservable, subscriber);
    }

    public static Subscription getPicTypes(Subscriber<PicType> subscriber){
        return toSubscribeOnIoWidthObserveOnMain(retrofit.create(PicTypeService.class).getPicTypes(),subscriber);
    }
    public static Subscription downloadFile(String url, Subscriber<File> subscriber){
        Observable<File> response = retrofit.create(DownloadFileService.class).downloadFile(url);
        return toSubscribeOnIoWidthObserveOnMain(response,subscriber);
    }
}
