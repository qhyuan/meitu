package com.walle.meitu.view.home;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.walle.meitu.R;
import com.walle.meitu.adapter.PicListRecyclerViewAdapter;
import com.walle.meitu.base.BaseFragment;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.listener.LoadMore;
import com.walle.meitu.utils.LogUtil;
import com.walle.meitu.widget.DividerGridItemDecoration;

public class PicListFragment extends BaseFragment {
    private static final String ARG_LIST = "arg-list";

    private SearchPic.ShowapiResBody.Pagebean mData;
    private PicListRecyclerViewAdapter adapter;
    private RecyclerView recycleView;
    private View mLoadMoreView;
    private LoadMore mLoadMoreListener;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PicListFragment() {
        LogUtil.d("PicListFragment");
    }


    public static PicListFragment newInstance(LoadMore loadMoreListener) {
        PicListFragment fragment = new PicListFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_LIST, loadMoreListener);
        fragment.setArguments(args);
        LogUtil.i("newInstance");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mLoadMoreListener = getArguments().getParcelable(ARG_LIST);
        }
    }

    @Override
    protected void initView() {
        adapter = new PicListRecyclerViewAdapter(mData, null);
        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycleView.setLayoutManager(layoutManager);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int[] lastPositions = layoutManager.findLastCompletelyVisibleItemPositions(null);
                if (lastPositions[0] == adapter.getItemCount()-1 || lastPositions[1] == adapter.getItemCount()-1) {
                    mLoadMoreListener.loadNextPage(mData.currentPage+1);
                    showLoadMore(true);
                }
            }
        });
        recycleView.setAdapter(adapter);
        recycleView.addItemDecoration(new DividerGridItemDecoration(getActivity().getApplicationContext()));
        showLoadMore(false);
    }

    @Override
    protected void findView(View view) {
        recycleView = (RecyclerView) view.findViewById(R.id.list);
        mLoadMoreView = view.findViewById(R.id.loadmore);
    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_piclist_list2;
    }


    private void showLoadMore(boolean isShow) {
        mLoadMoreView.setVisibility(isShow?View.VISIBLE:View.INVISIBLE);
    }

    public void refreshData(SearchPic.ShowapiResBody.Pagebean data) {
        mData = data;
        LogUtil.i("refreshData currentPageSize=" + mData.contentlist.size());
        adapter.refreshData(mData);
        showLoadMore(false);
    }

    public void loadMore(SearchPic.ShowapiResBody.Pagebean data) {
        mData = data;
        adapter.addData(data);
        showLoadMore(false);
    }
}
