package com.walle.meitu.data.remote;

import com.walle.meitu.data.remote.model.SearchPic;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yqh on 2016/5/20.
 */
//常用的订阅操作
public class SimpleSubscribeOperation {
    protected final static   <T> Subscription toSubscribeOnIoWidthObserveOnMain(Observable<T> o, Subscriber<T> s){
        return  o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    protected final static  <T> Subscription toSubscribeOnMainWidthObserveOnMain(Observable<T> o, Subscriber<T> s){
        return  o.subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
