package com.walle.meitu.data.remote.model;


/**
 * Created by yqh on 2016/5/31
 */
public interface IResult<T> {
     int getResultCode();
     T getData();
}
