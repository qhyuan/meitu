package com.walle.meitu.data.remote;

import android.content.Context;


import com.walle.meitu.data.Constant;
import com.walle.meitu.data.remote.convert.FileConverterFactory;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 获取Retrofit实例
 * Created by yqh on 2016/5/20.
 */
public class NetWorkManager  {
    private static NetWorkManager manager = new NetWorkManager();
    private Retrofit retrofit;

    private NetWorkManager(){}
    public static NetWorkManager getInstance(){
        return manager;
    }
    public  void init(Context context) {
        final Cache cache = new Cache(context.getCacheDir(),1024*1024*8);

        final Logger logger = Logger.getLogger("OkHttpLog");
        //网络访问日志
        Interceptor logInterceptor = new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) {
                Request request = chain.request();
                request.newBuilder().cacheControl(new CacheControl.Builder().onlyIfCached().build());//配置缓存策略
                long t1 = System.nanoTime();
                logger.info(String.format("Sending request %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
                Response response = null;
                try {
                    response = chain.proceed(request);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                long t2 = System.nanoTime();
                logger.info(String.format("Received response for %s in %.1fms%n%s",
                        response.request().url(), (t2 - t1) / 1e6d, response.headers()));

                return response;
            }
        };
        //缓存日志
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                logger.info("cacheInfo:hitCount="+cache.hitCount()+"----requestCount="+cache.requestCount()+"--  request.cacheControl()="+  chain.request().cacheControl());
                return chain.proceed(chain.request());
            }
        };

        OkHttpClient client = new OkHttpClient.Builder().cache(cache).addInterceptor(interceptor).addInterceptor(logInterceptor).build();
        retrofit = new Retrofit.Builder().client(client).addConverterFactory(FileConverterFactory.creat())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(Constant.BaseUrl).build();
    }
    public  Retrofit getDefaultRetrofit() {
        checkInit();
        return retrofit;
    }

    private void checkInit(){
        if(null==retrofit){
            throw new RuntimeException("You should call init(Context context) first!");
        }
    }
}
