package com.walle.meitu.data;

import com.walle.meitu.data.remote.NetWorkManager;
import com.walle.meitu.data.remote.SimpleSubscribeOperation;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.data.remote.service.DownloadFileService;
import com.walle.meitu.data.remote.service.PicTypeService;
import com.walle.meitu.data.remote.service.SearchPicService;

import java.io.File;

import retrofit2.Retrofit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public final class DataManager extends SimpleSubscribeOperation{

    private static Retrofit retrofit;

    static {
        retrofit = NetWorkManager.getInstance().getDefaultRetrofit();
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
