package com.walle.meitu;

import android.app.Application;

import com.walle.meitu.data.remote.NetWorkManager;

/**
 * Created by void on 16/6/30.
 */
public class App extends Application {
     private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NetWorkManager.getInstance().init(this);
    }
    public static App getInstance(){
        return instance;
    }
}
