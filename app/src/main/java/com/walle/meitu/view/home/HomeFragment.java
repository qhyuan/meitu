package com.walle.meitu.view.home;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viewpagerindicator.TabPageIndicator;
import com.walle.meitu.R;
import com.walle.meitu.adapter.FragmentPagerAdapterCompat;
import com.walle.meitu.base.BaseFragment;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.listener.LoadMore;
import com.walle.meitu.utils.LogUtil;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment implements HomeContract.View {
    private TabPageIndicator mIndicator;
    private ViewPager mPager;

    private View mView;
    private HomeContract.Presenter mPresenter;
    private ArrayList<PicType.ShowapiResBody.RootListEntity> mTitleData;


    private FragmentPagerAdapterCompat mPagerAdapter;
    private int mSelectedPostion;

    public HomeFragment() {
    }


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @Override
    protected void initView() {

        mTitleData = new ArrayList<>();
        mPagerAdapter=new FragmentPagerAdapterCompat(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PicListFragment.newInstance(new LoadMore(mPresenter));
            }

            @Override
            public int getCount() {
                return mTitleData.size();
            }

            @Override
            public String getPageTitle(int position) {
                return mTitleData.get(position).name;
            }
        };
        mPager.setOffscreenPageLimit(3);
        mPager.setAdapter(mPagerAdapter);
        mIndicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                mPresenter.cancelCall(mPagerAdapter.getFragment(mSelectedPostion));
                mPresenter.loadPicList(mPagerAdapter.getFragment(position),mTitleData.get(position));
                mSelectedPostion = position;
            }
        });
        mIndicator.setOnTabReselectedListener(new TabPageIndicator.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                mPresenter.cancelCall(mPagerAdapter.getFragment(mSelectedPostion));
                mPresenter.loadPicList(mPagerAdapter.getFragment(position),mTitleData.get(position));
                mSelectedPostion = position;
            }
        });
        mIndicator.setViewPager(mPager);
    }

    @Override
    protected void findView(View view) {
        mView = view;
        mPager = $(mView,R.id.pager);
        mIndicator = $(mView,R.id.indicator);
    }

    @Override
    public int getContentViewRes() {
        return R.layout.fragment_main;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        showMsg("loading:" + (active ? "begin" : "end"));
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
        showMsg("types:" + picTypes.size());
        mTitleData = picTypes;
        mPagerAdapter.notifyDataSetChanged();
        mIndicator.notifyDataSetChanged();
        mPresenter.loadPicList(mPagerAdapter.getFragment(mSelectedPostion), mTitleData.get(mSelectedPostion));
    }

    @Override
    public void showPicList(SearchPic.ShowapiResBody.Pagebean picListPage) {
        showMsg("piclist:" + picListPage.contentlist.size()+"--mSelectedPostion="+mSelectedPostion);
        PicListFragment fragment = (PicListFragment) mPagerAdapter.getFragment(mSelectedPostion);
        fragment.refreshData(picListPage);
    }

    @Override
    public void addPicList(SearchPic.ShowapiResBody.Pagebean pagebean) {
        PicListFragment fragment = (PicListFragment) mPagerAdapter.getFragment(mSelectedPostion);
        fragment.loadMore(pagebean);
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.start();
    }

    private void showMsg(String msg) {
        LogUtil.i(TAG,msg);
    }

    private  <T extends View>  T $(View view,int id){
        return (T)view.findViewById(id);
    }

}
