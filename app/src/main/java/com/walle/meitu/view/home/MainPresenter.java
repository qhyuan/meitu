package com.walle.meitu.view.home;

import android.support.v4.app.Fragment;

import com.walle.meitu.data.DataManager;
import com.walle.meitu.data.remote.SimpleSubscribers;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.utils.LogUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import rx.Subscription;

/**
 * Created by void on 16/6/30
 */
public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;
    private int mLoadedPage = 1;
    private int mLoadedId;
    private PicType.ShowapiResBody.RootListEntity mTitleEntity;
    private Map<Fragment,Subscription> mCallMap = new HashMap<>();
    public MainPresenter(MainContract.View view) {
        view.setPresenter(this);
        mView = view;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadPicTypes() {
        DataManager.getPicTypes(new MySubscriber<PicType>() {

            @Override
            public void onNext(PicType picType) {
                super.onNext(picType);
                mView.setLoadingIndicator(false);
                if(picType.showapi_res_code!=0){
                    mView.showLoadingError();
                }else {
                    mView.showPicTypes(picType.getData().rootList);
                }
            }
        });
    }

    @Override
    public void loadPicList(Fragment fragment, PicType.ShowapiResBody.RootListEntity entity) {
        if(entity==null)
            entity = mTitleEntity;
        mLoadedId = entity.secondList.get(0).id;
        Subscription subscription = DataManager.searchPic(mLoadedId, mLoadedPage, new MySubscriber<SearchPic>() {
            @Override
            public void onNext(SearchPic searchPic) {
                super.onNext(searchPic);
                mView.setLoadingIndicator(false);
                if (searchPic.showapi_res_code != 0) {
                    mView.showLoadingError();
                    LogUtil.e("" + searchPic.showapi_res_code);
                } else {
                    mView.showPicList(searchPic.showapi_res_body.pagebean);
                }
            }

        });
        mCallMap.put(fragment,subscription);
    }

    @Override
    public void loadMorePic(int page) {
        DataManager.searchPic(mLoadedId,page,new MySubscriber<SearchPic>(){
            @Override
            public void onNext(SearchPic searchPic) {
                super.onNext(searchPic);
                mView.setLoadingIndicator(false);
                if(searchPic.showapi_res_code!=0){
                    mView.showLoadingError();
                    return;
                }
                ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> contentlist = searchPic.showapi_res_body.pagebean.contentlist;
                if(contentlist!=null&&contentlist.size()>0) {
                    mLoadedPage++;
                    mView.addPicList(searchPic.showapi_res_body.pagebean);
                }else {
                    mView.showNoData();
                }


            }
        });
    }

    @Override
    public void cancelCall(Fragment fragment) {
        Subscription subscription = mCallMap.get(fragment);
        if(subscription!=null&&!subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
    }

    @Override
    public void start() {
        loadPicTypes();
    }

    class MySubscriber<T> extends SimpleSubscribers<T>{

        @Override
        public void onStartOnMainThread() {
            super.onStartOnMainThread();
            mView.setLoadingIndicator(true);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            mView.setLoadingIndicator(false);
            LogUtil.e(e.getMessage());
            mView.showLoadingError();
        }
    }

}
