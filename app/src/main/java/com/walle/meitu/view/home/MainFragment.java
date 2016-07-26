package com.walle.meitu.view.home;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;
import com.walle.meitu.R;
import com.walle.meitu.adapter.FragmentPagerAdapterCompat;
import com.walle.meitu.adapter.MyMainRecyclerViewAdapter;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.data.remote.model.SearchPic;
import com.walle.meitu.listener.LoadMoreListener;
import com.walle.meitu.utils.LogUtil;

import java.util.ArrayList;


public class MainFragment extends Fragment implements MainContract.View {
    private static final java.lang.String TAG = "MainFragment";
    private TabPageIndicator mIndicator;
    private ViewPager mPager;

    private View mView;
    private MainContract.Presenter mPresenter;
    private ArrayList<PicType.ShowapiResBody.RootListEntity> mTitleData;


    private PicType.ShowapiResBody.RootListEntity mSelectedTitle;
    private MyMainRecyclerViewAdapter.ViewHolder mSelectedViewHolder;
    private FragmentPagerAdapterCompat mPagerAdapter;
    private int mSelectedPostion;

    public MainFragment() {
    }


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        mPager = $(mView,R.id.pager);
        mIndicator = $(mView,R.id.indicator);

        mTitleData = new ArrayList<>();
        mPagerAdapter=new FragmentPagerAdapterCompat(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return PicListFragment.newInstance(new LoadMore());
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
        return mView;
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
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
        mPresenter.start();
    }

    private void showMsg(String msg) {
        LogUtil.i(TAG,msg);
    }

    private  <T extends View>  T $(View view,int id){
        return (T)view.findViewById(id);
    }

    public class LoadMore implements LoadMoreListener, Parcelable {

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

        public  final Parcelable.Creator<LoadMore> CREATOR = new Parcelable.Creator<LoadMore>() {
            public LoadMore createFromParcel(Parcel source) {
                return new LoadMore(source);
            }

            public LoadMore[] newArray(int size) {
                return new LoadMore[size];
            }
        };
    }
}
