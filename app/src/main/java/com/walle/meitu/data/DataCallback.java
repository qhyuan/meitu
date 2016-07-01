package com.walle.meitu.data;

/**
 * Created by void on 16/6/30.
 */
public interface DataCallback<T> {
    void onSuccess(T result);
    void onError(Throwable error);
}
