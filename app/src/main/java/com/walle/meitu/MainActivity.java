package com.walle.meitu;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.walle.meitu.data.DataManager;
import com.walle.meitu.data.remote.NetWorkManager;
import com.walle.meitu.data.remote.SimpleSubscribers;
import com.walle.meitu.data.remote.model.PicType;
import com.walle.meitu.utils.LogUtil;
import com.walle.meitu.view.MainFragment;
import com.walle.meitu.view.MainPresenter;
import com.walle.meitu.view.PicListFragment;

import java.io.File;

import rx.Subscriber;

public class MainActivity extends AppCompatActivity implements MainFragment.OnListFragmentInteractionListener {

    private RecyclerView mTitleBar;
    private FrameLayout mMain;
    private MainPresenter mPresenter;
    private ViewPager mPager;
    private FragmentPagerAdapterCompat mPagerAdapter;
    private int mSelectedPos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //// TODO: 2016/7/20 文件下载测试
        String url = "http://120.210.211.197:8080/consumer/img/1.jpg";
        DataManager.downloadFile(url, new SimpleSubscribers<File>(){});
        ////


        mMain = $(R.id.main_panel);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        final MainFragment mainFragment = MainFragment.newInstance();
        transaction.replace(R.id.main_panel,mainFragment);
        transaction.commit();

        mPresenter = new MainPresenter(mainFragment);

        mPager = $(R.id.showPicPager);
        mPagerAdapter = new FragmentPagerAdapterCompat(getSupportFragmentManager()) {

            @Override
            public Fragment getItem(int position) {
                return PicListFragment.newInstance(2);
            }

            @Override
            public int getCount() {
                return 7;
            }
        };

        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                LogUtil.i("onPageSelected="+position);
                mSelectedPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                LogUtil.i("onPageScrollStateChanged="+state);
                if(state==ViewPager.SCROLL_STATE_IDLE){
                    PicListFragment item = (PicListFragment) mPagerAdapter.getFragment(mSelectedPos);
                    mPresenter.setShowPicView(item,mainFragment.getCurrentSelectedTitle());
                    mainFragment.setSelectedPos(mSelectedPos);
                }
            }
        });

    }

    private  <T extends View>  T $(int id){
        return (T)findViewById(id);
    }

    @Override
    public void onListFragmentInteraction(PicType.ShowapiResBody.RootListEntity item) {

    }

    public abstract class FragmentPagerAdapterCompat extends FragmentPagerAdapter {

        private SparseArray<Fragment> fragments;

        public FragmentPagerAdapterCompat(android.support.v4.app.FragmentManager fm) {
            super(fm);
            fragments = new SparseArray<>(getCount());
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            fragments.remove(position);
        }

        public Fragment getFragment(int position) {
            return fragments.get(position);
        }

    }

}
