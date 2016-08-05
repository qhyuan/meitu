package com.walle.meitu.listener;

import android.os.Parcel;
import android.os.Parcelable;

import com.walle.meitu.view.home.HomeContract;

public class LoadMore implements LoadMoreListener, Parcelable {

    private HomeContract.Presenter mPresenter;

    public LoadMore(HomeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void loadNextPage(int i) {
        mPresenter.loadMorePic(i);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public LoadMore() {
    }

    protected LoadMore(Parcel in) {
    }

    public static final Parcelable.Creator<LoadMore> CREATOR = new Parcelable.Creator<LoadMore>() {
        public LoadMore createFromParcel(Parcel source) {
            return new LoadMore(source);
        }

        public LoadMore[] newArray(int size) {
            return new LoadMore[size];
        }
    };
}