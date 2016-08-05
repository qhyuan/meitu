package com.walle.meitu.data.remote.convert;

import android.util.Log;

import com.walle.meitu.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * Created by yqh on 2016/7/20
 */
public class FileConverterFactory extends Converter.Factory {

    public static FileConverterFactory creat() {
        return new FileConverterFactory();
    }

    @Override
    public Converter<ResponseBody, File> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        LogUtil.i("Type="+type);
        for (Annotation ann :
                annotations) {
            LogUtil.i("test","ann="+ann);
        }
        if(type.equals(File.class)) {
            return new ResponseConverter();
        }
        return  null;
    }

}
