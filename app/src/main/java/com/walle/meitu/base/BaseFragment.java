package com.walle.meitu.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by void on 16/3/9.
 */
public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";
    private View mContentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG,"onCreateView");
        mContentView = inflater.inflate(getContentViewRes(),container,false);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i(TAG,"onViewCreated");
        findView(view);
        initView();
    }
    protected Context getApplicationContext() {
        return getActivity().getApplication();
    }

    protected abstract void initView();

    protected abstract void findView(View view);

    public abstract int getContentViewRes();
}
