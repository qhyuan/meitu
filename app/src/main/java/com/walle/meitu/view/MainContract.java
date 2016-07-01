

package com.walle.meitu.view;

import com.walle.meitu.BasePresenter;
import com.walle.meitu.BaseView;
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

        void showPicList(ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> picList);



    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);

        void loadPicTypes();

        void loadPicList(PicType.ShowapiResBody.RootListEntity entity);

        void loadMorePic();

        void setShowPicView(View view, PicType.ShowapiResBody.RootListEntity currentSelectedTitle);
    }
}
