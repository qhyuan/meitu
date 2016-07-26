package com.walle.meitu.view.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.walle.meitu.R;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.listener.LoadMoreListener;
import com.walle.meitu.utils.LogUtil;
import com.walle.meitu.widget.DividerGridItemDecoration;

public class PicListFragment extends Fragment {
    private static final String ARG_LIST = "arg-list";

    private SearchPic.ShowapiResBody.Pagebean mData;
    private PicListRecyclerViewAdapter adapter;
    private RecyclerView recycleView;
    private View mLoadMoreView;
    private LoadMoreListener mLoadMoreListener;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PicListFragment() {
        LogUtil.d("PicListFragment");
    }


    public static PicListFragment newInstance(MainFragment.LoadMore loadMoreListener) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new PicListRecyclerViewAdapter(mData, null);
        View view = inflater.inflate(R.layout.fragment_piclist_list2, container, false);
        recycleView = (RecyclerView) view.findViewById(R.id.list);
        mLoadMoreView = view.findViewById(R.id.loadmore);
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
        return view;
    }

    private void showLoadMore(boolean isShow) {
        mLoadMoreView.setVisibility(isShow?View.VISIBLE:View.INVISIBLE);
    }

    public void refreshData(SearchPic.ShowapiResBody.Pagebean data) {
        mData = data;
        LogUtil.i("refreshData currentPage=" + mData.currentPage);
        if (adapter == null) {
            adapter = new PicListRecyclerViewAdapter(mData, null);
            recycleView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }
        adapter.refreshData(mData);
        showLoadMore(false);
    }

    public void loadMore(SearchPic.ShowapiResBody.Pagebean data) {
        mData = data;
        adapter.addData(data);
        showLoadMore(false);
    }
}
