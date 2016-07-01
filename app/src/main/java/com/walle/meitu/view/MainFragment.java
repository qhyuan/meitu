package com.walle.meitu.view;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.walle.meitu.R;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.utils.LogUtil;

import java.util.ArrayList;


public class MainFragment extends Fragment implements MainContract.View{



    private OnListFragmentInteractionListener mListener;
    private View mView;
    private MainContract.Presenter mPresenter;
    private MyMainRecyclerViewAdapter mTitleAdapter;
    private ArrayList<PicType.ShowapiResBody.RootListEntity> mTitleData;
    private RecyclerView mTitleRecycle;
    private ViewPager mViewPager;
    private PagerAdapter mViewPagerAdapter;
    private PicType.ShowapiResBody.RootListEntity mSelectedTitle;
    private MyMainRecyclerViewAdapter.ViewHolder mSelectedViewHolder;

    public MainFragment() {
    }


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);

        mTitleRecycle = (RecyclerView) mView.findViewById(R.id.title);
        Context context = mTitleRecycle.getContext();
        mTitleRecycle.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL,false));
        mTitleData =  new ArrayList<>();
        mTitleAdapter = new MyMainRecyclerViewAdapter(mTitleData, mListener);
        mTitleRecycle.setAdapter(mTitleAdapter);

        return mView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public PicType.ShowapiResBody.RootListEntity getCurrentSelectedTitle(){
        return mSelectedTitle;
    }

    public void setSelectedPos(int pos){
        if(pos>=mTitleData.size())
            return;
        mSelectedTitle = mTitleData.get(pos);
        mSelectedViewHolder.itemView.setSelected(false);
        mTitleRecycle.smoothScrollToPosition(pos);
        mSelectedViewHolder = (MyMainRecyclerViewAdapter.ViewHolder) mTitleRecycle.findViewHolderForAdapterPosition(pos);
        mSelectedViewHolder.itemView.setSelected(true);
        mPresenter.loadPicList(mSelectedTitle);
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        showMsg("loading:"+(active?"begin":"end"));
    }

    @Override
    public void showLoadingError() {
        showMsg("loading error");
    }

    @Override
    public void showNoData() {
        showMsg("no data");
    }

    @Override
    public void showPicTypes(ArrayList<PicType.ShowapiResBody.RootListEntity> picTypes) {
        showMsg("types:"+picTypes.size());
        mTitleData = picTypes;
        mTitleAdapter.refreshData(picTypes);
        mTitleRecycle.post(new Runnable() {
            @Override
            public void run() {
                LogUtil.e("run");
                MyMainRecyclerViewAdapter.ViewHolder viewHolder = (MyMainRecyclerViewAdapter.ViewHolder) mTitleRecycle.findViewHolderForAdapterPosition(0);
                viewHolder.itemView.setSelected(true);
                mSelectedTitle = mTitleData.get(0);
                mSelectedViewHolder = viewHolder;
            }
        });
    }

    @Override
    public void showPicList(ArrayList<SearchPic.ShowapiResBody.Pagebean.Contentlist> picList) {
        showMsg("piclist:"+ picList.size());
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(PicType.ShowapiResBody.RootListEntity item);
    }

    private void showMsg(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

}
