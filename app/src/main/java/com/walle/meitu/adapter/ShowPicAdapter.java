package com.walle.meitu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.view.showpic.ShowPicFragment;

import java.util.ArrayList;

/**
 * Created by void on 16/3/12.
 */
public class ShowPicAdapter extends FragmentPagerAdapter {
    private ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist.List> mData;
    private View.OnLongClickListener mListener;
    public ShowPicAdapter(FragmentManager fm, ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist.List> data, View.OnLongClickListener listener) {
        super(fm);
        mData = data;
        mListener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        return ShowPicFragment.newInstance(mData.get(position).big,mListener);
    }

    @Override
    public int getCount() {
        return mData.size();
    }
}
