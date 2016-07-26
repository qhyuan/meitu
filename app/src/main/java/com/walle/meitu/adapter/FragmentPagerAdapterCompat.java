package com.walle.meitu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.walle.meitu.utils.LogUtil;

public abstract class FragmentPagerAdapterCompat extends FragmentPagerAdapter {

    private SparseArray<Fragment> fragments;

    public FragmentPagerAdapterCompat(android.support.v4.app.FragmentManager fm) {
        super(fm);
        fragments = new SparseArray<>();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        fragments.put(position, fragment);
        LogUtil.i("instantiateItem="+position);
        return fragment;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
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
