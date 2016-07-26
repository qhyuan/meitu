

package com.walle.meitu.view.home;

import android.support.v4.app.Fragment;

import com.walle.meitu.base.BasePresenter;
import com.walle.meitu.base.BaseView;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.data.remote.model.SearchPic;

import java.util.ArrayList;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showLoadingError();
        void showNoData();
        void showPicTypes(ArrayList<PicType.ShowapiResBody.RootListEntity> picTypes);
        void showPicList(SearchPic.ShowapiResBody.Pagebean picListPage);
        void addPicList(SearchPic.ShowapiResBody.Pagebean pagebean);
    }

    interface Presenter extends BasePresenter {
        void result(int requestCode, int resultCode);
        void loadPicTypes();
        void loadPicList(Fragment fragment, PicType.ShowapiResBody.RootListEntity entity);
        void loadMorePic(int page);
        void cancelCall(Fragment fragment);
    }
}
