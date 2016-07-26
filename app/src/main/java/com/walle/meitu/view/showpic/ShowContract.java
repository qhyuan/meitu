

package com.walle.meitu.view.showpic;

import android.content.Context;

import com.walle.meitu.base.BasePresenter;
import com.walle.meitu.base.BaseView;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.download.ImageDownload;

import java.util.ArrayList;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface ShowContract {

    interface View extends BaseView<Presenter> {
        void shareSingleImage(String imagePath);
    }

    interface Presenter extends BasePresenter {
        void download(final String _url, final Context context);
        void download(final String _url, final Context context, final boolean isSetWallpaper);
        void download(ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist.List> picList);
    }
}
