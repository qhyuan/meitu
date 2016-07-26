package com.walle.meitu.view.showpic;

import android.content.Context;

import com.walle.meitu.App;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.download.ImageDownload;

import java.util.ArrayList;

/**
 * Created by yqh on 2016/7/26
 */
public class ShowPresenter implements ShowContract.Presenter {

    private final ShowContract.View mView;

    public ShowPresenter(ShowContract.View view){
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void download(String _url, Context context) {
        ImageDownload.download(_url, context, new ImageDownload.DownloadListener() {
            @Override
            public void onComplete(String path) {
                mView.shareSingleImage(path);
            }
        });
    }

    @Override
    public void download(String _url, Context context, boolean isSetWallpaper) {
        ImageDownload.download(_url,context,isSetWallpaper);
    }

    @Override
    public void download(ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist.List> picList) {
        for (SearchPic.ShowapiResBody.Pagebean.Contentlist.List item: picList) {
            ImageDownload.download(item.big, App.getInstance(),false);
        }
    }
}
