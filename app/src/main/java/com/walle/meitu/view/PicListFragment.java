package com.walle.meitu.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walle.meitu.R;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.utils.LogUtil;
import com.walle.meitu.widget.DividerGridItemDecoration;

import java.util.ArrayList;

public class PicListFragment extends Fragment implements MainContract.View{


    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 2;
    private ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> mData;
    private PicListRecyclerViewAdapter adapter;
    private RecyclerView recycleView;
    private MainContract.Presenter mPresenter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PicListFragment() {
        LogUtil.d("PicListFragment");
    }


    public static PicListFragment newInstance(int columnCount) {
        PicListFragment fragment = new PicListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        LogUtil.i("newInstance");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mData = new ArrayList<>();
        adapter = new PicListRecyclerViewAdapter(mData);
        recycleView = (RecyclerView) inflater.inflate(R.layout.fragment_piclist_list2, container, false);
        recycleView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        recycleView.setAdapter(adapter);
        recycleView.addItemDecoration(new DividerGridItemDecoration(getActivity().getApplicationContext()));
        if(mPresenter!=null)
            mPresenter.loadPicList(null);
        return recycleView;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    public void refreshData(ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> data){
        mData = data;
        LogUtil.i("refreshData="+mData.size());
        adapter.refreshData(mData);
    }

    public void loadMore(ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> data){
        adapter.addData(data);
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showLoadingError() {

    }

    @Override
    public void showNoData() {

    }

    @Override
    public void showPicTypes(ArrayList<PicType.ShowapiResBody.RootListEntity> picTypes) {

    }

    @Override
    public void showPicList(final ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> picList) {
        refreshData(picList);

    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
