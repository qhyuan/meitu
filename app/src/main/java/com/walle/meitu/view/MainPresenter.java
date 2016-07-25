package com.walle.meitu.view;

import com.walle.meitu.data.DataManager;
import com.walle.meitu.data.remote.SimpleSubscribers;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by void on 16/6/30
 */
public class MainPresenter implements MainContract.Presenter {

    private final MainContract.View mView;
    private int mLoadedPage = 1;
    private int mLoadedId;
    private MainContract.View mShowPicView;
    private PicType.ShowapiResBody.RootListEntity mTitleEntity;

    public MainPresenter(MainContract.View view) {
        view.setPresenter(this);
        mView = view;
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadPicTypes() {
        DataManager.getPicTypes(new MySubscriber<PicType>(mView) {

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
    public void loadPicList(PicType.ShowapiResBody.RootListEntity entity) {
        if(entity==null)
            entity = mTitleEntity;
        mLoadedId = entity.secondList.get(0).id;
        DataManager.searchPic(mLoadedId,mLoadedPage,new MySubscriber<SearchPic>(mShowPicView){

            @Override
            public void onNext(SearchPic searchPic) {
                super.onNext(searchPic);
                mShowPicView.setLoadingIndicator(false);
                if(searchPic.showapi_res_code!=0){
                    mShowPicView.showLoadingError();
                    LogUtil.e(""+searchPic.showapi_res_code);
                }else {
                    mShowPicView.showPicList(searchPic.showapi_res_body.pagebean.contentlist);
                }
            }

        });
    }

    @Override
    public void loadMorePic() {
        DataManager.searchPic(mLoadedId,mLoadedPage+1,new MySubscriber<SearchPic>(mShowPicView){
            @Override
            public void onNext(SearchPic searchPic) {
                super.onNext(searchPic);
                mShowPicView.setLoadingIndicator(false);
                if(searchPic.showapi_res_code!=0){
                    mShowPicView.showLoadingError();
                    return;
                }
                ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> contentlist = searchPic.showapi_res_body.pagebean.contentlist;
                if(contentlist!=null&&contentlist.size()>0) {
                    mLoadedPage++;
                    mShowPicView.showPicList(contentlist);
                }else {
                    mShowPicView.showNoData();
                }


            }
        });
    }

    @Override
    public void setShowPicView(MainContract.View view, PicType.ShowapiResBody.RootListEntity currentSelectedTitle) {
        mShowPicView = view;
        mTitleEntity = currentSelectedTitle;
        mShowPicView.setPresenter(this);
    }

    @Override
    public void start() {
        loadPicTypes();
    }

    class MySubscriber<T> extends SimpleSubscribers<T>{
        private MainContract.View view ;
        public MySubscriber(MainContract.View view) {
            this.view = view;
        }

        @Override
        public void onStartOnMainThread() {
            super.onStartOnMainThread();
            view.setLoadingIndicator(true);
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            view.setLoadingIndicator(false);
            LogUtil.e(e.getMessage());
            view.showLoadingError();
        }
    }

}
