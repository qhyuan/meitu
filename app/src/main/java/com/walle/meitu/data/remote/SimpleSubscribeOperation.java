package com.walle.meitu.data.remote;

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
    protected static   <T> Subscription toSubscribeOnIoWidthObserveOnMain(Observable<T> o, Subscriber<T> s){
        return  o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    protected static  <T> Subscription toSubscribeOnMainWidthObserveOnMain(Observable<T> o, Subscriber<T> s){
        return  o.subscribeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
