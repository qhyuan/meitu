package com.walle.meitu.data;

import com.walle.meitu.data.remote.NetWorkManager;
import com.walle.meitu.data.remote.SimpleSubscribeOperation;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.data.remote.service.PicTypeService;
import com.walle.meitu.data.remote.service.SearchPicService;

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

    public static DataManager getInstance(){
        return manager;
    }

    public  Subscription searchPic(int type, int page,Subscriber<SearchPic > subscriber){
        Observable<SearchPic> searchPicObservable =
                retrofit.create(SearchPicService.class).searchPic(type, page);
        return toSubscribeOnIoWidthObserveOnMain(searchPicObservable, subscriber);
    }

    public Subscription getPicTypes(Subscriber<PicType> subscriber){
        return toSubscribeOnIoWidthObserveOnMain(retrofit.create(PicTypeService.class).getPicTypes(),subscriber);
    }

}
